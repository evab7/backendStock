package fontys_s3_eb.stock_project_individual.bussines.User.Implementation;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.RefreshTokenEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Entity.UserEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.RefreshTokenRepository;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.UserRepository;
import fontys_s3_eb.stock_project_individual.bussines.Exceptions.InvalidCredentialException;
import fontys_s3_eb.stock_project_individual.bussines.User.UseCase.LogInUseCase;
import fontys_s3_eb.stock_project_individual.configuration.Security.TokenJWL.Implementation.AccessTokenImplementation;
import fontys_s3_eb.stock_project_individual.configuration.Security.TokenJWL.TokenGenerator;

import fontys_s3_eb.stock_project_individual.domain.UserPackage.LogInRequest;
import fontys_s3_eb.stock_project_individual.domain.UserPackage.LoginResponse;
import fontys_s3_eb.stock_project_individual.domain.UserPackage.RefreshTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class LogInUseCaseImpl implements LogInUseCase {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenGenerator tokenGenerator;

    private final RefreshTokenRepository refreshTokenRepository;
    private final  RefreshTokenUseCaseImpl refreshTokenUseCase;

    @Override
    public LoginResponse login(LogInRequest loginRequest) {
        UserEntity user = userRepository.findByUsername(loginRequest.getUsername());
        if (user == null) {
            throw new InvalidCredentialException("User can't be empty");
        }

        if (!matchesPassword(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialException("Passwords must match");
        }

        String accessToken = generateAccessToken(user);



        RefreshTokenEntity refreshToken = refreshTokenRepository.findById(user.getUserId())
                    .orElse(null);//

        if (refreshToken == null) {
            RefreshTokenEntity refreshTokenDb = generateRefreshToken(user);
            refreshToken = refreshTokenDb;

        }


        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken()).build();
    }
    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenEntity refreshToken) {
        Optional<UserEntity> optionalUser = userRepository.findById(refreshToken.getId());

        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            String accessToken = generateAccessToken(user);

            return RefreshTokenResponse.builder()
                    .accessToken(accessToken)
                    .build();
        }

        else {
            return null;
        }

    }

    private boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private String generateAccessToken(UserEntity user) {
        Long userId = user.getUserId();

        return tokenGenerator.encode(
                new AccessTokenImplementation(user.getUsername(), userId, user.getRole()));
    }

    private RefreshTokenEntity generateRefreshToken(UserEntity user) {

        return refreshTokenUseCase.createRefreshToken(user.getUsername());
    }


}
