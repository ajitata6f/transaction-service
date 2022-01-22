CREATE TABLE IF NOT EXISTS account (
   id serial,
   balance DECIMAL(12,2) DEFAULT 0.00 NOT NULL,
   PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS transaction (
    id serial,
    account_id integer,
    currency VARCHAR(5),
    amount DECIMAL(12,2) NOT NULL,
    description TEXT,
    type VARCHAR(5),
    PRIMARY KEY (id),
    FOREIGN KEY (account_id) REFERENCES account(id)
);
