CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS "holdings";
DROP TABLE IF EXISTS "transactions";
DROP TABLE IF EXISTS "users";

CREATE TABLE IF NOT EXISTS users (
        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
        username VARCHAR(50) UNIQUE NOT NULL,
        password VARCHAR(255) NOT NULL,
        balance NUMERIC(18, 8) NOT NULL DEFAULT 10000,
        session UUID DEFAULT uuid_generate_v4()
);

CREATE TABLE IF NOT EXISTS holdings (
        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
        user_id UUID REFERENCES users(id),
        symbol VARCHAR(10),
        amount NUMERIC(18, 8) NOT NULL
);

CREATE TABLE IF NOT EXISTS transactions (
        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
        user_id UUID REFERENCES users(id),
        type VARCHAR(4) NOT NULL, --This should be "BUY" or "SELL"
        symbol VARCHAR(10) NOT NULL,
        amount NUMERIC(18, 8) NOT NULL,
        price NUMERIC(18, 8) NOT NULL,
        total NUMERIC(18, 8) NOT NULL,
        transaction_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);