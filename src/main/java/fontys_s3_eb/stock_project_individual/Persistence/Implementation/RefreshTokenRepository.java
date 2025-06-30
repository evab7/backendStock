package fontys_s3_eb.stock_project_individual.Persistence.Implementation;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.RefreshTokenEntity;

import org.springframework.data.jpa.repository.JpaRepository;



public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> { ;
    RefreshTokenEntity findByToken(String token);

}
