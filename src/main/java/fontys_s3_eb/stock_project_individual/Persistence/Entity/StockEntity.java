package fontys_s3_eb.stock_project_individual.Persistence.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import java.util.List;


@Entity
@Table(name = "stock")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Long stockId;

    @NotBlank
    @Length(min = 2, max = 100)
    @Column(name = "company_name")
    private String companyName;

    @NotBlank
    @Length(min = 3, max = 10)
    @Column(name = "currency")
    private String currency;

    @Column(name = "description")
    @Length(max = 255)
    private String description;

    @NotBlank
    @Length(min = 1, max = 10)
    @Column(name = "stock_symbol")
    private String stockSymbol;

}
