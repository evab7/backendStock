package fontys_s3_eb.stock_project_individual.configuration.Security.TokenJWL.Implementation;

import fontys_s3_eb.stock_project_individual.configuration.Security.TokenJWL.AccessToken;
import fontys_s3_eb.stock_project_individual.configuration.Security.TokenJWL.TokenVerifier;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.Key;
import java.util.List;

@Service
public class TokenVerifierImplementation implements TokenVerifier {

    private final Key key;

    public TokenVerifierImplementation(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = (byte[]) Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
    @Override
    public AccessToken decode(String accessTokenEncoded) {

        Jwt<?, Claims> jwt = Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(accessTokenEncoded);
        Claims claims = (Claims)jwt.getBody();
        List<String> roles = claims.get("roles", List.class);
        String role = (roles != null && !roles.isEmpty()) ? roles.get(0) : null;
        Long userId = (Long)claims.get("userId", Long.class);
        return new AccessTokenImplementation(claims.getSubject(), userId, role);
    }

}
