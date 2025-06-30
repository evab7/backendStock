package fontys_s3_eb.stock_project_individual.bussines.PortfolioUseCases.Implementation;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.PortfolioEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Entity.PortfolioStockEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Entity.StockEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Entity.UserEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.PortfolioRepository;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.PortfolioStockRepository;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.StockRepository;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.UserRepository;
import fontys_s3_eb.stock_project_individual.bussines.PortfolioUseCases.UseCase.AddStockToPortoflioUseCase;
import fontys_s3_eb.stock_project_individual.domain.PortfolioPackage.AddStockToPortfolioRequest;
import fontys_s3_eb.stock_project_individual.domain.PortfolioPackage.AddStockToPortfolioResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AddStockToPortfolioUseCaseImpl implements AddStockToPortoflioUseCase {

    private final PortfolioStockRepository portfolioStockRepository;
    private final StockRepository stockRepository;
    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;

    @Override
    public AddStockToPortfolioResponse addStockToPortfolioResponse(AddStockToPortfolioRequest addStockToPortfolioRequest) {

        UserEntity user;
        if (addStockToPortfolioRequest.getUserId() == null) {
            throw new IllegalArgumentException("Invalid request: User ID cannot be null.");
        }
        try {
            Optional<UserEntity> userEntity = userRepository.findById(addStockToPortfolioRequest.getUserId());
            if (userEntity.isPresent()) {
                user = userEntity.get();
            } else {
                return AddStockToPortfolioResponse.builder()
                        .success("Failed")
                        .build();
            }
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Invalid request: User ID cannot be null.");
        }



        if (addStockToPortfolioRequest.getStockId() == null) {
            throw new IllegalArgumentException("Invalid request: User ID cannot be null.");
        }
        StockEntity stockEntity = stockRepository.findByStockId(addStockToPortfolioRequest.getStockId());

        PortfolioEntity savedPortfolio = portfolioRepository.save(PortfolioEntity.builder()
                .comment(addStockToPortfolioRequest.getComment())
                .date(new Date(System.currentTimeMillis()))
                .build());

        if(savedPortfolio != null) {
            PortfolioStockEntity portfolio =  PortfolioStockEntity.builder()
                    .portfolio(savedPortfolio)
                    .user(user)
                    .stock(stockEntity)
                    .build();
            PortfolioStockEntity entity = portfolioStockRepository.save(portfolio);

            return AddStockToPortfolioResponse.builder()
                    .success("Success")
                    .build();

        }

        return AddStockToPortfolioResponse.builder()
                .success("Failed")
                .build();



    }
}
