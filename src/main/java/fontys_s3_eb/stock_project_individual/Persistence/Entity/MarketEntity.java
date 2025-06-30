package fontys_s3_eb.stock_project_individual.Persistence.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "market")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "market_id")
    private Long marketId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_Id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "stock_id", referencedColumnName = "stock_id")
    private StockEntity stock;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "price", precision = 15, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", columnDefinition = "ENUM('BUY', 'SELL')")
    private TransactionType transactionType;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt;

    public enum TransactionType {
        BUY, SELL
    }
}
