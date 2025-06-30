package fontys_s3_eb.stock_project_individual.bussines.StockUseCases.Implementation;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.StockEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.StockRepository;
import fontys_s3_eb.stock_project_individual.bussines.StockUseCases.UseCase.EditStockUseCase;
import fontys_s3_eb.stock_project_individual.domain.StockPackage.EditStockRequest;
import fontys_s3_eb.stock_project_individual.domain.StockPackage.EditStockResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EditStockUseCaseImpl implements EditStockUseCase {

    private final StockRepository stockRepository;

    @Override
    public EditStockResponse editStock(EditStockRequest editStockRequest) {
        try {
            StockEntity existingStock = stockRepository.findByStockSymbol(editStockRequest.getStockSymbol());
            existingStock.setCompanyName(editStockRequest.getCompanyName());
            existingStock.setDescription(editStockRequest.getDescription());
            existingStock.setCurrency(editStockRequest.getCurrency());

            StockEntity stockId = stockRepository.save(existingStock);
            return EditStockResponse.builder()
                    .stockID(stockId.getStockId())
                    .build();

        } catch (Exception e) {

            return EditStockResponse.builder()
                    .errorMessage("Failed to edit stock: " + e.getMessage())
                    .build();
        }
    }

}
