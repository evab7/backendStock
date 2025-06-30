package fontys_s3_eb.stock_project_individual.bussines.User.UseCase;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.RefreshTokenEntity;

public interface RefreshTokenUseCase {
     RefreshTokenEntity getAccessTokenFromRefreshToken(String token);
     RefreshTokenEntity  createRefreshToken(String username);
}
