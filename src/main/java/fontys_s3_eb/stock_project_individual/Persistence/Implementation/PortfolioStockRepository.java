package fontys_s3_eb.stock_project_individual.Persistence.Implementation;
import fontys_s3_eb.stock_project_individual.Persistence.Entity.PortfolioStockEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioStockRepository  extends JpaRepository<PortfolioStockEntity, Long> {
    List<PortfolioStockEntity> findByUser(UserEntity user);
}
