package fontys_s3_eb.stock_project_individual.bussines.User.UseCase;


import fontys_s3_eb.stock_project_individual.domain.UserPackage.GetSingleUserResponse;

public interface GetSingleUserUseCase {
    GetSingleUserResponse getUser(Long userId);
}
