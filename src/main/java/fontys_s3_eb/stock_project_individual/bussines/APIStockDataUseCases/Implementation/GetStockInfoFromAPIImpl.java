package fontys_s3_eb.stock_project_individual.bussines.APIStockDataUseCases.Implementation;

import fontys_s3_eb.stock_project_individual.domain.APIStockData.APIStockData;
import fontys_s3_eb.stock_project_individual.domain.APIStockData.GetStockAPIRequest;
import fontys_s3_eb.stock_project_individual.domain.APIStockData.GetStockAPIResponse;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import fontys_s3_eb.stock_project_individual.bussines.APIStockDataUseCases.UseCase.GetStockInfoFromAPlUseCase;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetStockInfoFromAPIImpl implements GetStockInfoFromAPlUseCase {

    @Value("${POLYGON_API_KEY}")
    private String polygonApiKey;

    public GetStockAPIResponse getStockInfoAPI(GetStockAPIRequest stockRequest) {

        List<APIStockData> stockDataList = new ArrayList<>();

        Dotenv dotenv = Dotenv.configure().filename("PolygonKeyAPI.env").load();

        this.polygonApiKey = dotenv.get("POLYGON_API_KEY");

        // start date is 2 years ago, end date is today, geting the values for past 2 years
        String startDate = java.time.LocalDate.now().minusYears(2).toString();
        String endDate = java.time.LocalDate.now().toString();

        String url = "https://api.polygon.io/v2/aggs/ticker/" + stockRequest.getStockSymbol() + "/range/1/day/" // popravi!!!
                + startDate + "/" + endDate + "?adjusted=true&sort=asc&apiKey=" + polygonApiKey;

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = java.net.http.HttpRequest.newBuilder().uri(URI.create(url)).build();


            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonResponse = new JSONObject(response.body());


            if (jsonResponse.has("results")) {
                JSONArray results = jsonResponse.getJSONArray("results");

                for (int i = 0; i < results.length(); i++) {
                    JSONObject result = results.getJSONObject(i);

                    long date = result.getLong("t"); // timestamp (Unix timestamp)
                    double closePrice = result.getDouble("c");
                    double volume = result.getDouble("v");

                    Date dateObj = new java.util.Date(date);
                    SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = sdf.format(dateObj);

                    System.out.println("Date: " + formattedDate + ", Close Price: " + closePrice + ", Volume: " + volume);
                    APIStockData newStockInfo = APIStockData.builder()
                            .DateTime(dateObj)
                            .tradingVolume(volume)
                            .closePrice(closePrice)
                            .stockSymbol(stockRequest.getStockSymbol())
                            .build();

                    stockDataList.add(newStockInfo);

                }
            } else {
                System.out.println("No data found for the given date range.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return GetStockAPIResponse.builder()
                .stockData(stockDataList)
                .build();
    }


}
