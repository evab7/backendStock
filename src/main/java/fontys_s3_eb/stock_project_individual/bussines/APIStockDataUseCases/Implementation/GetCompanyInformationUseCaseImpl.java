package fontys_s3_eb.stock_project_individual.bussines.APIStockDataUseCases.Implementation;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.CompanyEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.CompanyRepository;
import fontys_s3_eb.stock_project_individual.bussines.APIStockDataUseCases.UseCase.GetCompanyInformationUseCase;
import fontys_s3_eb.stock_project_individual.bussines.Exceptions.StockExistsException;
import fontys_s3_eb.stock_project_individual.domain.APIStockData.GetCompanyInformationRequest;
import fontys_s3_eb.stock_project_individual.domain.APIStockData.GetCompanyInformationResponse;
import fontys_s3_eb.stock_project_individual.domain.Company;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Service
@RequiredArgsConstructor
public class GetCompanyInformationUseCaseImpl implements GetCompanyInformationUseCase {

    @Value("${POLYGON_API_KEY}")
    private String polygonApiKey;

    private Company company;

    private static final String BASE_URL = "https://api.polygon.io/v3/reference/tickers";

    private final CompanyRepository companyRepository;

    private CompanyConverter companyConverter;



    public GetCompanyInformationResponse getCompanyInformation (GetCompanyInformationRequest companyRequest) {

        String formattedDate = companyRequest.getDate() != null ? companyRequest.getDate().toString() : "";
        Dotenv dotenv = Dotenv.configure().filename("PolygonKeyAPI.env").load();

        this.polygonApiKey = dotenv.get("POLYGON_API_KEY");
        String url = String.format("%s/%s?apiKey=%s&date=%s", BASE_URL, companyRequest.getStockSymbol(), polygonApiKey, formattedDate);


        CompanyEntity companyEntity = companyRepository.findByStockSymbol(companyRequest.getStockSymbol());



        if (companyEntity == null) {

            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = java.net.http.HttpRequest.newBuilder().uri(URI.create(url)).build();


                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                JSONObject jsonResponse = new JSONObject(response.body());

                if (jsonResponse.has("results")) {
                    JSONObject result = jsonResponse.getJSONObject("results");


                    boolean active = result.getBoolean("active");

                    String currencyName = result.getString("currency_name");
                    String companyURL = result.getString("homepage_url");
                    String category = result.getString("sic_description");
                    String description = result.getString("description");
                    String companyName = result.getString("name");
                    String stockSymbol = result.getString("ticker");


                    String city = "Unknown";
                    if (result.has("address")) {
                        JSONObject address = result.getJSONObject("address");
                        city = address.optString("city", "Unknown");
                    }

                    String logoURL = "Unknown";
                    if (result.has("branding")) {
                        JSONObject branding = result.getJSONObject("branding");
                        logoURL = branding.optString("icon_url", "Unknown");
                    }

                    try {
                        companyEntity = CompanyEntity.builder()
                                .active(active)
                                .city(city)
                                .companyURL(companyURL)
                                .category(category)
                                .currencyName(currencyName)
                                .description(description)
                                .logoURL(logoURL)
                                .companyName(companyName)
                                .stockSymbol(stockSymbol)
                                .build();

                        companyRepository.save(companyEntity);
                    }
                   catch (DataIntegrityViolationException e) {
                        if (e.getMessage().contains("stockSymbol")) {
                            throw new StockExistsException("Stock already exist.");
                        }
                        throw e;
                    }


                }
            } catch (IOException e) {
                throw new RuntimeException("API error");
            } catch (InterruptedException e) {
                throw new RuntimeException("API error");
            }

        }
        company = companyConverter.convert(companyEntity);
        return GetCompanyInformationResponse.builder()
                .companyInformation(company)
                .build();
    }

}
