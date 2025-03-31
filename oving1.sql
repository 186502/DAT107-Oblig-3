DROP SCHEMA IF EXISTS oving1 CASCADE;

CREATE SCHEMA oving1;

set search_path TO oving1;

CREATE TABLE ansatt
(
brukernavn VARCHAR(4) NOT NULL PRIMARY KEY,
fornavn VARCHAR(40) NOT NULL,
etternavn VARCHAR(40) NOT NULL,
ansettelsesdato DATE NOT NULL,
stilling VARCHAR(30) NOT NULL,
maanedslonn INT NOT NULL
);

INSERT INTO
  ansatt(brukernavn, fornavn, etternavn, ansettelsesdato, stilling, maanedslonn)
VALUES
    ('AS', 'Åsmund', 'S', DATE '2020-05-10', 'administrator av db', 50000),
    ('ON', 'Ola', 'Nordmann', DATE '2022-06-11', 'revadilter', 5),
    ('SM', 'Scrooge', 'Mcduck', DATE '1950 01 01', 'CEO', 999999999);