//package fontys_s3_eb.stock_project_individual;
//
//import fontys_s3_eb.stock_project_individual.bussines.APIStockDataUseCases.Implementation.GetStockInfoFromAPIImpl;
//import fontys_s3_eb.stock_project_individual.domain.APIStockData.GetStockAPIRequest;
//import fontys_s3_eb.stock_project_individual.domain.APIStockData.GetStockAPIResponse;
//import fontys_s3_eb.stock_project_individual.domain.Stock.CreateStockResponse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.*;
//import org.springframework.beans.factory.annotation.Value;
//
//import java.net.URI;
//import java.net.http.*;
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//class TestingHTTP {
//
//    @Mock
//    private HttpClient mockHttpClient;
//
//    @Mock
//    private HttpResponse<String> mockResponse;
//
//    @InjectMocks
//    private GetStockInfoFromAPIImpl getStockInfoFromAPIImpl;
//
//    @Value("${POLYGON_API_KEY}")
//    private String polygonApiKey;
//
//    @BeforeEach
//    public void setUp() {
//        // Initialize the mocks
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testGetStockInfoFromAPI() throws Exception {
//        // Given
//        String mockJsonResponse = "{ \"results\": [ { \"t\": \"1623456000000\", \"c\": 134.56 } ] }";
//        String url = "https://api.polygon.io/v3/stocks/" + polygonApiKey;
//
//        // Mock HttpClient behavior
//        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
//                .thenReturn(mockResponse);
//        when(mockResponse.body()).thenReturn(mockJsonResponse);
//
//        GetStockAPIRequest getStockAPIRequest = GetStockAPIRequest.builder()
//                .stockSymbol("AAPL")
//                .build();
//        // Act
//        GetStockAPIResponse response = getStockInfoFromAPIImpl.getStockInfoAPI(getStockAPIRequest);
//
//        // Assert
//     //   assertTrue(response., "The method should return true"); promijenjeno zbog implementacije
//        verify(mockHttpClient, times(1)).send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString()));
//    }
//}
