package fontys_s3_eb.stock_project_individual.configuration.Security.TokenJWL.Implementation;


import fontys_s3_eb.stock_project_individual.configuration.Security.TokenJWL.AccessToken;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Collections;
import java.util.Set;

@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class AccessTokenImplementation implements AccessToken {

    private final Long userId;
    private final Set<String> roles;
    private final String subject;

    public AccessTokenImplementation( String subject, Long userId, String role) {

        this.userId = userId;
        this.roles = Collections.singleton(role);
        this.subject = subject;
    }


    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public Long getUserId() {
        return this.userId;
    }

    @Override
    public Set<String> getRoles() {
        return roles;
    }

    public boolean hasRole(String roleName) {
        return roles.contains(roleName);
    }
}
