package fontys_s3_eb.stock_project_individual.domain.PortfolioPackage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddStockToPortfolioRequest {
     @NotNull
     private Long userId;
     @NotNull
     private Long stockId;
     @NotBlank
     private String comment;

}
