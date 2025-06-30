package fontys_s3_eb.stock_project_individual.bussines.Market.Implementation;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.MarketEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Entity.StockEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Entity.UserEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.MarketRepository;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.StockRepository;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.UserRepository;
import fontys_s3_eb.stock_project_individual.bussines.Market.UseCase.BuyStockUseCase;
import fontys_s3_eb.stock_project_individual.domain.MarketPackage.BuyStockRequest;
import fontys_s3_eb.stock_project_individual.domain.MarketPackage.BuyStockResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class BuyStockUseCaseImpl implements BuyStockUseCase {

    private final MarketRepository marketRepository;
    private final StockRepository stockRepository;
    private final UserRepository userRepository;

    @Override
    public BuyStockResponse buyStock(BuyStockRequest buyStockRequest) {

        StockEntity stock = stockRepository.findById(buyStockRequest.getStockId()).orElse(null);
        UserEntity user = userRepository.findById(buyStockRequest.getUserId()).orElse(null);

        if (stock == null || user == null) {
            return BuyStockResponse.builder()
                    .market(null)
                    .build();
        }
        MarketEntity marketEntity = MarketEntity.builder()
                .stock(stock)
                .user(user)
                .quantity(buyStockRequest.getQuantity())
                .price(buyStockRequest.getPrice())
                .transactionType(MarketEntity.TransactionType.BUY)
                .createdAt(new Date())
                .build();

        MarketEntity market = marketRepository.save(marketEntity);

        return BuyStockResponse.builder()
                .market(market)
                .build();
    }
}


