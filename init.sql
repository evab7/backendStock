CREATE DATABASE IF NOT EXISTS Stock_project;
--
-- CREATE TABLE user (
--                       user_Id BIGINT AUTO_INCREMENT PRIMARY KEY,
--                       username VARCHAR(50) NOT NULL ,
--                       password VARCHAR(100),
--                       role VARCHAR(10)
--
-- );
-- CREATE TABLE refresh_token (
--                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
--                                token VARCHAR(255) NOT NULL,
--                                expiry_date TIMESTAMP,
--                                user_id BIGINT,
--                                FOREIGN KEY (user_id) REFERENCES user(user_Id)
-- );
-- CREATE TABLE stock (
--                        stock_id BIGINT AUTO_INCREMENT PRIMARY KEY,
--                        company_name VARCHAR(100) NOT NULL,
--                        currency VARCHAR(10) NOT NULL,
--                        description VARCHAR(255),
--                        stock_symbol VARCHAR(10) NOT NULL UNIQUE
-- );
--
--
-- CREATE TABLE company (
--                          company_id BIGINT AUTO_INCREMENT PRIMARY KEY,
--                          company_name VARCHAR(255) NOT NULL,
--                          active VARCHAR(50) NOT NULL,
--                          city VARCHAR(255) NOT NULL,
--                          stock_symbol VARCHAR(255) NOT NULL,
--                          currency_name VARCHAR(255) NOT NULL,
--                          company_URL VARCHAR(255) NOT NULL,
--                          description TEXT NOT NULL,
--                          category VARCHAR(255) NOT NULL,
--                          logo_URL VARCHAR(255) NOT NULL,
--                          stock_id BIGINT,
--                          FOREIGN KEY (stock_id) REFERENCES stock(stock_id)
-- );
