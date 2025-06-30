CREATE TABLE company (
           company_id BIGINT AUTO_INCREMENT PRIMARY KEY,
           company_name VARCHAR(255) NOT NULL,
           active VARCHAR(50) NOT NULL,
           city VARCHAR(255) NOT NULL,
           stock_symbol VARCHAR(255) NOT NULL,
           currency_name VARCHAR(255) NOT NULL,
           company_URL VARCHAR(255) NOT NULL,
           description LONGTEXT NOT NULL,
           category VARCHAR(255) NOT NULL,
           logo_URL VARCHAR(255) NOT NULL,
           stock_id BIGINT,
           FOREIGN KEY (stock_id) REFERENCES stock(stock_id)
);
CREATE TABLE portfolio_stock (
             id BIGINT AUTO_INCREMENT PRIMARY KEY,
             portfolio_id BIGINT NOT NULL,
             stock_id BIGINT NOT NULL,
             user_Id BIGINT NOT NULL,
             FOREIGN KEY (portfolio_id) REFERENCES portfolio(portfolio_id) ON DELETE CASCADE,
             FOREIGN KEY (stock_id) REFERENCES stock(stock_id) ON DELETE CASCADE,
             FOREIGN KEY (user_Id) REFERENCES user(user_Id) ON DELETE CASCADE
);


CREATE TABLE market (
                market_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                user_id BIGINT,
                stock_id BIGINT,
                quantity BIGINT,
                price DECIMAL(15, 2),
                transaction_type ENUM('BUY', 'SELL'),
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (stock_id) REFERENCES stock(stock_id) ON DELETE CASCADE ON UPDATE CASCADE,
                FOREIGN KEY (user_id) REFERENCES user(user_Id) ON DELETE CASCADE ON UPDATE CASCADE
);
