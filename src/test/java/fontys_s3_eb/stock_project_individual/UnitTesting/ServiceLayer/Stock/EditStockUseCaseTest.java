package fontys_s3_eb.stock_project_individual.UnitTesting.ServiceLayer.Stock;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.StockEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.StockRepository;
import fontys_s3_eb.stock_project_individual.bussines.StockUseCases.Implementation.EditStockUseCaseImpl;
import fontys_s3_eb.stock_project_individual.domain.StockPackage.EditStockRequest;
import fontys_s3_eb.stock_project_individual.domain.StockPackage.EditStockResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;@ExtendWith(MockitoExtension.class)
public class EditStockUseCaseTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private EditStockUseCaseImpl editStockUseCase;

    private EditStockRequest editStockRequest;
    private StockEntity existingStock;

    @BeforeEach
    void setUp() {
        editStockRequest = EditStockRequest.builder()
                .stockSymbol("AAPL")
                .companyName("Apple Inc.")
                .description("Technology company")
                .currency("USD")
                .build();

        existingStock = StockEntity.builder()
                .stockId(1L)
                .stockSymbol("AAPL")
                .companyName("Old Apple")
                .description("Old description")
                .currency("EUR")
                .build();
    }

    @Test
    void editStock_SuccessfulUpdate_ReturnsCorrectResponse() {
        // Arrange
        when(stockRepository.findByStockSymbol("AAPL")).thenReturn(existingStock);
        when(stockRepository.save(any(StockEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        EditStockResponse response = editStockUseCase.editStock(editStockRequest);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getStockID());
        verify(stockRepository, times(1)).findByStockSymbol("AAPL");
        verify(stockRepository, times(1)).save(existingStock);

        // Verify that the stock entity was updated correctly
        assertEquals("Apple Inc.", existingStock.getCompanyName());
        assertEquals("Technology company", existingStock.getDescription());
        assertEquals("USD", existingStock.getCurrency());
    }

    @Test
    void editStock_FailureToFindStock_ReturnsErrorMessage() {
        // Arrange
        when(stockRepository.findByStockSymbol("AAPL")).thenThrow(new RuntimeException("Stock not found"));

        // Act
        EditStockResponse response = editStockUseCase.editStock(editStockRequest);

        // Assert
        assertNotNull(response);
        assertNull(response.getStockID());
        assertTrue(response.getErrorMessage().contains("Failed to edit stock: Stock not found"));
        verify(stockRepository, times(1)).findByStockSymbol("AAPL");
        verify(stockRepository, times(0)).save(any(StockEntity.class));
    }
}
