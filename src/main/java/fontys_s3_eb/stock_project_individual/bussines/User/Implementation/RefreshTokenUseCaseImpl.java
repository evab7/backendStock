package fontys_s3_eb.stock_project_individual.bussines.User.Implementation;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.RefreshTokenEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Entity.UserEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.RefreshTokenRepository;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.UserRepository;
import fontys_s3_eb.stock_project_individual.bussines.User.UseCase.RefreshTokenUseCase;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.Instant;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RefreshTokenUseCaseImpl implements RefreshTokenUseCase {


    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;


    @Override
    public RefreshTokenEntity  createRefreshToken(String username) { //private

        UserEntity userEntity = userRepository.findByUsername(username);



        if (userEntity != null) {

            RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                    .user(userEntity)
                    .token(UUID.randomUUID().toString())
                    .expiryDate(Instant.now().plusMillis(24 * 60 * 60 * 1000))  // 24 hours
                    .build();
            try{
                refreshTokenRepository.save(refreshToken);
                return refreshToken;
            }
            catch(Exception e){
                return null;
            }
        }
        return null;

    }
    @Override
    public RefreshTokenEntity getAccessTokenFromRefreshToken(String token) {

        RefreshTokenEntity refreshToken = refreshTokenRepository.findByToken(token);

        try {


            if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {

                UserEntity user = userRepository.findById(refreshToken.getId())
                        .orElse(null);

               RefreshTokenEntity refreshToken1 = createRefreshToken(user.getUsername());



                throw new RuntimeException("Refresh token expired");
            } else {
                return refreshToken;
            }
        }
        catch(Exception e){
            return null;
        }
    }


}
