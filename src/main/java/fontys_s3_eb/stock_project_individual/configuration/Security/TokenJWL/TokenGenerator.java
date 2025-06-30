package fontys_s3_eb.stock_project_individual.configuration.Security.TokenJWL;

public interface TokenGenerator {
    String encode(AccessToken accessToken);
}
