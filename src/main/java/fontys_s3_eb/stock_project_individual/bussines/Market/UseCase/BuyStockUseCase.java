package fontys_s3_eb.stock_project_individual.bussines.Market.UseCase;
import fontys_s3_eb.stock_project_individual.domain.MarketPackage.BuyStockRequest;
import fontys_s3_eb.stock_project_individual.domain.MarketPackage.BuyStockResponse;

public interface BuyStockUseCase {
    BuyStockResponse buyStock (BuyStockRequest buyStockRequest);
}
