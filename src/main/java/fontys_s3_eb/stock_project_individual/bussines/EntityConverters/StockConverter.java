package fontys_s3_eb.stock_project_individual.bussines.EntityConverters;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.StockEntity;
import fontys_s3_eb.stock_project_individual.domain.StockPackage.Stock;


public final class StockConverter {

    public static Stock convert(StockEntity stock)
    {
        return Stock.builder()
                .stockId(stock.getStockId())
                .companyName(stock.getCompanyName())
                .currency(stock.getCurrency())
                .description(stock.getDescription())
                .stockSymbol(stock.getStockSymbol())
                .build();
    }



}

