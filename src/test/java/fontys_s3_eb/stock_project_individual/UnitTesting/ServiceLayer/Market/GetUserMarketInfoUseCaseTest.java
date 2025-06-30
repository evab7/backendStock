package fontys_s3_eb.stock_project_individual.UnitTesting.ServiceLayer.Market;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.MarketEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Entity.StockEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Entity.UserEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.MarketRepository;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.UserRepository;
import fontys_s3_eb.stock_project_individual.bussines.Market.Implementation.GetUserMarketInfoUseCaseImpl;
import fontys_s3_eb.stock_project_individual.domain.MarketPackage.GetUserMarketInfoResponse;
import fontys_s3_eb.stock_project_individual.domain.MarketPackage.Market;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class GetUserMarketInfoUseCaseTest {

    @Mock
    private MarketRepository marketRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserMarketInfoUseCaseImpl getUserMarketInfoUseCase;

    private UserEntity userEntity;
    private List<MarketEntity> marketEntities;

    @BeforeEach
    void setUp() {
        userEntity = UserEntity.builder()
                .userId(1L)
                .username("testuser")
                .build();

        marketEntities = List.of(
                MarketEntity.builder()
                        .marketId(1L)
                        .user(userEntity)
                        .stock(StockEntity.builder()
                                .stockId(1L)
                                .stockSymbol("AAPL")
                                .build())
                        .quantity(10L)
                        .price(new BigDecimal("150.0"))
                        .transactionType(MarketEntity.TransactionType.BUY)
                        .build(),
                MarketEntity.builder()
                        .marketId(2L)
                        .user(userEntity)
                        .stock(StockEntity.builder()
                                .stockId(2L)
                                .stockSymbol("GOOGL")
                                .build())
                        .quantity(5L)
                        .price(new BigDecimal("2800.0"))
                        .transactionType(MarketEntity.TransactionType.SELL)
                        .build()
        );
    }

    @Test
    void getUserMarketInfo_ValidUserId_ReturnsMarketInfo() {
        // Arrange
        when(userRepository.findByUserId(1L)).thenReturn(userEntity);
        when(marketRepository.findByUser(userEntity)).thenReturn(marketEntities);

        // Act
        GetUserMarketInfoResponse response = getUserMarketInfoUseCase.getUserMarketInfo(1L);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getMarketList());
        assertEquals(2, response.getMarketList().size());

        Market firstMarket = response.getMarketList().get(0);
        assertEquals(1L, firstMarket.getMarketId());
        assertEquals("AAPL", firstMarket.getStock().getStockSymbol());
        assertEquals(10, firstMarket.getQuantity());
        assertEquals(0, new BigDecimal("150.0").compareTo(firstMarket.getPrice()));

        verify(userRepository, times(1)).findByUserId(1L);
        verify(marketRepository, times(1)).findByUser(userEntity);
    }

    @Test
    void getUserMarketInfo_NullUserId_ThrowsIllegalArgumentException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> getUserMarketInfoUseCase.getUserMarketInfo(null));

        assertEquals("Invalid request: User ID cannot be null.", exception.getMessage());
        verifyNoInteractions(userRepository, marketRepository);
    }

    @Test
    void getUserMarketInfo_UserNotFound_ReturnsEmptyMarketInfo() {
        // Arrange
        when(userRepository.findByUserId(null)).thenReturn(null);

        // Act
        GetUserMarketInfoResponse response = getUserMarketInfoUseCase.getUserMarketInfo(999L);

        // Assert
        assertNull(response);
        verify(userRepository, times(1)).findByUserId(999L);
        verifyNoInteractions(marketRepository);
    }

    @Test
    void getUserMarketInfo_RepositoryThrowsException_ReturnsNull() {
        // Arrange
        when(userRepository.findByUserId(1L)).thenThrow(new RuntimeException("Database error"));

        // Act
        GetUserMarketInfoResponse response = getUserMarketInfoUseCase.getUserMarketInfo(1L);

        // Assert
        assertNull(response);
        verify(userRepository, times(1)).findByUserId(1L);
        verifyNoInteractions(marketRepository);
    }
}
