package fontys_s3_eb.stock_project_individual.bussines.Market.UseCase;

import fontys_s3_eb.stock_project_individual.domain.MarketPackage.GetUserMarketInfoResponse;

public interface GetUserMarketInfoUseCase {
    GetUserMarketInfoResponse getUserMarketInfo (Long userId);
}
