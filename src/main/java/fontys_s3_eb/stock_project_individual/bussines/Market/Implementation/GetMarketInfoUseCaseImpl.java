package fontys_s3_eb.stock_project_individual.bussines.Market.Implementation;


import fontys_s3_eb.stock_project_individual.Persistence.Entity.MarketEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.MarketRepository;
import fontys_s3_eb.stock_project_individual.bussines.EntityConverters.MarketConverter;
import fontys_s3_eb.stock_project_individual.bussines.Market.UseCase.GetMarketInfoUseCase;
import fontys_s3_eb.stock_project_individual.domain.MarketPackage.GetMarketInfoResponse;
import fontys_s3_eb.stock_project_individual.domain.MarketPackage.Market;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GetMarketInfoUseCaseImpl implements GetMarketInfoUseCase {

    private final MarketRepository marketRepository;


    @Override
    public GetMarketInfoResponse getMarketInfo() {
        try {
            List<MarketEntity> results = Optional.of(marketRepository.findAll())
                    .orElse(Collections.emptyList());

            List<Market> userMarkets = new ArrayList<>();
            final GetMarketInfoResponse response = new GetMarketInfoResponse();


            for (MarketEntity market : results) {
                Market market1 = MarketConverter.convert(market);
                userMarkets.add(market1);
            }
            response.setMarketList(userMarkets);
            return response;



        }
        catch (Exception e) {
            return new GetMarketInfoResponse(Collections.emptyList());
        }
    }

}
