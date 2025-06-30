package fontys_s3_eb.stock_project_individual.bussines.User.Implementation;
import fontys_s3_eb.stock_project_individual.Persistence.Entity.UserEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.UserRepository;
import fontys_s3_eb.stock_project_individual.bussines.User.UseCase.CreateUserUseCase;
import fontys_s3_eb.stock_project_individual.bussines.Exceptions.UsernameAlreadyTakenException;

import fontys_s3_eb.stock_project_individual.domain.UserPackage.CreateUserRequest;
import fontys_s3_eb.stock_project_individual.domain.UserPackage.CreateUserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private static final Logger logger = LoggerFactory.getLogger(CreateUserUseCaseImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {
        try {
            saveUser(request, request.getPassword());
            logger.info("User successfully created: {}", request.getUsername());
            return CreateUserResponse.builder()
                    .success(true)
                    .message("User created successfully!")
                    .username(request.getUsername())
                    .build();
        } catch (UsernameAlreadyTakenException e) {
            logger.warn("Failed to create user: {}", e.getMessage());
            return CreateUserResponse.builder()
                    .success(false)
                    .message(e.getMessage())
                    .username(request.getUsername())
                    .build();
        } catch (Exception e) {
            logger.error("Unexpected error during user creation: {}", e.getMessage());
            return CreateUserResponse.builder()
                    .success(false)
                    .message("An unexpected error occurred while creating the user.")
                    .build();
        }
    }

    private void saveUser(CreateUserRequest request, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        try {
            UserEntity user = UserEntity.builder()
                    .username(request.getUsername())
                    .password(encodedPassword)
                    .role(request.getRole())
                    .build();
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("username")) {
                throw new UsernameAlreadyTakenException("Username is already taken.");
            }
            throw e;
        }
    }
}