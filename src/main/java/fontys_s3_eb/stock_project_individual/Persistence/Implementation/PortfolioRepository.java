package fontys_s3_eb.stock_project_individual.Persistence.Implementation;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.PortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;


public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Long> {
    List<PortfolioEntity> Date(Date date);
}
