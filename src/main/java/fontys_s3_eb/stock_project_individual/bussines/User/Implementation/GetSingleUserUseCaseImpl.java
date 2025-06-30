package fontys_s3_eb.stock_project_individual.bussines.User.Implementation;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.UserEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.UserRepository;
import fontys_s3_eb.stock_project_individual.bussines.EntityConverters.UserConverter;
import fontys_s3_eb.stock_project_individual.bussines.User.UseCase.GetSingleUserUseCase;
import fontys_s3_eb.stock_project_individual.domain.UserPackage.GetSingleUserResponse;
import fontys_s3_eb.stock_project_individual.domain.UserPackage.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class GetSingleUserUseCaseImpl implements GetSingleUserUseCase {

    private final UserRepository userRepository;

    @Override
    public GetSingleUserResponse getUser(Long userId) {

        if (userId == null ) {
            throw new IllegalArgumentException("Invalid request: User ID cannot be null.");
        }

        UserEntity userEntity = userRepository.findByUserId(userId);

        User user = UserConverter.convert(userEntity);

        return GetSingleUserResponse.builder()
                .user(user)
                .build();

    }
}
