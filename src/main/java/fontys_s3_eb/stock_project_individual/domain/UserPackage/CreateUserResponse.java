package fontys_s3_eb.stock_project_individual.domain.UserPackage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponse {
    private boolean success;
    private String message;
    private String username;
}
