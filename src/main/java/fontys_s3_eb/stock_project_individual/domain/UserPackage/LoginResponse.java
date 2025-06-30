package fontys_s3_eb.stock_project_individual.domain.UserPackage;//package fontys_s3_eb.stock_project_individual.domain.AuthenticationAuthorization;
//import lombok.Generated;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
}
