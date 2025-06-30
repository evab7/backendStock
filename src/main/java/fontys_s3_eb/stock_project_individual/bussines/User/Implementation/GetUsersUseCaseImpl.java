package fontys_s3_eb.stock_project_individual.bussines.User.Implementation;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.UserEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.UserRepository;
import fontys_s3_eb.stock_project_individual.bussines.EntityConverters.UserConverter;
import fontys_s3_eb.stock_project_individual.bussines.User.UseCase.GetUsersUseCase;
import fontys_s3_eb.stock_project_individual.domain.UserPackage.GetUsersResponse;
import fontys_s3_eb.stock_project_individual.domain.UserPackage.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetUsersUseCaseImpl implements GetUsersUseCase {

    private final UserRepository userRepository;

    @Override
    public GetUsersResponse getUsers() {
        List<UserEntity> users;
        users = userRepository.findAll();

        final GetUsersResponse response = new GetUsersResponse();
        List<User> newUsers = users
                .stream()
                .map(UserConverter::convert)
                .toList();
        response.setUsers(newUsers);
        return response;
    }
}
