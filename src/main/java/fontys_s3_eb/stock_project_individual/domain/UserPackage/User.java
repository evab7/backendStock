package fontys_s3_eb.stock_project_individual.domain.UserPackage;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class User {
    private Long userId;
    private String username;
    private String password;
    private String role;

}
