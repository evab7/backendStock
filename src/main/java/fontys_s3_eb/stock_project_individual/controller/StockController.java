package fontys_s3_eb.stock_project_individual.controller;
import fontys_s3_eb.stock_project_individual.bussines.StockUseCases.UseCase.*;
import fontys_s3_eb.stock_project_individual.domain.StockPackage.*;
import fontys_s3_eb.stock_project_individual.domain.StockPackage.CreateStockResponse;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/stock")
@AllArgsConstructor
public class StockController {

    private final AddStockUseCase addStockUseCase;
    private final DeleteStockUseCase deleteStockUseCase;
    private final GetStockUseCase getStockUseCase;
    private final GetSingleStockUseCase getSingleStockUseCase;
    private final EditStockUseCase editStockUseCase;

    @PostMapping()
    @RolesAllowed({"ADMIN"})
    public ResponseEntity<CreateStockResponse> addStock(@RequestBody @Valid CreateStockRequest request) {
        CreateStockResponse response = addStockUseCase.addStock(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @RolesAllowed({"ADMIN"})
    @PutMapping("/{stockSymbol}")
    public ResponseEntity<EditStockResponse> editStock( @PathVariable String stockSymbol, @RequestBody @Valid EditStockRequest editStockRequest) {
        editStockRequest.setStockSymbol(stockSymbol);

        EditStockResponse response = editStockUseCase.editStock(editStockRequest);

        return ResponseEntity.ok(response);
    }

    @RolesAllowed({"ADMIN"})
    @DeleteMapping("{stockId}")
    public ResponseEntity<Void> deleteStock(@PathVariable Integer stockId) {
        deleteStockUseCase.deleteStock(stockId);
        return ResponseEntity.noContent().build();
    }
    @RolesAllowed({"ADMIN", "USER"})
    @GetMapping
    public ResponseEntity<GetStockResponse> getAllStocks() {
        GetStockResponse response = getStockUseCase.getStocks();
        return ResponseEntity.ok(response);
    }
    @RolesAllowed({"ADMIN", "USER"})
    @GetMapping("/{stockSymbol}")
    public ResponseEntity<GetSingleStockResponse> getSingleStock(@PathVariable @Valid GetSingleStockRequest stockSymbol) {
        GetSingleStockResponse response = getSingleStockUseCase.getStocks(stockSymbol);
        return ResponseEntity.ok(response);
    }
}
