package fontys_s3_eb.stock_project_individual.UnitTesting.ServiceLayer.Market;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.MarketEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Entity.StockEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Entity.UserEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.MarketRepository;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.StockRepository;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.UserRepository;
import fontys_s3_eb.stock_project_individual.bussines.Market.Implementation.SellStockUseCaseImpl;
import fontys_s3_eb.stock_project_individual.domain.MarketPackage.SellStockRequest;
import fontys_s3_eb.stock_project_individual.domain.MarketPackage.SellStockResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SellStockUseCaseTest {

    @Mock
    private MarketRepository marketRepository;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SellStockUseCaseImpl sellStockUseCase;

    private StockEntity stockEntity;
    private UserEntity userEntity;
    private SellStockRequest sellStockRequest;

    @BeforeEach
    void setUp() {
        stockEntity = StockEntity.builder()
                .stockId(1L)
                .stockSymbol("AAPL")
                .companyName("Apple Inc.")
                .build();

        userEntity = UserEntity.builder()
                .userId(1L)
                .username("testuser")
                .build();

        sellStockRequest = SellStockRequest.builder()
                .stockId(1L)
                .userId(1L)
                .quantity(5L)
                .price(new BigDecimal("150.0"))
                .build();
    }

    @Test
    void sellStock_ValidRequest_ReturnsSuccessResponse() {
        // Arrange
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stockEntity));
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(marketRepository.countUserStockByTransactionType(MarketEntity.TransactionType.BUY, 1L, 1L)).thenReturn(10L);
        when(marketRepository.countUserStockByTransactionType(MarketEntity.TransactionType.SELL, 1L, 1L)).thenReturn(3L);

        MarketEntity savedMarketEntity = MarketEntity.builder()
                .marketId(1L)
                .stock(stockEntity)
                .user(userEntity)
                .quantity(5L)
                .price(new BigDecimal("150.0"))
                .transactionType(MarketEntity.TransactionType.SELL)
                .createdAt(new Date())
                .build();

        when(marketRepository.save(any(MarketEntity.class))).thenReturn(savedMarketEntity);

        // Act
        SellStockResponse response = sellStockUseCase.sellStock(sellStockRequest);

        // Assert
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals("Stock sold successfully!", response.getMessage());
        assertNotNull(response.getMarket());
        assertEquals(1L, response.getMarket().getMarketId());
        assertEquals(5, response.getMarket().getQuantity());
        assertEquals(0, new BigDecimal("150.0").compareTo(response.getMarket().getPrice()));

        verify(stockRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(1L);
        verify(marketRepository, times(1)).countUserStockByTransactionType(MarketEntity.TransactionType.BUY, 1L, 1L);
        verify(marketRepository, times(1)).countUserStockByTransactionType(MarketEntity.TransactionType.SELL, 1L, 1L);
        verify(marketRepository, times(1)).save(any(MarketEntity.class));
    }

    @Test
    void sellStock_InsufficientStock_ReturnsFailureResponse() {
        // Arrange
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stockEntity));
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(marketRepository.countUserStockByTransactionType(MarketEntity.TransactionType.BUY, 1L, 1L)).thenReturn(5L);
        when(marketRepository.countUserStockByTransactionType(MarketEntity.TransactionType.SELL, 1L, 1L)).thenReturn(2L);

        // Act
        SellStockResponse response = sellStockUseCase.sellStock(sellStockRequest);

        // Assert
        assertNotNull(response);
        assertFalse(response.isSuccess());
        assertEquals("Insufficient stock to sell.", response.getMessage());
        assertNull(response.getMarket());

        verify(stockRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(1L);
        verify(marketRepository, times(1)).countUserStockByTransactionType(MarketEntity.TransactionType.BUY, 1L, 1L);
        verify(marketRepository, times(1)).countUserStockByTransactionType(MarketEntity.TransactionType.SELL, 1L, 1L);
        verify(marketRepository, never()).save(any(MarketEntity.class));
    }



}

