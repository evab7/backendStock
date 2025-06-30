package fontys_s3_eb.stock_project_individual.domain.MarketPackage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserMarketInfoResponse {
    private List<Market> marketList;
}
