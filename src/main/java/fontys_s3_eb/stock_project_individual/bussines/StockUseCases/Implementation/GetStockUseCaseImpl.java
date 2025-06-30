package fontys_s3_eb.stock_project_individual.bussines.StockUseCases.Implementation;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.StockEntity;
import fontys_s3_eb.stock_project_individual.bussines.EntityConverters.StockConverter;
import fontys_s3_eb.stock_project_individual.bussines.StockUseCases.UseCase.GetStockUseCase;
import fontys_s3_eb.stock_project_individual.domain.StockPackage.GetStockResponse;
import fontys_s3_eb.stock_project_individual.domain.StockPackage.Stock;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.StockRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class GetStockUseCaseImpl implements GetStockUseCase {

    private final StockRepository stockRepository;


    @Override
    public GetStockResponse getStocks() {
        List<StockEntity> results;
        results = stockRepository.findAll();


        final GetStockResponse response = new GetStockResponse();
        List<Stock> stocks = results
                .stream()
                .map(StockConverter::convert)
                .toList();
        response.setStocks(stocks);

        return response;
    }
}
