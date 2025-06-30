package fontys_s3_eb.stock_project_individual.configuration.Security.TokenJWL.Implementation;

import fontys_s3_eb.stock_project_individual.configuration.Security.TokenJWL.AccessToken;
import fontys_s3_eb.stock_project_individual.configuration.Security.TokenJWL.TokenGenerator;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.security.Key;
import io.jsonwebtoken.Jwts;

@Service
public class TokenGeneratorImplementation implements TokenGenerator {

    private final Key key;

    public TokenGeneratorImplementation(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = (byte[]) Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
    @Override
    public String encode(AccessToken accessToken) {


        Map<String, Object> claimsMap = new HashMap<>();



        if (accessToken.getRoles() != null) {
            claimsMap.put("roles", accessToken.getRoles());
        }



        if (accessToken.getUserId() != null) {
            claimsMap.put("userId", accessToken.getUserId());
        }



        Instant now = Instant.now();



        String subject = accessToken.getUserId() != null ? String.valueOf(accessToken.getUserId()) : "unknown";




        return Jwts.builder()
                .setSubject(subject)

                .setIssuedAt(Date.from(now))

                .setExpiration(Date.from(now.plus(30L, ChronoUnit.MINUTES)))
                .addClaims(claimsMap)
                .signWith(this.key)
                .compact();

    }

}
