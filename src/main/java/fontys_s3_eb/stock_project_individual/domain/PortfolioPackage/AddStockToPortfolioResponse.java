package fontys_s3_eb.stock_project_individual.domain.PortfolioPackage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddStockToPortfolioResponse {
    private String success;
}
