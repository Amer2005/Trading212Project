DROP TABLE IF EXISTS "holdings";
DROP TABLE IF EXISTS "transactions";
DROP TABLE IF EXISTS "users";

CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       balance NUMERIC(18, 8) NOT NULL DEFAULT 10000
);

CREATE TABLE holdings (
                          user_id INTEGER REFERENCES users(id),
                          symbol VARCHAR(10),
                          amount NUMERIC(18, 8) NOT NULL,
                          PRIMARY KEY (user_id, symbol)
);

CREATE TABLE transactions (
                              id SERIAL PRIMARY KEY,
                              user_id INTEGER REFERENCES users(id),
                              type VARCHAR(4) NOT NULL, --This should be "BUY" or "SELL"
                              symbol VARCHAR(10) NOT NULL,
                              amount NUMERIC(18, 8) NOT NULL,
                              price NUMERIC(18, 8) NOT NULL,
                              total NUMERIC(18, 8) NOT NULL,
                              timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);