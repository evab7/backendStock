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
public class EditStockRequest {
    @NotBlank(message = "Company name cannot be empty")
    private String companyName;
    @NotBlank(message = "Currency name cannot be blank")
    private String currency;
    @NotBlank(message = "Description name cannot be blank")
    private String description;
    @NotBlank(message = "Stock symbol cannot be blank")
    private String stockSymbol;

}
