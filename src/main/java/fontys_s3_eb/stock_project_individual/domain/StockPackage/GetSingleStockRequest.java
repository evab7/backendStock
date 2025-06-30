package fontys_s3_eb.stock_project_individual.domain.StockPackage;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetSingleStockRequest {
    @NotBlank(message = "Stock symbol can not be empty!")
    private String stockSymbol;


}
