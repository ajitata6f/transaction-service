CREATE TABLE IF NOT EXISTS account (
    id VARCHAR(19) PRIMARY KEY,
    balance DECIMAL(12,2) DEFAULT 0.00 NOT NULL
);

CREATE TABLE IF NOT EXISTS transaction (
    id VARCHAR(19) PRIMARY KEY,
    account_id VARCHAR(19),
    currency VARCHAR(5),
    amount DECIMAL(12,2) NOT NULL,
    description TEXT,
    type VARCHAR(5),
    FOREIGN KEY (account_id) REFERENCES account(id)
);

