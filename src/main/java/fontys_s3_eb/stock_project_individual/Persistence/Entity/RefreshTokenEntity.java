package fontys_s3_eb.stock_project_individual.Persistence.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "refresh_token")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;


        @NotBlank
        @Column(name = "token")
        private String token;


        @Column(name = "expiry_date")
        private Instant expiryDate;

        @OneToOne
        @PrimaryKeyJoinColumn
        private UserEntity user;



}
