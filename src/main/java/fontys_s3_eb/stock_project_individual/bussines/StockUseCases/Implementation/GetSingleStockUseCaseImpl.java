package fontys_s3_eb.stock_project_individual.bussines.StockUseCases.Implementation;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.StockEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.StockRepository;
import fontys_s3_eb.stock_project_individual.bussines.EntityConverters.StockConverter;
import fontys_s3_eb.stock_project_individual.bussines.StockUseCases.UseCase.GetSingleStockUseCase;
import fontys_s3_eb.stock_project_individual.domain.StockPackage.GetSingleStockRequest;
import fontys_s3_eb.stock_project_individual.domain.StockPackage.GetSingleStockResponse;
import fontys_s3_eb.stock_project_individual.domain.StockPackage.Stock;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class GetSingleStockUseCaseImpl implements GetSingleStockUseCase {
    private StockRepository stockRepository;

    @Override
    public GetSingleStockResponse getStocks(GetSingleStockRequest stockSymbol) {

        StockEntity result = stockRepository.findByStockSymbol(stockSymbol.getStockSymbol());
        final GetSingleStockResponse response = new GetSingleStockResponse();
        Stock stock = StockConverter.convert(result);
        response.setStock(stock);

        return response;
    }
}
