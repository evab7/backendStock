package fontys_s3_eb.stock_project_individual.Persistence.Implementation;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    CompanyEntity findByStockSymbol(String stockSymbol);
}
