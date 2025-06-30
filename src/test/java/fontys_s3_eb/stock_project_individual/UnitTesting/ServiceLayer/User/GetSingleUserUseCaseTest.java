package fontys_s3_eb.stock_project_individual.UnitTesting.ServiceLayer.User;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.UserEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.UserRepository;
import fontys_s3_eb.stock_project_individual.bussines.User.Implementation.GetSingleUserUseCaseImpl;
import fontys_s3_eb.stock_project_individual.domain.UserPackage.GetSingleUserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetSingleUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetSingleUserUseCaseImpl getSingleUserUseCase;

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userEntity = UserEntity.builder()
                .userId(1L)
                .username("testuser")
                .password("password123")
                .role("USER")
                .build();
    }

    @Test
    void getUser_ValidUserId_ReturnsUserResponse() {
        // Arrange
        when(userRepository.findByUserId(1L)).thenReturn(userEntity);

        // Act
        GetSingleUserResponse response = getSingleUserUseCase.getUser(1L);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getUser());
        assertEquals("testuser", response.getUser().getUsername());
        assertEquals("USER", response.getUser().getRole());

        verify(userRepository, times(1)).findByUserId(1L);
    }

    @Test
    void getUser_NullUserId_ThrowsIllegalArgumentException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> getSingleUserUseCase.getUser(null));

        assertEquals("Invalid request: User ID cannot be null.", exception.getMessage());
        verify(userRepository, times(0)).findByUserId(any());
    }

}
