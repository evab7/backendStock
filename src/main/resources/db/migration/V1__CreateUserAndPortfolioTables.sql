CREATE TABLE stock (
               stock_id BIGINT AUTO_INCREMENT PRIMARY KEY,
               company_name VARCHAR(100) NOT NULL,
               currency VARCHAR(10) NOT NULL,
               description VARCHAR(255),
               stock_symbol VARCHAR(10) NOT NULL UNIQUE
);

CREATE TABLE portfolio (
               portfolio_id BIGINT AUTO_INCREMENT PRIMARY KEY,
               comment VARCHAR(255),
               date DATE
);
CREATE TABLE user (
              user_Id BIGINT AUTO_INCREMENT PRIMARY KEY,
              username VARCHAR(50) NOT NULL UNIQUE,
              password VARCHAR(100),
              role VARCHAR(10)
);
CREATE TABLE refresh_token (

               id BIGINT AUTO_INCREMENT PRIMARY KEY,
               token VARCHAR(255) NOT NULL,
               expiry_date TIMESTAMP,
               user_id BIGINT,
               FOREIGN KEY (user_id) REFERENCES user(user_Id)
);
