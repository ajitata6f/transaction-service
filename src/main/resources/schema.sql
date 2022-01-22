CREATE TABLE IF NOT EXISTS account (
   id CHARACTER VARYING(36),
   balance DECIMAL(12,2) DEFAULT 0.00 NOT NULL,
   PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS transaction (
   id CHARACTER VARYING(36),
   account_id CHARACTER VARYING(36),
   currency VARCHAR(5),
   amount DECIMAL(12,2) NOT NULL,
   description TEXT,
   type VARCHAR(6),
   PRIMARY KEY (id),
   FOREIGN KEY (account_id) REFERENCES account(id)
);