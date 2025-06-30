package fontys_s3_eb.stock_project_individual.Persistence.Implementation;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.StockEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<StockEntity, Long> {
    StockEntity findByStockSymbol(String stockSymbol);
    StockEntity findByStockId(Long stockId);
}
