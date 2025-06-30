package fontys_s3_eb.stock_project_individual.domain.MarketPackage;
import fontys_s3_eb.stock_project_individual.domain.StockPackage.Stock;
import fontys_s3_eb.stock_project_individual.domain.UserPackage.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyStockRequest {

    @NotNull
    private Long userId;
    @NotNull
    private Long stockId;
    @NotNull
    private Long quantity;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Date date;
}
