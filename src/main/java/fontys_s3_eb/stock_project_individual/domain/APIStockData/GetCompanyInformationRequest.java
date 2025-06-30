package fontys_s3_eb.stock_project_individual.domain.APIStockData;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCompanyInformationRequest {
    @NotBlank(message = "Stock symbol can not be empty!")
    private String stockSymbol;
    @NotBlank(message = "Date can not be empty!")
    private LocalDate date;

}
