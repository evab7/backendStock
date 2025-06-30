package fontys_s3_eb.stock_project_individual.UnitTesting.ServiceLayer.Stock;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.UserEntity;
import fontys_s3_eb.stock_project_individual.bussines.User.Implementation.CreateUserUseCaseImpl;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.UserRepository;
import fontys_s3_eb.stock_project_individual.domain.UserPackage.CreateUserRequest;
import fontys_s3_eb.stock_project_individual.domain.UserPackage.CreateUserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCase;

    private CreateUserRequest request;
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {

        request = CreateUserRequest.builder()
                .username("testuser")
                .password("password123")
                .role("USER")
                .build();
    }

    @Test
    void addUser_SuccessfulSave_ReturnsCorrectResponse() {
        // Arrange
        UserEntity mockUserEntity = UserEntity.builder()
                .userId(1L)
                .username("testuser")
                .password("encodedPassword123")
                .role("USER")
                .build();

        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodedPassword123");

        when(userRepository.save(any(UserEntity.class))).thenReturn(mockUserEntity);

        // Act
        CreateUserResponse response = createUserUseCase.createUser(request);

        // Assert

        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(passwordEncoder, times(1)).encode("password123");
    }



}
