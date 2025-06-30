package fontys_s3_eb.stock_project_individual.bussines.StockUseCases.UseCase;

import fontys_s3_eb.stock_project_individual.domain.StockPackage.EditStockRequest;
import fontys_s3_eb.stock_project_individual.domain.StockPackage.EditStockResponse;

public interface EditStockUseCase {
    EditStockResponse editStock(EditStockRequest editStockRequest);
}
