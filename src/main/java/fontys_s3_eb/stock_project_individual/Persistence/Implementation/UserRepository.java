package fontys_s3_eb.stock_project_individual.Persistence.Implementation;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
    UserEntity findByUserId(Long userId);

}
