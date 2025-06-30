package fontys_s3_eb.stock_project_individual.controller;

import fontys_s3_eb.stock_project_individual.bussines.Market.UseCase.BuyStockUseCase;
import fontys_s3_eb.stock_project_individual.bussines.Market.UseCase.GetMarketInfoUseCase;
import fontys_s3_eb.stock_project_individual.bussines.Market.UseCase.GetUserMarketInfoUseCase;
import fontys_s3_eb.stock_project_individual.bussines.Market.UseCase.SellStockUseCase;
import fontys_s3_eb.stock_project_individual.domain.MarketPackage.*;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/market")
@AllArgsConstructor
public class MarketController {

    private final BuyStockUseCase buyStockUseCase;
    private final SellStockUseCase sellStockUseCase;
    private final GetMarketInfoUseCase getMarketInfoUseCase;
    private final GetUserMarketInfoUseCase getUserMarketInfoUseCase;

    @PostMapping("/buy")
    @RolesAllowed({"USER"})
    public ResponseEntity<BuyStockResponse> buyStock(@RequestBody @Valid BuyStockRequest request) {
        BuyStockResponse response = buyStockUseCase.buyStock(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PostMapping("/sell")
    @RolesAllowed({"USER"})
    public ResponseEntity<SellStockResponse> sellStock(@RequestBody @Valid SellStockRequest request) {
        SellStockResponse response = sellStockUseCase.sellStock(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @RolesAllowed({"ADMIN"})
    @GetMapping
    public ResponseEntity<GetMarketInfoResponse> getAllMarketInfo() {
        GetMarketInfoResponse response = getMarketInfoUseCase.getMarketInfo();
        return ResponseEntity.ok(response);
    }
    @RolesAllowed({"USER"})
    @GetMapping("/{userId}")
    public ResponseEntity<GetUserMarketInfoResponse> getUserMarket(@PathVariable @Valid Long userId) {
        GetUserMarketInfoResponse response = getUserMarketInfoUseCase.getUserMarketInfo(userId);
        return ResponseEntity.ok(response);
    }
}
