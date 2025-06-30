package fontys_s3_eb.stock_project_individual.UnitTesting.ServiceLayer.Stock;
import fontys_s3_eb.stock_project_individual.Persistence.Entity.StockEntity;
import fontys_s3_eb.stock_project_individual.bussines.StockUseCases.Implementation.GetStockUseCaseImpl;
import fontys_s3_eb.stock_project_individual.domain.StockPackage.GetStockResponse;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetStockUseCaseImplTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private GetStockUseCaseImpl getStockUseCaseImpl;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getStocks_ReturnsCorrectResponse() {
        // Arrange:
        StockEntity stockEntity1 = new StockEntity();
        stockEntity1.setStockId(1L);
        stockEntity1.setCompanyName("Company 3456789");
        stockEntity1.setCurrency("USD");
        stockEntity1.setStockSymbol("COMP34567890");

        StockEntity stockEntity2 = new StockEntity();
        stockEntity2.setStockId(2L);
        stockEntity2.setCompanyName("Company 34567890");
        stockEntity2.setCurrency("EUR");
        stockEntity2.setStockSymbol("COMP4567890");

        List<StockEntity> stockEntities = Arrays.asList(stockEntity1, stockEntity2);

        when(stockRepository.findAll()).thenReturn(stockEntities);

        // Act: call getStocks() method
        GetStockResponse response = getStockUseCaseImpl.getStocks();

        // Assert: check that the response contains the expected stocks
        assertEquals(2, response.getStocks().size());
        assertEquals("Company 3456789", response.getStocks().get(0).getCompanyName());
        assertEquals("Company 34567890", response.getStocks().get(1).getCompanyName());
    }
}
