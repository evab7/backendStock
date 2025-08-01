package fontys_s3_eb.stock_project_individual.bussines.APIStockDataUseCases.UseCase;

import fontys_s3_eb.stock_project_individual.domain.APIStockData.GetCompanyInformationRequest;
import fontys_s3_eb.stock_project_individual.domain.APIStockData.GetCompanyInformationResponse;


public interface GetCompanyInformationUseCase {
    GetCompanyInformationResponse getCompanyInformation (GetCompanyInformationRequest companyRequest);
}
