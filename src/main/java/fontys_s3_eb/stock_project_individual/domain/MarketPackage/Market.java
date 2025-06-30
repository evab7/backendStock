package fontys_s3_eb.stock_project_individual.domain.MarketPackage;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.MarketEntity;
import fontys_s3_eb.stock_project_individual.domain.StockPackage.Stock;
import fontys_s3_eb.stock_project_individual.domain.UserPackage.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class Market {
    private Long marketId;

    private Long userId;

    private Stock stock;

    private Long quantity;

    private BigDecimal price;

    private MarketEntity.TransactionType transactionType;

    private Date createdAt;

    public enum TransactionType {
        BUY, SELL
    }
}
