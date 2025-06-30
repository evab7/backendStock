package fontys_s3_eb.stock_project_individual.bussines.Market.UseCase;

import fontys_s3_eb.stock_project_individual.domain.MarketPackage.SellStockRequest;
import fontys_s3_eb.stock_project_individual.domain.MarketPackage.SellStockResponse;

public interface SellStockUseCase {
    SellStockResponse sellStock (SellStockRequest sellStockRequest);
}
