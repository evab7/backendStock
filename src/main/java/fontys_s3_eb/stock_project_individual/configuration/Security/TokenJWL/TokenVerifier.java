package fontys_s3_eb.stock_project_individual.configuration.Security.TokenJWL;


public interface TokenVerifier {
    AccessToken decode(String accessTokenEncoded);
}
