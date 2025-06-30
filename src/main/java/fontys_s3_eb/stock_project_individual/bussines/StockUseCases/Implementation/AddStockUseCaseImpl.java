package fontys_s3_eb.stock_project_individual.bussines.StockUseCases.Implementation;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.StockEntity;
import fontys_s3_eb.stock_project_individual.bussines.StockUseCases.UseCase.AddStockUseCase;
import fontys_s3_eb.stock_project_individual.domain.StockPackage.CreateStockRequest;
import fontys_s3_eb.stock_project_individual.domain.StockPackage.CreateStockResponse;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.StockRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AddStockUseCaseImpl implements AddStockUseCase {

    private final StockRepository stockRepository;



    @Override
    public CreateStockResponse addStock(CreateStockRequest request) {

        StockEntity stock = saveStock(request);
        return CreateStockResponse.builder()
                .stockID(stock.getStockId())
                .build();
    }

    private StockEntity saveStock(CreateStockRequest request) {
        try {
            StockEntity newStock = StockEntity.builder()
                    .companyName(request.getCompanyName())
                    .currency(request.getCurrency())
                    .description(request.getDescription())
                    .stockSymbol(request.getStockSymbol())
                    .build();
            return stockRepository.save(newStock);
        }
        catch (Exception e) {
            StockEntity newStock = StockEntity.builder()
                    .companyName(request.getCompanyName())
                    .currency(request.getCurrency())
                    .description(request.getDescription())
                    .stockSymbol(request.getStockSymbol())
                    .build();
            return stockRepository.save(newStock);
        }


    }
}
