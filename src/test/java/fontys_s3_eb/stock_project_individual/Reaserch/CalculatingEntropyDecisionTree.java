package fontys_s3_eb.stock_project_individual.Reaserch;

import fontys_s3_eb.stock_project_individual.bussines.APIStockDataUseCases.Implementation.GetStockInfoFromAPIImpl;
import fontys_s3_eb.stock_project_individual.bussines.APIStockDataUseCases.UseCase.GetStockInfoFromAPlUseCase;
import fontys_s3_eb.stock_project_individual.domain.APIStockData.APIStockData;
import fontys_s3_eb.stock_project_individual.domain.APIStockData.GetStockAPIRequest;
import fontys_s3_eb.stock_project_individual.domain.APIStockData.GetStockAPIResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CalculatingEntropyDecisionTree {


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

        double minPrice = Collections.min(prices);
        double maxPrice = Collections.max(prices);
        double range = maxPrice - minPrice;

        int k = (int) Math.ceil(Math.log(prices.size()) / Math.log(2) + 1);

        double s = range / k;

        List<Integer> frequencies = new ArrayList<>(Collections.nCopies(k, 0));
        for (double price : prices) {
            int sIndex = (int) Math.min((price - minPrice) / s, k - 1);
            frequencies.set(sIndex, frequencies.get(sIndex) + 1);
        }


        double entropy = calculateEntropy(frequencies, prices.size());
        System.out.println("Entropy: " + entropy);


    }

    public static double calculateEntropy(List<Integer> frequencies, int total) {
        double entropy = 0.0;

        for (int frequency : frequencies) {
            if (frequency == 0) continue;

            double probability = (double) frequency / total;
            entropy -= probability * (Math.log(probability) / Math.log(2));
        }

        return entropy;
    }
}
