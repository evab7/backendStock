package fontys_s3_eb.stock_project_individual.UnitTesting.ControllersTesting;

import fontys_s3_eb.stock_project_individual.bussines.StockUseCases.UseCase.AddStockUseCase;
import fontys_s3_eb.stock_project_individual.bussines.StockUseCases.UseCase.DeleteStockUseCase;
import fontys_s3_eb.stock_project_individual.bussines.StockUseCases.UseCase.GetSingleStockUseCase;
import fontys_s3_eb.stock_project_individual.bussines.StockUseCases.UseCase.EditStockUseCase;
import fontys_s3_eb.stock_project_individual.bussines.StockUseCases.UseCase.GetStockUseCase;
import fontys_s3_eb.stock_project_individual.configuration.Security.TokenJWL.TokenVerifier;
import fontys_s3_eb.stock_project_individual.controller.StockController;
import fontys_s3_eb.stock_project_individual.domain.StockPackage.*;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = StockController.class)
@AutoConfigureMockMvc(addFilters = false)
class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddStockUseCase addStockUseCase;

    @MockBean
    private DeleteStockUseCase deleteStockUseCase;


    @MockBean
    private EditStockUseCase editStockUseCase;


    @MockBean
    private GetStockUseCase getStockUseCase;

    @MockBean
    private GetSingleStockUseCase getSingleStockUseCase;

    @MockBean
    private TokenVerifier tokenVerifier;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void addStock_shouldReturn201_whenRequestValid() throws Exception {
        // Arrange
        CreateStockRequest request = new CreateStockRequest();
        CreateStockResponse response = new CreateStockResponse();
        when(addStockUseCase.addStock(any())).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "companyName": "string",
                                "currency": "string",
                                "description": "string",
                                "stockSymbol": "string"
                            }
                        """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                            {}
                        """));

        verify(addStockUseCase).addStock(any());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void addStock_shouldReturn400_whenRequestInvalid() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "stockSymbol": "",
                                "stockName": null,
                                "price": -1
                            }
                        """))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteStock_shouldReturn204_whenStockExists() throws Exception {
        // Arrange
        Integer stockId = 1;
        doNothing().when(deleteStockUseCase).deleteStock(stockId);

        // Act & Assert
        mockMvc.perform(delete("/stock/{stockId}", stockId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(deleteStockUseCase).deleteStock(stockId);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteStock_shouldReturn404_whenStockNotFound() throws Exception {
        // Arrange
        Integer stockId = 999;
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock not found"))
                .when(deleteStockUseCase).deleteStock(stockId);

        // Act & Assert
        mockMvc.perform(delete("/stock/{stockId}", stockId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(deleteStockUseCase).deleteStock(stockId);
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void getAllStocks_shouldReturn200_whenRequestValid() throws Exception {
        // Arrange
        GetStockResponse response = new GetStockResponse();
        when(getStockUseCase.getStocks()).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/stock")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("""
                            {}
                        """));

        verify(getStockUseCase).getStocks();
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void getSingleStock_shouldReturn200_whenStockExists() throws Exception {
        // Arrange
        GetSingleStockRequest request = new GetSingleStockRequest();
        GetSingleStockResponse response = new GetSingleStockResponse();
        when(getSingleStockUseCase.getStocks(any())).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/stock/{stockSymbol}", "AAPL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("""
                            {}
                        """));

        verify(getSingleStockUseCase).getStocks(any());
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void getSingleStock_shouldReturn404_whenStockNotFound() throws Exception {
        // Arrange
        when(getSingleStockUseCase.getStocks(any()))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock not found"));

        // Act & Assert
        mockMvc.perform(get("/stock/{stockSymbol}", "INVALID")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(getSingleStockUseCase).getStocks(any());
    }
}
