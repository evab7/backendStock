package fontys_s3_eb.stock_project_individual.UnitTesting.ConvertersTest;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.CompanyEntity;
import fontys_s3_eb.stock_project_individual.bussines.APIStockDataUseCases.Implementation.CompanyConverter;
import fontys_s3_eb.stock_project_individual.domain.Company;
import org.junit.jupiter.api.Test;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CompanyConverterTest {
    @Test
    void convert_FullyPopulatedEntity_MapsFieldsCorrectly() {
        // Arrange
        CompanyEntity companyEntity = CompanyEntity.builder()
                .companyName("Test Company")
                .city("Test City")
                .currencyName("USD")
                .companyURL("http://test.com")
                .description("Test Description")
                .category("Tech")
                .logoURL("http://test.com/logo.png")
                .stockSymbol("TST")
                .build();

        // Act
        Company company = CompanyConverter.convert(companyEntity);

        // Assert
        assertNotNull(company);
        assertEquals("Test Company", company.getCompanyName());
        assertEquals("Test City", company.getCity());
        assertEquals("USD", company.getCurrencyName());
        assertEquals("http://test.com", company.getCompanyURL());
        assertEquals("Test Description", company.getDescription());
        assertEquals("Tech", company.getCategory());
        assertEquals("http://test.com/logo.png", company.getLogoURL());
        assertEquals("TST", company.getStockSymbol());
    }

    @Test
    void convert_EntityWithNullFields_MapsNullFieldsCorrectly() {
        // Arrange
        CompanyEntity companyEntity = CompanyEntity.builder()
                .companyName(null)
                .city(null)
                .currencyName(null)
                .companyURL(null)
                .description(null)
                .category(null)
                .logoURL(null)
                .stockSymbol(null)
                .build();

        // Act
        Company company = CompanyConverter.convert(companyEntity);

        // Assert
        assertNotNull(company);
        assertNull(company.getCompanyName());
        assertNull(company.getCity());
        assertNull(company.getCurrencyName());
        assertNull(company.getCompanyURL());
        assertNull(company.getDescription());
        assertNull(company.getCategory());
        assertNull(company.getLogoURL());
        assertNull(company.getStockSymbol());
    }

    @Test
    void convert_EntityWithEmptyStrings_MapsFieldsCorrectly() {
        // Arrange
        CompanyEntity companyEntity = CompanyEntity.builder()
                .companyName("")
                .city("")
                .currencyName("")
                .companyURL("")
                .description("")
                .category("")
                .logoURL("")
                .stockSymbol("")
                .build();

        // Act
        Company company = CompanyConverter.convert(companyEntity);

        // Assert
        assertNotNull(company);
        assertEquals("", company.getCompanyName());
        assertEquals("", company.getCity());
        assertEquals("", company.getCurrencyName());
        assertEquals("", company.getCompanyURL());
        assertEquals("", company.getDescription());
        assertEquals("", company.getCategory());
        assertEquals("", company.getLogoURL());
        assertEquals("", company.getStockSymbol());
    }
}
