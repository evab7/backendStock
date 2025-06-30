package fontys_s3_eb.stock_project_individual.bussines.APIStockDataUseCases.UseCase;

import fontys_s3_eb.stock_project_individual.domain.APIStockData.GetStockAPIRequest;
import fontys_s3_eb.stock_project_individual.domain.APIStockData.GetStockAPIResponse;

public interface GetStockInfoFromAPlUseCase {
    GetStockAPIResponse getStockInfoAPI(GetStockAPIRequest stockRequest);
}
