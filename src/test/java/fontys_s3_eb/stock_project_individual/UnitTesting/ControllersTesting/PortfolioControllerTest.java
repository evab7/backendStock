package fontys_s3_eb.stock_project_individual.UnitTesting.ControllersTesting;

import fontys_s3_eb.stock_project_individual.bussines.PortfolioUseCases.UseCase.AddStockToPortoflioUseCase;
import fontys_s3_eb.stock_project_individual.bussines.PortfolioUseCases.UseCase.GetStocksFromPortolioUseCase;
import fontys_s3_eb.stock_project_individual.configuration.Security.TokenJWL.TokenVerifier;
import fontys_s3_eb.stock_project_individual.controller.PortfolioController;
import fontys_s3_eb.stock_project_individual.domain.PortfolioPackage.AddStockToPortfolioRequest;
import fontys_s3_eb.stock_project_individual.domain.PortfolioPackage.AddStockToPortfolioResponse;
import fontys_s3_eb.stock_project_individual.domain.PortfolioPackage.GetPortfolioStockRequest;
import fontys_s3_eb.stock_project_individual.domain.PortfolioPackage.GetPortfolioStockResponse;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PortfolioController.class)
@AutoConfigureMockMvc(addFilters = false)
class PortfolioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddStockToPortoflioUseCase addStockToPortoflioUseCase;

    @MockBean
    private GetStocksFromPortolioUseCase getStocksFromPortolioUseCase;

    @MockBean
    private TokenVerifier tokenVerifier;


    @Test
    @WithMockUser
    void addToPortfolio_shouldReturn201_whenRequestValid() throws Exception {
        AddStockToPortfolioRequest request = new AddStockToPortfolioRequest();
        AddStockToPortfolioResponse response = new AddStockToPortfolioResponse();
        when(addStockToPortoflioUseCase.addStockToPortfolioResponse(any()))
                .thenReturn(response);

        mockMvc.perform(post("/portfolio/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "userId": 5,
                                "stockId": 2,
                                "comment": "bok"
                          
                                
                            }
                        """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                            {}
                        """));

        verify(addStockToPortoflioUseCase).addStockToPortfolioResponse(any());
    }

    @Test
    @WithMockUser
    void addToPortfolio_shouldReturn400_whenRequestInvalid() throws Exception {
        mockMvc.perform(post("/portfolio/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""" 
                        {
                            "userId": null,
                            "stockId": 2,
                            "comment": ""
                        }
                    """))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @WithMockUser
    @Test
    void getPortfolioStock_shouldReturn200_whenRequestValid() throws Exception {
        GetPortfolioStockRequest request = new GetPortfolioStockRequest();
        GetPortfolioStockResponse response = new GetPortfolioStockResponse();
        when(getStocksFromPortolioUseCase.getStocksFromPortolio(any()))
                .thenReturn(response);

        mockMvc.perform(get("/portfolio/{userId}", 5)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("""
                            {}
                        """));

        verify(getStocksFromPortolioUseCase).getStocksFromPortolio(any());
    }

    @WithMockUser
    @Test
    void getPortfolioStock_shouldReturn404_whenUserDoesNotExist() throws Exception {
        when(getStocksFromPortolioUseCase.getStocksFromPortolio(any()))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        mockMvc.perform(get("/portfolio/{userId}", 999)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(getStocksFromPortolioUseCase).getStocksFromPortolio(any());
    }
}