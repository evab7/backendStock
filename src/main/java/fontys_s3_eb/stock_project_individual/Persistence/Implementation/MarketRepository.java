package fontys_s3_eb.stock_project_individual.Persistence.Implementation;


import fontys_s3_eb.stock_project_individual.Persistence.Entity.MarketEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Entity.StockEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Entity.UserEntity;
import fontys_s3_eb.stock_project_individual.domain.UserPackage.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MarketRepository  extends JpaRepository<MarketEntity, Long> {
    @Query("SELECT SUM(m.quantity) FROM MarketEntity m " +
            "WHERE m.transactionType = :transactionType " +
            "AND m.user.userId = :userId " +
            "AND m.stock.stockId = :stockId")
    Long countUserStockByTransactionType(@Param("transactionType") MarketEntity.TransactionType transactionType,
                                         @Param("userId") Long userId,
                                         @Param("stockId") Long stockId);
    List<MarketEntity> findByUser(UserEntity user);
}