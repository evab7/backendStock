package fontys_s3_eb.stock_project_individual.bussines.StockUseCases.UseCase;

import fontys_s3_eb.stock_project_individual.domain.StockPackage.GetSingleStockRequest;
import fontys_s3_eb.stock_project_individual.domain.StockPackage.GetSingleStockResponse;

public interface GetSingleStockUseCase {
    GetSingleStockResponse getStocks(GetSingleStockRequest stockSymbol);
}
