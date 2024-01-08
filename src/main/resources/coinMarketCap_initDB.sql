USE smartstorage;

CREATE TABLE IF NOT EXISTS crypto_currency_data (
       id BIGINT PRIMARY KEY AUTO_INCREMENT,
       ticker VARCHAR(255),
       price DOUBLE,
       currency VARCHAR(255)
);