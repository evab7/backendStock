package fontys_s3_eb.stock_project_individual.UnitTesting.ConvertersTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.StockEntity;
import fontys_s3_eb.stock_project_individual.bussines.EntityConverters.StockConverter;
import fontys_s3_eb.stock_project_individual.domain.StockPackage.Stock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StockConverterTest {
    @Mock
    private StockEntity mockStockEntity;

    @Test
    public void convert_ShouldConvertValidStockEntityToStock() {
        // Arrange:
        when(mockStockEntity.getStockId()).thenReturn(1L);
        when(mockStockEntity.getCompanyName()).thenReturn("Test Company");
        when(mockStockEntity.getCurrency()).thenReturn("USD");
        when(mockStockEntity.getDescription()).thenReturn("This is a test stock");
        when(mockStockEntity.getStockSymbol()).thenReturn("TST");

        // Act:

        Stock stock = StockConverter.convert(mockStockEntity);

        // Assert:
        assertEquals(1L, stock.getStockId());
        assertEquals("Test Company", stock.getCompanyName());
        assertEquals("USD", stock.getCurrency());
        assertEquals("This is a test stock", stock.getDescription());
        assertEquals("TST", stock.getStockSymbol());
    }

    @Test
    public void convert_ShouldHandleNullFieldsInStockEntity() {
        // Arrange:
        when(mockStockEntity.getStockId()).thenReturn(1L);
        when(mockStockEntity.getCompanyName()).thenReturn(null);
        when(mockStockEntity.getCurrency()).thenReturn(null);
        when(mockStockEntity.getDescription()).thenReturn(null);
        when(mockStockEntity.getStockSymbol()).thenReturn(null);

        // Act:
        Stock stock = StockConverter.convert(mockStockEntity);

        // Assert:
        assertEquals(1L, stock.getStockId());
        assertNull(stock.getCompanyName());
        assertNull(stock.getCurrency());
        assertNull(stock.getDescription());
        assertNull(stock.getStockSymbol());
    }

    @Test
    public void convert_ShouldHandleEmptyFieldsInStockEntity() {
        // Arrange:
        when(mockStockEntity.getStockId()).thenReturn(2L);
        when(mockStockEntity.getCompanyName()).thenReturn("");
        when(mockStockEntity.getCurrency()).thenReturn("");
        when(mockStockEntity.getDescription()).thenReturn("");
        when(mockStockEntity.getStockSymbol()).thenReturn("");

        // Act:
        Stock stock = StockConverter.convert(mockStockEntity);

        // Assert:
        assertEquals(2L, stock.getStockId());
        assertEquals("", stock.getCompanyName());
        assertEquals("", stock.getCurrency());
        assertEquals("", stock.getDescription());
        assertEquals("", stock.getStockSymbol());
    }
}

