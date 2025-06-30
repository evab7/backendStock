package fontys_s3_eb.stock_project_individual.domain.PortfolioPackage;

import fontys_s3_eb.stock_project_individual.domain.StockPackage.Stock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class Portfolio {
    private Stock stock;
    private String comment;
    private Date date;
}
