package fontys_s3_eb.stock_project_individual.UnitTesting.ServiceLayer.Company;

import fontys_s3_eb.stock_project_individual.bussines.APIStockDataUseCases.Implementation.GetStockInfoFromAPIImpl;
import fontys_s3_eb.stock_project_individual.domain.APIStockData.GetStockAPIRequest;
import fontys_s3_eb.stock_project_individual.domain.APIStockData.GetStockAPIResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class GetStockFromAPITest {

    @Mock
    private HttpClient mockHttpClient;

    @InjectMocks
    private GetStockInfoFromAPIImpl getStockInfoFromAPIImpl;

    @Mock
    private HttpResponse<String> mockHttpResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getStockInfoAPI_success() throws Exception {
        // Arrange
        GetStockAPIRequest stockRequest = GetStockAPIRequest.builder()
                .stockSymbol("AAPL")
                .build();


        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockHttpResponse);

        // Act
        GetStockAPIResponse response = getStockInfoFromAPIImpl.getStockInfoAPI(stockRequest);

        // Assert
        assertNotNull(response);
        assertEquals(502, response.getStockData().size());
    }

    @Test
    void getStockInfoAPI_noResults_wrong_stockSymbol() throws Exception {
        // Arrange
        GetStockAPIRequest stockRequest = GetStockAPIRequest.builder()
                .stockSymbol("APL")
                .build();


        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockHttpResponse);

        // Act
        GetStockAPIResponse response = getStockInfoFromAPIImpl.getStockInfoAPI(stockRequest);

        // Assert
        assertNotNull(response);
        assertTrue(response.getStockData().isEmpty());
    }


}
