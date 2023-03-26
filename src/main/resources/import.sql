-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(nextval('hibernate_sequence'), 'field-1');
-- insert into myentity (id, field) values(nextval('hibernate_sequence'), 'field-2');
-- insert into myentity (id, field) values(nextval('hibernate_sequence'), 'field-3');

CREATE TABLE IF NOT EXISTS CUSTOMER (
id VARCHAR(255) PRIMARY KEY,
username VARCHAR(128) NOT NULL,
password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS WALLET (
id VARCHAR(255) PRIMARY KEY,
user_id VARCHAR(255) NOT NULL,
balance NUMBER
);

CREATE TABLE IF NOT EXISTS TRANSFER (
id VARCHAR(255) PRIMARY KEY,
user_origin VARCHAR(255),
user_destination VARCHAR(255) NOT NULL,
wallet_origin  VARCHAR(255),
wallet_destination VARCHAR(255) NOT NULL,
amount NUMBER NOT NULL,
transfer_type VARCHAR(255) NOT NULL,
date_at timestamp NOT NULL
);