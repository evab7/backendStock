package fontys_s3_eb.stock_project_individual.domain.StockPackage;

import fontys_s3_eb.stock_project_individual.domain.APIStockData.APIStockData;
import fontys_s3_eb.stock_project_individual.domain.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Stock {
    private Long stockId;
    private String companyName;
    private String currency;
    private String description;
    private String stockSymbol;
    private List<APIStockData> pastStockValues;
    private Company company;

    public Stock(String companyName, String currency, String description, String stockSymbol) {
        this.companyName = companyName;
        this.currency = currency;
        this.description = description;
        this.stockSymbol = stockSymbol;

    }
    public void AddPastStockValue(APIStockData pastStockValue) {
        this.pastStockValues.add(pastStockValue);
    }

}
