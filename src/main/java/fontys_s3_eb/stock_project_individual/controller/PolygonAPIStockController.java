package fontys_s3_eb.stock_project_individual.controller;


import fontys_s3_eb.stock_project_individual.bussines.APIStockDataUseCases.UseCase.GetCompanyInformationUseCase;
import fontys_s3_eb.stock_project_individual.bussines.APIStockDataUseCases.UseCase.GetStockInfoFromAPlUseCase;
import fontys_s3_eb.stock_project_individual.bussines.PortfolioUseCases.UseCase.GetStocksFromPortolioUseCase;
import fontys_s3_eb.stock_project_individual.domain.APIStockData.GetCompanyInformationRequest;
import fontys_s3_eb.stock_project_individual.domain.APIStockData.GetCompanyInformationResponse;
import fontys_s3_eb.stock_project_individual.domain.APIStockData.GetStockAPIRequest;
import fontys_s3_eb.stock_project_individual.domain.APIStockData.GetStockAPIResponse;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/polygonAPI")
@AllArgsConstructor
public class PolygonAPIStockController {

    private final GetStocksFromPortolioUseCase getStocksFromPortolioUseCase;
    private final GetStockInfoFromAPlUseCase getStockInfoFromAPlUseCase;
    private final GetCompanyInformationUseCase getCompanyInformationUseCase;


    @RolesAllowed({"ADMIN", "USER"})
    @GetMapping("/{stockSymbol}")
    public ResponseEntity<GetStockAPIResponse> getAllStockInfoAPI( GetStockAPIRequest request) {
        GetStockAPIResponse response = getStockInfoFromAPlUseCase.getStockInfoAPI(request);
        return ResponseEntity.ok(response);
    }

    @RolesAllowed({"ADMIN", "USER"})
    @GetMapping
    public ResponseEntity<GetCompanyInformationResponse> getCompanyInformation( GetCompanyInformationRequest request) {
        GetCompanyInformationResponse response = getCompanyInformationUseCase.getCompanyInformation(request);
        return ResponseEntity.ok(response);
    }

}
