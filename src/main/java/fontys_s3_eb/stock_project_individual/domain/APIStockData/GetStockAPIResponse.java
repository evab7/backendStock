package fontys_s3_eb.stock_project_individual.domain.APIStockData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetStockAPIResponse {
    private List<APIStockData> stockData;
}
