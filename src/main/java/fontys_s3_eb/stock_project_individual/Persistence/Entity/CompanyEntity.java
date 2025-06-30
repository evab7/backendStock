package fontys_s3_eb.stock_project_individual.Persistence.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "company")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private String companyId;

    @NotBlank
    @Column(name = "company_name")
    private String companyName;

    @Column(name = "active")
    private boolean active;

    @NotBlank
    @Column(name = "city")
    private String city;

    @NotBlank
    @Column(name = "currency_name")
    private String currencyName;

    @NotBlank
    @Column(name = "company_URL")
    private String companyURL;

    @Lob
    @NotBlank
    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;

    @NotBlank
    @Column(name = "category")
    private String category;

    @NotBlank
    @Column(name = "logo_URL")
    private String logoURL;

    @NotBlank
    @Column(name = "stock_symbol")
    private String stockSymbol;

    @OneToOne
    @PrimaryKeyJoinColumn
    private StockEntity stock;
}
