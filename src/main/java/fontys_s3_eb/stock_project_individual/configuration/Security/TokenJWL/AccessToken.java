package fontys_s3_eb.stock_project_individual.configuration.Security.TokenJWL;

import java.util.Set;

public interface AccessToken {
    String getSubject();

    Long getUserId();

    Set<String> getRoles();

    boolean hasRole(String roleName);
}
