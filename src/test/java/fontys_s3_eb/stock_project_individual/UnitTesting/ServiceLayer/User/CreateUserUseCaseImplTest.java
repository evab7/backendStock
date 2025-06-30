package fontys_s3_eb.stock_project_individual.UnitTesting.ServiceLayer.User;
import fontys_s3_eb.stock_project_individual.Persistence.Entity.UserEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.UserRepository;
import fontys_s3_eb.stock_project_individual.bussines.User.Implementation.CreateUserUseCaseImpl;
import fontys_s3_eb.stock_project_individual.domain.UserPackage.CreateUserRequest;
import fontys_s3_eb.stock_project_individual.domain.UserPackage.CreateUserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateUserUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCase;

    private CreateUserRequest request;

    @BeforeEach
    void setUp() {
        request = CreateUserRequest.builder()
                .username("testuser")
                .password("password123")
                .role("USER")
                .build();
    }

    @Test
    void createUser_SuccessfulSave_ReturnsSuccessResponse() {
        // Arrange
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword123");
        when(userRepository.save(any(UserEntity.class))).thenReturn(UserEntity.builder()
                .userId(1L)
                .username("testuser")
                .password("encodedPassword123")
                .role("USER")
                .build());

        // Act
        CreateUserResponse response = createUserUseCase.createUser(request);

        // Assert
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals("User created successfully!", response.getMessage());
        assertEquals("testuser", response.getUsername());

        verify(passwordEncoder, times(1)).encode("password123");
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void createUser_UsernameAlreadyTaken_ThrowsExceptionAndReturnsFailureResponse() {
        // Arrange
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword123");
        doThrow(new DataIntegrityViolationException("username")).when(userRepository).save(any(UserEntity.class));

        // Act
        CreateUserResponse response = createUserUseCase.createUser(request);

        // Assert
        assertNotNull(response);
        assertFalse(response.isSuccess());
        assertEquals("Username is already taken.", response.getMessage());
        assertEquals("testuser", response.getUsername());

        verify(passwordEncoder, times(1)).encode("password123");
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void createUser_UnexpectedError_ReturnsGenericErrorMessage() {
        // Arrange
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword123");
        doThrow(new RuntimeException("Unexpected database error")).when(userRepository).save(any(UserEntity.class));

        // Act
        CreateUserResponse response = createUserUseCase.createUser(request);

        // Assert
        assertNotNull(response);
        assertFalse(response.isSuccess());
        assertEquals("An unexpected error occurred while creating the user.", response.getMessage());
        assertNull(response.getUsername());

        verify(passwordEncoder, times(1)).encode("password123");
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }
}
