package fontys_s3_eb.stock_project_individual.bussines.Market.Implementation;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.MarketEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Entity.UserEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.MarketRepository;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.UserRepository;
import fontys_s3_eb.stock_project_individual.bussines.EntityConverters.MarketConverter;
import fontys_s3_eb.stock_project_individual.bussines.Market.UseCase.GetUserMarketInfoUseCase;
import fontys_s3_eb.stock_project_individual.domain.MarketPackage.GetUserMarketInfoResponse;
import fontys_s3_eb.stock_project_individual.domain.MarketPackage.Market;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GetUserMarketInfoUseCaseImpl implements GetUserMarketInfoUseCase {

    private final MarketRepository marketRepository;
    private final UserRepository userRepository;

    @Override
    public GetUserMarketInfoResponse getUserMarketInfo(Long userId) {

        if (userId== null ) {
            throw new IllegalArgumentException("Invalid request: User ID cannot be null.");
        }

        try {

            UserEntity userEntity = userRepository.findByUserId(userId);

            List<MarketEntity> userMarketinfo = marketRepository.findByUser(userEntity);

            List<Market> userMarkets = new ArrayList<>();
            final GetUserMarketInfoResponse response = new GetUserMarketInfoResponse();

            for (MarketEntity market : userMarketinfo) {
                Market market1 = MarketConverter.convert(market);
                userMarkets.add(market1);
            }

            response.setMarketList(userMarkets);

            return response;

        }
        catch (Exception e) {
            return null;
        }


    }
}
