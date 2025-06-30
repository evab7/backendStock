package fontys_s3_eb.stock_project_individual.UnitTesting.ControllersTesting;

import fontys_s3_eb.stock_project_individual.bussines.Market.UseCase.BuyStockUseCase;
import fontys_s3_eb.stock_project_individual.bussines.Market.UseCase.GetMarketInfoUseCase;
import fontys_s3_eb.stock_project_individual.bussines.Market.UseCase.GetUserMarketInfoUseCase;
import fontys_s3_eb.stock_project_individual.bussines.Market.UseCase.SellStockUseCase;
import fontys_s3_eb.stock_project_individual.configuration.Security.TokenJWL.TokenVerifier;
import fontys_s3_eb.stock_project_individual.controller.MarketController;
import fontys_s3_eb.stock_project_individual.domain.MarketPackage.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = MarketController.class)
@AutoConfigureMockMvc(addFilters = false)
class MarketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BuyStockUseCase buyStockUseCase;

    @MockBean
    private SellStockUseCase sellStockUseCase;

    @MockBean
    private GetMarketInfoUseCase getMarketInfoUseCase;

    @MockBean
    private GetUserMarketInfoUseCase getUserMarketInfoUseCase;

    @MockBean
    private TokenVerifier tokenVerifier;

    @Test
    @WithMockUser(roles = {"USER"})
    void buyStock_shouldReturn201_whenRequestValid() throws Exception {
        BuyStockRequest request = new BuyStockRequest();
        BuyStockResponse response = new BuyStockResponse();
        when(buyStockUseCase.buyStock(any())).thenReturn(response);

        mockMvc.perform(post("/market/buy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                               "userId": 2,
                                "stockId": 5,
                                "quantity": 4,
                                "price": 100,
                                "date": "2025-01-16T21:26:17.811Z"
                            }
                        """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                            {}
                        """));

        verify(buyStockUseCase).buyStock(any());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void sellStock_shouldReturn201_whenRequestValid() throws Exception {
        SellStockRequest request = new SellStockRequest();
        SellStockResponse response = new SellStockResponse();
        when(sellStockUseCase.sellStock(any())).thenReturn(response);

        mockMvc.perform(post("/market/sell")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                               "userId": 2,
                                "stockId": 5,
                                "quantity": 4,
                                "price": 100,
                                "date": "2025-01-16T21:26:17.811Z"
                            }
                        """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                            {}
                        """));

        verify(sellStockUseCase).sellStock(any());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void buyStock_shouldReturn400_whenRequestInvalid() throws Exception {
        // Act & Assert:
        mockMvc.perform(post("/market/buy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""" 
                        {
                           "userId": 2,
                           "stockId": 5,
                           "quantity": "invalid",
                           "price": 100
                        }
                    """))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(roles = {"USER"})
    void sellStock_shouldReturn400_whenRequestInvalid() throws Exception {
        // Act & Assert: Send a malformed or incomplete JSON
        mockMvc.perform(post("/market/sell")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""" 
                        {
                           "userId": null, 
                           "stockId": 5,
                           "quantity": 4,
                           "price": 100
                           
                        }
                    """))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    @WithMockUser(roles = {"ADMIN"})
    void getAllMarketInfo_shouldReturn200_whenRequestValid() throws Exception {
        GetMarketInfoResponse response = new GetMarketInfoResponse();
        when(getMarketInfoUseCase.getMarketInfo()).thenReturn(response);

        mockMvc.perform(get("/market")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("""
                            {}
                        """));

        verify(getMarketInfoUseCase).getMarketInfo();
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void getUserMarket_shouldReturn200_whenRequestValid() throws Exception {
        GetUserMarketInfoResponse response = new GetUserMarketInfoResponse();
        when(getUserMarketInfoUseCase.getUserMarketInfo(anyLong())).thenReturn(response);

        mockMvc.perform(get("/market/{userId}", 5)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("""
                            {}
                        """));

        verify(getUserMarketInfoUseCase).getUserMarketInfo(anyLong());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void getUserMarket_shouldReturn404_whenUserNotFound() throws Exception {
        when(getUserMarketInfoUseCase.getUserMarketInfo(anyLong()))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        mockMvc.perform(get("/market/{userId}", 999)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(getUserMarketInfoUseCase).getUserMarketInfo(anyLong());
    }
}

