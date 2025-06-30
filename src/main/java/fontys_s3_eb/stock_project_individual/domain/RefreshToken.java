package fontys_s3_eb.stock_project_individual.domain;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public class RefreshToken {


    private Long id;

    private String token;

    private Instant expiryDate;

    private UserEntity user;
}
