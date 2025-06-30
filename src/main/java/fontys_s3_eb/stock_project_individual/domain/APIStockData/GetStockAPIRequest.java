package fontys_s3_eb.stock_project_individual.domain.APIStockData;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetStockAPIRequest {
    @NotBlank
    private String stockSymbol;
}


