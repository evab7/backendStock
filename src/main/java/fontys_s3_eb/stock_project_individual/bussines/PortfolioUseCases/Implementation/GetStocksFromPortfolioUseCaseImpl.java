package fontys_s3_eb.stock_project_individual.bussines.PortfolioUseCases.Implementation;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.PortfolioStockEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Entity.UserEntity;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.PortfolioStockRepository;
import fontys_s3_eb.stock_project_individual.Persistence.Implementation.UserRepository;
import fontys_s3_eb.stock_project_individual.bussines.EntityConverters.StockConverter;
import fontys_s3_eb.stock_project_individual.bussines.PortfolioUseCases.UseCase.GetStocksFromPortolioUseCase;
import fontys_s3_eb.stock_project_individual.domain.PortfolioPackage.GetPortfolioStockRequest;
import fontys_s3_eb.stock_project_individual.domain.PortfolioPackage.GetPortfolioStockResponse;
import fontys_s3_eb.stock_project_individual.domain.PortfolioPackage.Portfolio;
import fontys_s3_eb.stock_project_individual.domain.StockPackage.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class GetStocksFromPortfolioUseCaseImpl implements GetStocksFromPortolioUseCase {

    private final UserRepository userRepository;
    private final PortfolioStockRepository portfolioStockRepository;


    @Override
    public GetPortfolioStockResponse getStocksFromPortolio(GetPortfolioStockRequest request) {

        if (request.getUserId() == null ) {
            throw new IllegalArgumentException("Invalid request: User ID cannot be null.");
        }


        UserEntity userEntity = userRepository.findByUserId(request.getUserId());

        List<PortfolioStockEntity> portfolioStock = portfolioStockRepository.findByUser(userEntity);



        List<Portfolio> portfolioList = new ArrayList<>();

        for (PortfolioStockEntity portfolioStockEntity : portfolioStock) {

            Stock stock = StockConverter.convert(portfolioStockEntity.getStock());
            Portfolio portfolio = Portfolio.builder()
                    .stock(stock)
                    .date(portfolioStockEntity.getPortfolio().getDate())
                    .comment(portfolioStockEntity.getPortfolio().getComment())
                    .build();
            portfolioList.add(portfolio);
        }

        return GetPortfolioStockResponse.builder()
                .portfolios(portfolioList)
                .build();
    }
}
