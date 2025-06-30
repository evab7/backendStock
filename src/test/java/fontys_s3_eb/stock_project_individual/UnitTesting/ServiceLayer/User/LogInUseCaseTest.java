package fontys_s3_eb.stock_project_individual.UnitTesting.ServiceLayer.User;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.RefreshTokenEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Entity.UserEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.RefreshTokenRepository;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.UserRepository;
import fontys_s3_eb.stock_project_individual.bussines.Exceptions.InvalidCredentialException;
import fontys_s3_eb.stock_project_individual.bussines.User.Implementation.LogInUseCaseImpl;
import fontys_s3_eb.stock_project_individual.bussines.User.Implementation.RefreshTokenUseCaseImpl;
import fontys_s3_eb.stock_project_individual.configuration.Security.TokenJWL.Implementation.AccessTokenImplementation;
import fontys_s3_eb.stock_project_individual.configuration.Security.TokenJWL.TokenGenerator;
import fontys_s3_eb.stock_project_individual.domain.UserPackage.LogInRequest;
import fontys_s3_eb.stock_project_individual.domain.UserPackage.LoginResponse;
import fontys_s3_eb.stock_project_individual.domain.UserPackage.RefreshTokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LogInUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenGenerator tokenGenerator;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private RefreshTokenUseCaseImpl refreshTokenUseCase;

    @InjectMocks
    private LogInUseCaseImpl logInUseCase;

    private UserEntity userEntity;
    private LogInRequest loginRequest;

    @BeforeEach
    void setUp() {
        userEntity = UserEntity.builder()
                .userId(1L)
                .username("testuser")
                .password("encodedPassword123")
                .role("USER")
                .build();

        loginRequest = LogInRequest.builder()
                .username("testuser")
                .password("password123")
                .build();
    }

    @Test
    void login_ValidCredentials_ReturnsLoginResponse() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(userEntity);
        when(passwordEncoder.matches("password123", "encodedPassword123")).thenReturn(true);
        when(tokenGenerator.encode(any(AccessTokenImplementation.class))).thenReturn("accessToken123");
        when(refreshTokenRepository.findById(1L)).thenReturn(Optional.of(RefreshTokenEntity.builder()
                .id(1L)
                .token("refreshToken123")
                .build()));

        // Act
        LoginResponse response = logInUseCase.login(loginRequest);

        // Assert
        assertNotNull(response);
        assertEquals("accessToken123", response.getAccessToken());
        assertEquals("refreshToken123", response.getRefreshToken());

        verify(userRepository, times(1)).findByUsername("testuser");
        verify(passwordEncoder, times(1)).matches("password123", "encodedPassword123");
        verify(tokenGenerator, times(1)).encode(any(AccessTokenImplementation.class));
    }

    @Test
    void login_InvalidCredentials_ThrowsInvalidCredentialsException() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(userEntity);
        when(passwordEncoder.matches("password123", "encodedPassword123")).thenReturn(false);

        // Act & Assert
        assertThrows(InvalidCredentialException.class, () -> logInUseCase.login(loginRequest));

        verify(userRepository, times(1)).findByUsername("testuser");
        verify(passwordEncoder, times(1)).matches("password123", "encodedPassword123");
        verifyNoInteractions(tokenGenerator, refreshTokenRepository, refreshTokenUseCase);
    }

    @Test
    void login_NoRefreshToken_CreatesNewRefreshToken() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(userEntity);
        when(passwordEncoder.matches("password123", "encodedPassword123")).thenReturn(true);
        when(tokenGenerator.encode(any(AccessTokenImplementation.class))).thenReturn("accessToken123");
        when(refreshTokenRepository.findById(1L)).thenReturn(Optional.empty());
        when(refreshTokenUseCase.createRefreshToken("testuser"))
                .thenReturn(RefreshTokenEntity.builder().id(1L).token("newRefreshToken123").build());

        // Act
        LoginResponse response = logInUseCase.login(loginRequest);

        // Assert
        assertNotNull(response);
        assertEquals("accessToken123", response.getAccessToken());
        assertEquals("newRefreshToken123", response.getRefreshToken());

        verify(refreshTokenUseCase, times(1)).createRefreshToken("testuser");
    }

    @Test
    void refreshToken_ValidRefreshToken_ReturnsAccessToken() {
        // Arrange
        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder().id(1L).token("refreshToken123").build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(tokenGenerator.encode(any(AccessTokenImplementation.class))).thenReturn("newAccessToken123");

        // Act
        RefreshTokenResponse response = logInUseCase.refreshToken(refreshTokenEntity);

        // Assert
        assertNotNull(response);
        assertEquals("newAccessToken123", response.getAccessToken());

        verify(userRepository, times(1)).findById(1L);
        verify(tokenGenerator, times(1)).encode(any(AccessTokenImplementation.class));
    }

    @Test
    void refreshToken_InvalidRefreshToken_ReturnsNull() {
        // Arrange
        RefreshTokenEntity invalidRefreshToken = RefreshTokenEntity.builder().id(999L).token("invalidToken").build();
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        RefreshTokenResponse response = logInUseCase.refreshToken(invalidRefreshToken);

        // Assert
        assertNull(response);

        verify(userRepository, times(1)).findById(999L);
        verifyNoInteractions(tokenGenerator);
    }
}
