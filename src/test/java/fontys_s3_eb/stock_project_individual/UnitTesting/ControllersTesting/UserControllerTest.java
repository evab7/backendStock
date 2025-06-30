package fontys_s3_eb.stock_project_individual.UnitTesting.ControllersTesting;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.RefreshTokenEntity;
import fontys_s3_eb.stock_project_individual.bussines.User.Implementation.GetSingleUserUseCaseImpl;
import fontys_s3_eb.stock_project_individual.bussines.User.Implementation.RefreshTokenUseCaseImpl;
import fontys_s3_eb.stock_project_individual.bussines.User.UseCase.CreateUserUseCase;
import fontys_s3_eb.stock_project_individual.bussines.User.UseCase.GetUsersUseCase;
import fontys_s3_eb.stock_project_individual.bussines.User.UseCase.LogInUseCase;
import fontys_s3_eb.stock_project_individual.configuration.Security.TokenJWL.TokenVerifier;
import fontys_s3_eb.stock_project_individual.controller.UserController;
import fontys_s3_eb.stock_project_individual.domain.UserPackage.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LogInUseCase loginUseCase;

    @MockBean
    private CreateUserUseCase createUserUseCase;

    @MockBean
    private GetUsersUseCase getUserUseCase;

    @MockBean
    private RefreshTokenUseCaseImpl refreshTokenUseCaseImpl;

    @MockBean
    private GetSingleUserUseCaseImpl getSingleUserUseCaseImpl;

    @MockBean
    private TokenVerifier tokenVerifier;

    @Test
    void login_shouldReturn201_whenRequestValid() throws Exception {
        // Arrange
        LogInRequest request = new LogInRequest();
        LoginResponse response = new LoginResponse();
        when(loginUseCase.login(any())).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/user/logIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "username": "testUser",
                                "password": "password123"
                            }
                        """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                            {}
                        """));

        verify(loginUseCase).login(any());
    }

    @Test
    void createUser_shouldReturn201_whenRequestValid() throws Exception {
        // Arrange
        CreateUserRequest request = new CreateUserRequest();
        CreateUserResponse response = new CreateUserResponse();
        when(createUserUseCase.createUser(any())).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "username": "string",
                                "password": "string",
                                "role": "string"
                            }
                        """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                            {}
                        """));

        verify(createUserUseCase).createUser(any());
    }

    @Test
    void createUser_shouldReturn400_whenRequestInvalid() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "username": "string",
                                 "password": -2
                            }
                        """))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void refreshToken_shouldReturn201_whenRequestValid() throws Exception {
        // Arrange
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
        RefreshTokenResponse response = new RefreshTokenResponse();
        when(refreshTokenUseCaseImpl.getAccessTokenFromRefreshToken(anyString())).thenReturn(refreshTokenEntity);
        when(loginUseCase.refreshToken(any())).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/user/refreshToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "refreshToken": "validRefreshToken"
                            }
                        """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                            {}
                        """));

        verify(refreshTokenUseCaseImpl).getAccessTokenFromRefreshToken(anyString());
        verify(loginUseCase).refreshToken(any());
    }



    @Test
    void getAllUsers_shouldReturn200_whenRequestValid() throws Exception {
        // Arrange
        GetUsersResponse response = new GetUsersResponse();
        when(getUserUseCase.getUsers()).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("""
                            {}
                        """));

        verify(getUserUseCase).getUsers();
    }

    @Test
    void getSingleUser_shouldReturn200_whenUserExists() throws Exception {
        // Arrange
        GetSingleUserResponse response = new GetSingleUserResponse();
        when(getSingleUserUseCaseImpl.getUser(anyLong())).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/user/{userId}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("""
                            {}
                        """));

        verify(getSingleUserUseCaseImpl).getUser(anyLong());
    }

    @Test
    void getSingleUser_shouldReturn404_whenUserNotFound() throws Exception {
        // Arrange
        when(getSingleUserUseCaseImpl.getUser(anyLong()))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Act & Assert
        mockMvc.perform(get("/user/{userId}", 999)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(getSingleUserUseCaseImpl).getUser(anyLong());
    }
}

