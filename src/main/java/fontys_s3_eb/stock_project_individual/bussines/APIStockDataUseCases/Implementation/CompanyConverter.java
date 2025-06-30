package fontys_s3_eb.stock_project_individual.bussines.APIStockDataUseCases.Implementation;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.CompanyEntity;
import fontys_s3_eb.stock_project_individual.domain.Company;

public class CompanyConverter {
    public static Company convert(CompanyEntity companyEntity)
    {
        return Company.builder()
                .companyName(companyEntity.getCompanyName())
                .city(companyEntity.getCity())
                .currencyName(companyEntity.getCurrencyName())
                .companyURL(companyEntity.getCompanyURL())
                .description(companyEntity.getDescription())
                .category(companyEntity.getCategory())
                .logoURL(companyEntity.getLogoURL())
                .stockSymbol(companyEntity.getStockSymbol())
                .build();

    }
}
