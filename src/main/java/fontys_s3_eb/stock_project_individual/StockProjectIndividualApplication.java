package fontys_s3_eb.stock_project_individual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("fontys_s3_eb.stock_project_individual.Persistence.Entity")
@SpringBootApplication
public class StockProjectIndividualApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.configure().filename("PolygonKeyAPI.env").load();
		System.setProperty("POLYGON_API_KEY", dotenv.get("POLYGON_API_KEY"));


		String polygonApiKey = dotenv.get("POLYGON_API_KEY");

		System.out.println("Polygon API Key: " + polygonApiKey);

		SpringApplication.run(StockProjectIndividualApplication.class, args);
	}

}
