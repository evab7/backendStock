package fontys_s3_eb.stock_project_individual.Persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "user")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_Id")
    private Long userId;
    @NotBlank
    @Length(min = 2 ,max = 50)
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    @Length(min = 2, max = 100)
    private String password;
    @Column(name = "role")
    private String role;


}

