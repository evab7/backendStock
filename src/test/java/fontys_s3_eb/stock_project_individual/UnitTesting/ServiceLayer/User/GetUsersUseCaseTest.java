package fontys_s3_eb.stock_project_individual.UnitTesting.ServiceLayer.User;


import fontys_s3_eb.stock_project_individual.Persistence.Entity.UserEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.UserRepository;
import fontys_s3_eb.stock_project_individual.bussines.User.Implementation.GetUsersUseCaseImpl;
import fontys_s3_eb.stock_project_individual.domain.UserPackage.GetUsersResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class GetUsersUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUsersUseCaseImpl getUsersUseCase;

    private List<UserEntity> userEntities;

    @BeforeEach
    void setUp() {
        userEntities = List.of(
                UserEntity.builder()
                        .userId(1L)
                        .username("user1")
                        .password("password1")
                        .role("USER")
                        .build(),
                UserEntity.builder()
                        .userId(2L)
                        .username("user2")
                        .password("password2")
                        .role("ADMIN")
                        .build()
        );
    }

    @Test
    void getUsers_ValidUsersExist_ReturnsUserList() {
        // Arrange
        when(userRepository.findAll()).thenReturn(userEntities);

        // Act
        GetUsersResponse response = getUsersUseCase.getUsers();

        // Assert
        assertNotNull(response);
        assertNotNull(response.getUsers());
        assertEquals(2, response.getUsers().size());
        assertEquals("user1", response.getUsers().get(0).getUsername());
        assertEquals("ADMIN", response.getUsers().get(1).getRole());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUsers_NoUsersExist_ReturnsEmptyList() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        GetUsersResponse response = getUsersUseCase.getUsers();

        // Assert
        assertNotNull(response);
        assertNotNull(response.getUsers());
        assertTrue(response.getUsers().isEmpty());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUsers_RepositoryThrowsException_ThrowsRuntimeException() {
        // Arrange
        when(userRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> getUsersUseCase.getUsers());

        assertEquals("Database error", exception.getMessage());
        verify(userRepository, times(1)).findAll();
    }
}

