package fontys_s3_eb.stock_project_individual.domain.MarketPackage;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserMarketInfoRequest {

    @NotBlank(message = "User id can not be empty!")
    private Long userId;
}
