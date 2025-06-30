package fontys_s3_eb.stock_project_individual.domain.MarketPackage;

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

public class SellStockRequest {
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
