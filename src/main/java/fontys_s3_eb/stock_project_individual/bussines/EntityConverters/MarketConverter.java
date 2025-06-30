package fontys_s3_eb.stock_project_individual.bussines.EntityConverters;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.MarketEntity;
import fontys_s3_eb.stock_project_individual.domain.MarketPackage.Market;

public class MarketConverter {
    public static Market convert(MarketEntity market)
    {
        return Market.builder()
                .stock(StockConverter.convert(market.getStock()))
                .userId(market.getUser().getUserId())
                .price(market.getPrice())
                .quantity(market.getQuantity())
                .marketId(market.getMarketId())
                .createdAt(market.getCreatedAt())
                .transactionType(market.getTransactionType())
                .build();
    }
}
