package fontys_s3_eb.stock_project_individual.UnitTesting.ServiceLayer.Stock;

import fontys_s3_eb.stock_project_individual.Persistence.Implementation.StockRepository;
import fontys_s3_eb.stock_project_individual.bussines.StockUseCases.Implementation.DeleteStockUseCaseImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteStockUseCaseImplTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private DeleteStockUseCaseImpl deleteStockUseCaseImpl;

    @Test
    void deleteStock_CallsRepositoryWithCorrectId() {
        // Arrange
        long stockId = 1;

        // Act
        deleteStockUseCaseImpl.deleteStock(stockId);

        // Assert
        verify(stockRepository, times(1)).deleteById(stockId);
    }

    }


