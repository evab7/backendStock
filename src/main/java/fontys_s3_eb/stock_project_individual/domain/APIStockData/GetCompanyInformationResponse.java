package fontys_s3_eb.stock_project_individual.domain.APIStockData;

import fontys_s3_eb.stock_project_individual.domain.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCompanyInformationResponse {
    private Company companyInformation;
}
