package fontys_s3_eb.stock_project_individual.domain.MarketPackage;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.MarketEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellStockResponse {
    private MarketEntity market;
    private String message;
    private boolean success;
}
