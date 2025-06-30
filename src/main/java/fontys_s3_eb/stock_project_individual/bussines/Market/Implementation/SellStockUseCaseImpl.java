package fontys_s3_eb.stock_project_individual.bussines.Market.Implementation;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.MarketEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Entity.StockEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Entity.UserEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.MarketRepository;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.StockRepository;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.UserRepository;
import fontys_s3_eb.stock_project_individual.bussines.Market.UseCase.SellStockUseCase;
import fontys_s3_eb.stock_project_individual.domain.MarketPackage.SellStockRequest;
import fontys_s3_eb.stock_project_individual.domain.MarketPackage.SellStockResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SellStockUseCaseImpl implements SellStockUseCase {

    private final MarketRepository marketRepository;
    private final StockRepository stockRepository;
    private final UserRepository userRepository;

    @Override
    public SellStockResponse sellStock(SellStockRequest sellStockRequest) {
        StockEntity stock = stockRepository.findById(sellStockRequest.getStockId()).orElse(null);
        UserEntity user = userRepository.findById(sellStockRequest.getUserId()).orElse(null);

        try {
            Long stockBought = Optional.ofNullable(
                            marketRepository.countUserStockByTransactionType(MarketEntity.TransactionType.BUY,
                                    sellStockRequest.getUserId(),
                                    sellStockRequest.getStockId()))
                    .orElse(0L);

            Long stockSold = Optional.ofNullable(
                            marketRepository.countUserStockByTransactionType(MarketEntity.TransactionType.SELL,
                                    sellStockRequest.getUserId(),
                                    sellStockRequest.getStockId()))
                    .orElse(0L);

            if (stockBought >= stockSold + sellStockRequest.getQuantity()) {
                MarketEntity marketEntity = MarketEntity.builder()
                        .stock(stock)
                        .user(user)
                        .quantity(sellStockRequest.getQuantity())
                        .price(sellStockRequest.getPrice())
                        .transactionType(MarketEntity.TransactionType.SELL)
                        .createdAt(new Date())
                        .build();

                MarketEntity market = marketRepository.save(marketEntity);

                return SellStockResponse.builder()
                        .market(market)
                        .success(true)
                        .message("Stock sold successfully!")
                        .build();
            } else {
                return SellStockResponse.builder()
                        .success(false)
                        .message("Insufficient stock to sell.")
                        .build();
            }
        } catch (Exception e) {
            return SellStockResponse.builder()
                    .success(false)
                    .message("An error occurred while processing the request.")
                    .build();
        }
    }

}
