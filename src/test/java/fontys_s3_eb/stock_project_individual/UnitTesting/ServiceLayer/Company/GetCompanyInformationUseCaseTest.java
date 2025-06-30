package fontys_s3_eb.stock_project_individual.UnitTesting.ServiceLayer.Company;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.CompanyEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.CompanyRepository;
import fontys_s3_eb.stock_project_individual.bussines.APIStockDataUseCases.Implementation.CompanyConverter;
import fontys_s3_eb.stock_project_individual.bussines.APIStockDataUseCases.Implementation.GetCompanyInformationUseCaseImpl;
import fontys_s3_eb.stock_project_individual.bussines.APIStockDataUseCases.UseCase.GetCompanyInformationUseCase;
import fontys_s3_eb.stock_project_individual.bussines.Exceptions.StockExistsException;
import fontys_s3_eb.stock_project_individual.domain.APIStockData.GetCompanyInformationRequest;
import fontys_s3_eb.stock_project_individual.domain.APIStockData.GetCompanyInformationResponse;
import fontys_s3_eb.stock_project_individual.domain.Company;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GetCompanyInformationUseCaseTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse<String> httpResponse;

    @Mock
    private CompanyConverter companyConverter;

    @InjectMocks
    private GetCompanyInformationUseCaseImpl getCompanyInformationUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCompanyInformation_CompanyExistsInDatabase() {
        // Arrange
        CompanyEntity existingEntity = CompanyEntity.builder()
                .companyName("Test Company")
                .stockSymbol("AAPL")
                .city("Test City")
                .currencyName("USD")
                .companyURL("http://example.com")
                .description("Test Description")
                .category("Tech")
                .logoURL("http://example.com/logo.png")
                .build();

        when(companyRepository.findByStockSymbol("AAPL")).thenReturn(existingEntity);



       Company company = CompanyConverter.convert(existingEntity);

        GetCompanyInformationRequest request = new GetCompanyInformationRequest("AAPL", null);

        // Act
        GetCompanyInformationResponse response = getCompanyInformationUseCase.getCompanyInformation(request);

        // Assert
        assertNotNull(response);
        assertEquals(company, response.getCompanyInformation());
        verify(companyRepository, never()).save(any(CompanyEntity.class));
    }


    @Test
    void testGetCompanyInformation_CompanyNotInDatabase_ApiSuccess() throws Exception {
        // Arrange
        when(companyRepository.findByStockSymbol("AAPL")).thenReturn(null);

        JSONObject apiResponse = new JSONObject();
        apiResponse.put("results", new JSONObject()
                .put("active", true)
                .put("currency_name", "USD")
                .put("homepage_url", "http://example.com")
                .put("description", "Test Description")
                .put("sic_description", "Tech")
                .put("name", "Test Company")
                .put("ticker", "AAPL")
                .put("address", new JSONObject().put("city", "Test City"))
                .put("branding", new JSONObject().put("icon_url", "http://example.com/logo.png"))
        );

        when(httpResponse.body()).thenReturn(apiResponse.toString());
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

        CompanyEntity savedEntity = CompanyEntity.builder()
                .companyName("Apple Inc.")
                .active(false)
                .city("CUPERTINO")
                .currencyName("usd")
                .companyURL("https://www.apple.com")
                .description("Apple is among the largest companies in the world, with a broad portfolio of hardware and software products targeted at consumers and businesses. Apple's iPhone makes up a majority of the firm sales, and Apple's other products like Mac, iPad, and Watch are designed around the iPhone as the focal point of an expansive software ecosystem. Apple has progressively worked to add new applications, like streaming video, subscription bundles, and augmented reality. The firm designs its own software and semiconductors while working with subcontractors like Foxconn and TSMC to build its products and chips. Slightly less than half of Apple's sales come directly through its flagship stores, with a majority of sales coming indirectly through partnerships and distribution.")
                .category("ELECTRONIC COMPUTERS")
                .logoURL("https://api.polygon.io/v1/reference/company-branding/YXBwbGUuY29t/images/2024-10-01_icon.png")
                .stockSymbol("AAPL")
                .build();

        CompanyEntity companyEntity = CompanyEntity.builder()
                .companyName("Apple Inc.")
                .active(false)
                .city("CUPERTINO")
                .currencyName("usd")
                .companyURL("https://www.apple.com")
                .description("Test Description")
                .category("ELECTRONIC COMPUTERS")
                .logoURL("https://api.polygon.io/v1/reference/company-branding/YXBwbGUuY29t/images/2024-10-01_icon.png")
                .stockSymbol("AAPL")
                .build();

        when(companyRepository.save(any(CompanyEntity.class))).thenReturn(savedEntity);
        when(companyRepository.save(any(CompanyEntity.class))).thenReturn(companyEntity);

        Company company = CompanyConverter.convert(savedEntity);
        Company companySaved = CompanyConverter.convert(companyEntity);

        LocalDate specificDate = LocalDate.of(2025, 1, 7);
        GetCompanyInformationRequest request = new GetCompanyInformationRequest("AAPL", specificDate);

        // Act
        GetCompanyInformationResponse response = getCompanyInformationUseCase.getCompanyInformation(request);

        // Assert
        assertNotNull(response);
        assertEquals(company, response.getCompanyInformation());
        assertNotEquals(companySaved, response.getCompanyInformation());


        verify(companyRepository, times(1)).save(any(CompanyEntity.class));

    }


    @Test
    void testGetCompanyInformation_CompanyNotInDatabase_ApiThrowsException() throws Exception {
        // Arrange
        when(companyRepository.findByStockSymbol("sa")).thenReturn(null);
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenThrow(new RuntimeException("API error"));
        LocalDate specificDate = LocalDate.of(2025, 1, 7);
        GetCompanyInformationRequest request = new GetCompanyInformationRequest("sa", specificDate);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () ->
                getCompanyInformationUseCase.getCompanyInformation(request));
        assertEquals("Cannot invoke \"fontys_s3_eb.stock_project_individual.Persistence.Entity.CompanyEntity.getCompanyName()\" because \"companyEntity\" is null", exception.getMessage());
        verify(companyRepository, never()).save(any(CompanyEntity.class));
    }


}
