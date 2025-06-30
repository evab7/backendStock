package fontys_s3_eb.stock_project_individual.bussines.User.UseCase;


import fontys_s3_eb.stock_project_individual.Persistence.Entity.RefreshTokenEntity;
import fontys_s3_eb.stock_project_individual.domain.UserPackage.LogInRequest;
import fontys_s3_eb.stock_project_individual.domain.UserPackage.LoginResponse;
import fontys_s3_eb.stock_project_individual.domain.UserPackage.RefreshTokenResponse;


public interface LogInUseCase {
    LoginResponse login(LogInRequest loginRequest);
    RefreshTokenResponse refreshToken (RefreshTokenEntity refreshToken);
}
