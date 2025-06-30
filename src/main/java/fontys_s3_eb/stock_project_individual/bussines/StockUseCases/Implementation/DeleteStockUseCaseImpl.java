package fontys_s3_eb.stock_project_individual.bussines.StockUseCases.Implementation;
import fontys_s3_eb.stock_project_individual.bussines.StockUseCases.UseCase.DeleteStockUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.StockRepository;

@AllArgsConstructor
@Service
public class DeleteStockUseCaseImpl implements DeleteStockUseCase {

    private final StockRepository stockRepository;

    @Override
    public void deleteStock(long stockId) {
        this.stockRepository.deleteById(stockId);
    }
}
