package fontys_s3_eb.stock_project_individual.UnitTesting.ServiceLayer.Stock;
import fontys_s3_eb.stock_project_individual.Persistence.Entity.StockEntity;
import fontys_s3_eb.stock_project_individual.bussines.StockUseCases.Implementation.AddStockUseCaseImpl;
import fontys_s3_eb.stock_project_individual.domain.StockPackage.CreateStockRequest;
import fontys_s3_eb.stock_project_individual.domain.StockPackage.CreateStockResponse;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddStockUseCaseImplTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private AddStockUseCaseImpl addStockUseCaseImpl;

    private CreateStockRequest request;

    @BeforeEach
    void setUp() {
        request = CreateStockRequest.builder()
                .companyName("Company Company")
                .currency("USD")
                .description("Guees : Company")
                .stockSymbol("CMPNY")
                .build();
    }

    @Test
    void addStock_SuccessfulSave_ReturnsCorrectResponse() {
        // Arrange
        StockEntity mockStockEntity = StockEntity.builder()
                .stockId(1L)
                .companyName("Not company Not company ")
                .currency("USD")
                .description("Company")
                .stockSymbol("TST")
                .build();

        when(stockRepository.save(any(StockEntity.class))).thenReturn(mockStockEntity);

        // Act
        CreateStockResponse response = addStockUseCaseImpl.addStock(request);

        // Assert
        assertEquals(1L, response.getStockID());
        verify(stockRepository, times(1)).save(any(StockEntity.class));
    }
    @Test
    void addStock_EmptyFields_ThrowsNullPointerException() {
        // Arrange:
        CreateStockRequest emptyRequest = CreateStockRequest.builder()
                .companyName("")
                .currency("")
                .description("")
                .stockSymbol("")
                .build();

        Exception exception = null;
        try {
            addStockUseCaseImpl.addStock(emptyRequest);
        } catch (Exception e) {
            exception = e;
        }

        // Assert:
        assertNotNull(exception);
        assertTrue(exception instanceof NullPointerException);



    }




}