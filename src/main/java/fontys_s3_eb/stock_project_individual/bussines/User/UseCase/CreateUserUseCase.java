package fontys_s3_eb.stock_project_individual.bussines.User.UseCase;


import fontys_s3_eb.stock_project_individual.domain.UserPackage.CreateUserRequest;
import fontys_s3_eb.stock_project_individual.domain.UserPackage.CreateUserResponse;

public interface CreateUserUseCase {
    CreateUserResponse createUser(CreateUserRequest request);
}
