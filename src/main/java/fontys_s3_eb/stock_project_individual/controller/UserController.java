package fontys_s3_eb.stock_project_individual.controller;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.RefreshTokenEntity;
import fontys_s3_eb.stock_project_individual.bussines.User.Implementation.GetSingleUserUseCaseImpl;
import fontys_s3_eb.stock_project_individual.bussines.User.Implementation.RefreshTokenUseCaseImpl;
import fontys_s3_eb.stock_project_individual.bussines.User.UseCase.CreateUserUseCase;
import fontys_s3_eb.stock_project_individual.bussines.User.UseCase.GetUsersUseCase;
import fontys_s3_eb.stock_project_individual.bussines.User.UseCase.LogInUseCase;
import fontys_s3_eb.stock_project_individual.bussines.Exceptions.UsernameAlreadyTakenException;
import fontys_s3_eb.stock_project_individual.domain.UserPackage.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@AllArgsConstructor
@RestController
@RequestMapping({"/user"})
public class UserController {


    private final LogInUseCase loginUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final GetUsersUseCase getUserUseCase;
    private final RefreshTokenUseCaseImpl refreshTokenUseCaseImpl;
    private final GetSingleUserUseCaseImpl getSIngleUserUseCaseImpl;

    private static final String SUCCESS_USER_CREATED_MESSAGE = "User successfully created.";
    private static final String CONFLICT_USERNAME_TAKEN_MESSAGE = "Username is already taken.";

    @PostMapping("/logIn")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LogInRequest loginRequest) {
        LoginResponse loginResponse = this.loginUseCase.login(loginRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(loginResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        CreateUserResponse createUserResponse = this.createUserUseCase.createUser(createUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createUserResponse);

    }

    @PostMapping("/refreshToken")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
        RefreshTokenEntity refreshToken = refreshTokenUseCaseImpl.getAccessTokenFromRefreshToken(refreshTokenRequest.getRefreshToken());
        RefreshTokenResponse refreshTokenResponse = this.loginUseCase.refreshToken(refreshToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(refreshTokenResponse);


    }

    @GetMapping
    public ResponseEntity<GetUsersResponse> getAllUsers() {
        GetUsersResponse response = getUserUseCase.getUsers();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<GetSingleUserResponse> getSingleUser(@PathVariable @Valid Long userId) {
        GetSingleUserResponse response = getSIngleUserUseCaseImpl.getUser(userId);
        return ResponseEntity.ok(response);
    }
}


