package fontys_s3_eb.stock_project_individual.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Company {
    private String companyName;
    private boolean active;
    private String city;
    private String currencyName;
    private String companyURL;
    private String description;
    private String category;
    private String logoURL;
    private String stockSymbol;
}
