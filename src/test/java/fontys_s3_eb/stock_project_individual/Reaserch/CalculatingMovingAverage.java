package fontys_s3_eb.stock_project_individual.Reaserch;
import fontys_s3_eb.stock_project_individual.bussines.APIStockDataUseCases.Implementation.GetStockInfoFromAPIImpl;
import fontys_s3_eb.stock_project_individual.bussines.APIStockDataUseCases.UseCase.GetStockInfoFromAPlUseCase;
import fontys_s3_eb.stock_project_individual.domain.APIStockData.APIStockData;
import fontys_s3_eb.stock_project_individual.domain.APIStockData.GetStockAPIRequest;
import fontys_s3_eb.stock_project_individual.domain.APIStockData.GetStockAPIResponse;

import java.util.ArrayList;
import java.util.List;


public class CalculatingMovingAverage {

    public static void main(String[] args) {


        GetStockInfoFromAPlUseCase getStockInfoFromAPlUseCase = new GetStockInfoFromAPIImpl();
        GetStockAPIRequest getStockAPIRequest = GetStockAPIRequest.builder()
                .stockSymbol("AAPL")
                .build();

        GetStockAPIResponse response = getStockInfoFromAPlUseCase.getStockInfoAPI(getStockAPIRequest);
        List<APIStockData> stockDataList = response.getStockData();


        List<Double> prices = new ArrayList<>();
        for (APIStockData stockData : stockDataList) {
            prices.add(stockData.getClosePrice());
        }

        int windowSize = 10;


        List<Double> smaValues = calculateSMA(prices, windowSize);


        System.out.println("SMA Values:");
        for (int i = 0; i < smaValues.size(); i++) {
            System.out.printf("SMA at t=%d: %.2f%n", (windowSize + i), smaValues.get(i));
        }
    }


    private static List<Double> calculateSMA(List<Double> prices, int windowSize) {
        List<Double> smaValues = new ArrayList<>();
        for (int i = 0; i <= prices.size() - windowSize; i++) {
            double sum = 0.0;


            for (int j = i; j < i + windowSize; j++) {
                sum += prices.get(j);
            }

            double sma = sum / windowSize;
            smaValues.add(sma);
        }
        return smaValues;
    }
}


