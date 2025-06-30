package fontys_s3_eb.stock_project_individual.domain.APIStockData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class APIStockData {

    private double  closePrice; // stock value at market open
    private Date DateTime; // Date of the stock information
    private double  tradingVolume; // will be used for working with liquidity
    private String stockSymbol;
}