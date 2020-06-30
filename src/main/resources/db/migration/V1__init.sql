CREATE SEQUENCE IF NOT EXISTS accounts_id;
CREATE TABLE IF NOT EXISTS accounts
(
    id bigint NOT NULL DEFAULT nextval('accounts_id'),
    balance NUMERIC(10, 2) NOT NULL,
    PRIMARY KEY(id)
);


INSERT INTO accounts (balance) VALUES('100.00'), ('100.00');