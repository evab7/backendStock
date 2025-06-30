package fontys_s3_eb.stock_project_individual.domain.StockPackage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditStockResponse {

    private Long stockID;
    private String errorMessage;

}
