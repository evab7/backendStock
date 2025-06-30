package fontys_s3_eb.stock_project_individual.controller;

import fontys_s3_eb.stock_project_individual.bussines.PortfolioUseCases.UseCase.AddStockToPortoflioUseCase;
import fontys_s3_eb.stock_project_individual.bussines.PortfolioUseCases.UseCase.GetStocksFromPortolioUseCase;
import fontys_s3_eb.stock_project_individual.domain.PortfolioPackage.AddStockToPortfolioRequest;
import fontys_s3_eb.stock_project_individual.domain.PortfolioPackage.AddStockToPortfolioResponse;
import fontys_s3_eb.stock_project_individual.domain.PortfolioPackage.GetPortfolioStockRequest;
import fontys_s3_eb.stock_project_individual.domain.PortfolioPackage.GetPortfolioStockResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/portfolio")
@AllArgsConstructor
public class PortfolioController {

    private final AddStockToPortoflioUseCase addStockToPortoflioUseCase;
    private final GetStocksFromPortolioUseCase getStocksFromPortolioUseCase;

    @PostMapping("/add")
    public ResponseEntity<AddStockToPortfolioResponse> addToPortfolio(@RequestBody @Valid AddStockToPortfolioRequest request) {
        AddStockToPortfolioResponse response = addStockToPortoflioUseCase.addStockToPortfolioResponse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(("/{userId}"))
    public ResponseEntity<GetPortfolioStockResponse> getPortfolioStock(@Valid GetPortfolioStockRequest request) {
        GetPortfolioStockResponse response = getStocksFromPortolioUseCase.getStocksFromPortolio(request);
        return ResponseEntity.ok(response);
    }



}
