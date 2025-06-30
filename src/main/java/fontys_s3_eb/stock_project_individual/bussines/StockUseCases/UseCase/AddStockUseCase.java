package fontys_s3_eb.stock_project_individual.bussines.StockUseCases.UseCase;

import fontys_s3_eb.stock_project_individual.domain.StockPackage.CreateStockRequest;
import fontys_s3_eb.stock_project_individual.domain.StockPackage.CreateStockResponse;

public interface AddStockUseCase {
    CreateStockResponse addStock(CreateStockRequest request);
}
