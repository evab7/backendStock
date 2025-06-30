package fontys_s3_eb.stock_project_individual.UnitTesting.ServiceLayer.Market;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.MarketEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Entity.StockEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.MarketRepository;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.StockRepository;
import fontys_s3_eb.stock_project_individual.bussines.Market.Implementation.GetMarketInfoUseCaseImpl;
import fontys_s3_eb.stock_project_individual.domain.MarketPackage.GetMarketInfoResponse;
import fontys_s3_eb.stock_project_individual.domain.MarketPackage.Market;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class GetMarketInfoUseCaseTest {

    @Mock
    private MarketRepository marketRepository;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private GetMarketInfoUseCaseImpl getMarketInfoUseCase;

    private List<MarketEntity> marketEntities;

    @BeforeEach
    void setUp() {
        marketEntities = List.of(
                MarketEntity.builder()
                        .marketId(1L)
                        .stock(StockEntity.builder()
                                .stockId(1L)
                                .stockSymbol("AAPL")
                                .companyName("Apple Inc.")
                                .build())
                        .quantity(10L)
                        .price(new BigDecimal("150.0"))
                        .transactionType(MarketEntity.TransactionType.BUY)
                        .build(),
                MarketEntity.builder()
                        .marketId(2L)
                        .stock(StockEntity.builder()
                                .stockId(2L)
                                .stockSymbol("GOOGL")
                                .companyName("Google LLC")
                                .build())
                        .quantity(5L)
                        .price(new BigDecimal("2800.0"))
                        .transactionType(MarketEntity.TransactionType.SELL)
                        .build()
        );
    }

//    @Test
//    void getMarketInfo_ValidMarketData_ReturnsMarketInfo() {
//        // Arrange
//        when(marketRepository.findAll()).thenReturn(marketEntities);
//
//        // Act
//        GetMarketInfoResponse response = getMarketInfoUseCase.getMarketInfo();
//
//        // Assert
//        assertNotNull(response);
//        assertNotNull(response.getMarketList());
//
//        Market firstMarket = response.getMarketList().get(0);
//        assertEquals(1L, firstMarket.getMarketId());
//        assertEquals("AAPL", firstMarket.getStock().getStockSymbol());
//        assertEquals(10, firstMarket.getQuantity());
//        assertEquals(0, new BigDecimal("150.0").compareTo(firstMarket.getPrice()));
//
//        Market secondMarket = response.getMarketList().get(1);
//        assertEquals(2L, secondMarket.getMarketId());
//        assertEquals("GOOGL", secondMarket.getStock().getStockSymbol());
//        assertEquals(5, secondMarket.getQuantity());
//        assertEquals(0, new BigDecimal("2800.0").compareTo(secondMarket.getPrice()));
//
//        verify(marketRepository, times(1)).findAll();
//    }

    @Test
    void getMarketInfo_NoMarketData_ReturnsEmptyList() {
        // Arrange
        when(marketRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        GetMarketInfoResponse response = getMarketInfoUseCase.getMarketInfo();

        // Assert
        assertNotNull(response);
        assertNotNull(response.getMarketList());
        assertTrue(response.getMarketList().isEmpty());

        verify(marketRepository, times(1)).findAll();
    }

    @Test
    void getMarketInfo_RepositoryThrowsException_ReturnsEmptyList() {
        // Arrange
        when(marketRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        // Act
        GetMarketInfoResponse response = getMarketInfoUseCase.getMarketInfo();

        // Assert
        assertNotNull(response);
        assertNotNull(response.getMarketList());
        assertTrue(response.getMarketList().isEmpty());

        verify(marketRepository, times(1)).findAll();
    }
}
