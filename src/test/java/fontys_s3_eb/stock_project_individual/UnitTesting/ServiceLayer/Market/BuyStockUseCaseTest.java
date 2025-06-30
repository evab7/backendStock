package fontys_s3_eb.stock_project_individual.UnitTesting.ServiceLayer.Market;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.MarketEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Entity.StockEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Entity.UserEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.MarketRepository;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.StockRepository;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.UserRepository;
import fontys_s3_eb.stock_project_individual.bussines.Market.Implementation.BuyStockUseCaseImpl;
import fontys_s3_eb.stock_project_individual.domain.MarketPackage.BuyStockRequest;
import fontys_s3_eb.stock_project_individual.domain.MarketPackage.BuyStockResponse;
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
public class BuyStockUseCaseTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MarketRepository marketRepository;

    @InjectMocks
    private BuyStockUseCaseImpl buyStockUseCase;

    private StockEntity stockEntity;
    private UserEntity userEntity;
    private BuyStockRequest buyStockRequest;

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

        buyStockRequest = BuyStockRequest.builder()
                .stockId(1L)
                .userId(1L)
                .quantity(10L)
                .price(new BigDecimal("150.0"))
                .build();
    }

    @Test
    void buyStock_ValidRequest_ReturnsBuyStockResponse() {
        // Arrange
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stockEntity));
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        MarketEntity savedMarketEntity = MarketEntity.builder()
                .marketId(1L)
                .stock(stockEntity)
                .user(userEntity)
                .quantity(10L)
                .price(new BigDecimal("150.0"))
                .transactionType(MarketEntity.TransactionType.BUY)
                .createdAt(new Date())
                .build();

        when(marketRepository.save(any(MarketEntity.class))).thenReturn(savedMarketEntity);

        // Act
        BuyStockResponse response = buyStockUseCase.buyStock(buyStockRequest);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getMarket());
        assertEquals(1L, response.getMarket().getMarketId());
        assertEquals("AAPL", response.getMarket().getStock().getStockSymbol());
        assertEquals("testuser", response.getMarket().getUser().getUsername());
        assertEquals(10, response.getMarket().getQuantity());
        assertEquals(new BigDecimal("150.0"), response.getMarket().getPrice());

        verify(stockRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(1L);
        verify(marketRepository, times(1)).save(any(MarketEntity.class));
    }

    @Test
    void buyStock_StockNotFound_ReturnsNullMarket() {
        // Arrange
        when(stockRepository.findById(1L)).thenReturn(Optional.empty());
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        // Act
        BuyStockResponse response = buyStockUseCase.buyStock(buyStockRequest);

        // Assert
        assertNotNull(response);
        assertNull(response.getMarket());

        verify(stockRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(1L);
        verifyNoInteractions(marketRepository);
    }

    @Test
    void buyStock_UserNotFound_ReturnsNullMarket() {
        // Arrange
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stockEntity));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        BuyStockResponse response = buyStockUseCase.buyStock(buyStockRequest);

        // Assert
        assertNotNull(response);
        assertNull(response.getMarket());

        verify(stockRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(1L);
        verifyNoInteractions(marketRepository);
    }
}
