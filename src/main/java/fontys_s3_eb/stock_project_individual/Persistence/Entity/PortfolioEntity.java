package fontys_s3_eb.stock_project_individual.Persistence.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "portfolio")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id")
    private Long portfolioId;

    @Column(name = "comment", length = 255)
    @Length(max = 255)
    private String comment;

    @Column(name = "date")
    private Date date;


}
