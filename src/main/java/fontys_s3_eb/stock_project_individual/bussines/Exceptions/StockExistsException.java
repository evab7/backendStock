package fontys_s3_eb.stock_project_individual.bussines.Exceptions;

public class StockExistsException extends RuntimeException {
    public StockExistsException(String message) {
        super(message);
    }
}
