DROP SCHEMA IF EXISTS oblig3 CASCADE;

CREATE SCHEMA oblig3;

set search_path TO oblig3;

-- Avdeling tabell
CREATE TABLE Avdeling (
    avdeling_id SERIAL PRIMARY KEY,
    navn VARCHAR(50) NOT NULL
    -- Sjef_id vil bli lagt til senere med ALTER TABLE
);

-- Ansatt tabell
CREATE TABLE Ansatt (
    ansatt_id SERIAL PRIMARY KEY,
    brukernavn VARCHAR(4) UNIQUE NOT NULL,
    fornavn VARCHAR(50) NOT NULL,
    etternavn VARCHAR(50) NOT NULL,
    ansettelsesdato DATE NOT NULL,
    stilling VARCHAR(50) NOT NULL,
    manedslonn DECIMAL(10, 2) NOT NULL,
    avdeling_id INTEGER NOT NULL,
    FOREIGN KEY (avdeling_id) REFERENCES Avdeling(avdeling_id)
);

-- Legger til sjef_id i Avdeling (måtte vente med dette pga. "hønen og egget")
ALTER TABLE Avdeling ADD COLUMN sjef_id INTEGER;
ALTER TABLE Avdeling ADD CONSTRAINT fk_sjef FOREIGN KEY (sjef_id) REFERENCES Ansatt(ansatt_id);

-- Prosjekt tabell
CREATE TABLE Prosjekt (
    prosjekt_id SERIAL PRIMARY KEY,
    navn VARCHAR(50) NOT NULL,
    beskrivelse TEXT
);

-- Prosjektdeltagelse (mange-til-mange mellom Ansatt og Prosjekt)
CREATE TABLE Prosjektdeltagelse (
    ansatt_id INTEGER NOT NULL,
    prosjekt_id INTEGER NOT NULL,
    rolle VARCHAR(50) NOT NULL,
    timer INTEGER NOT NULL DEFAULT 0,
    PRIMARY KEY (ansatt_id, prosjekt_id),
    FOREIGN KEY (ansatt_id) REFERENCES Ansatt(ansatt_id),
    FOREIGN KEY (prosjekt_id) REFERENCES Prosjekt(prosjekt_id)
);

-- Eksempeldata for Avdeling
INSERT INTO Avdeling (navn) VALUES ('IT-avdeling');
INSERT INTO Avdeling (navn) VALUES ('Markedsføring');
INSERT INTO Avdeling (navn) VALUES ('Økonomi');

-- Eksempeldata for Ansatt - legg merke til at vi ikke setter avdeling_id ennå
-- Vi må gjøre dette i to trinn grunnet avhengigheter
INSERT INTO Ansatt (brukernavn, fornavn, etternavn, ansettelsesdato, stilling, manedslonn, avdeling_id) 
VALUES ('jad', 'Jan', 'Danielsen', '2020-01-15', 'Systemutvikler', 55000, 1);
INSERT INTO Ansatt (brukernavn, fornavn, etternavn, ansettelsesdato, stilling, manedslonn, avdeling_id) 
VALUES ('ala', 'Anne', 'Larsen', '2019-03-20', 'Markedssjef', 65000, 2);
INSERT INTO Ansatt (brukernavn, fornavn, etternavn, ansettelsesdato, stilling, manedslonn, avdeling_id) 
VALUES ('phj', 'Petter', 'Hansen', '2021-06-10', 'Økonomisjef', 70000, 3);
INSERT INTO Ansatt (brukernavn, fornavn, etternavn, ansettelsesdato, stilling, manedslonn, avdeling_id) 
VALUES ('mni', 'Marit', 'Nielsen', '2022-01-05', 'Systemarkitekt', 60000, 1);
INSERT INTO Ansatt (brukernavn, fornavn, etternavn, ansettelsesdato, stilling, manedslonn, avdeling_id) 
VALUES ('oba', 'Olav', 'Bakken', '2020-11-20', 'Markedskonsulent', 45000, 2);

-- Oppdaterer Avdeling med sjefer
UPDATE Avdeling SET sjef_id = 1 WHERE avdeling_id = 1; -- Jan er sjef for IT
UPDATE Avdeling SET sjef_id = 2 WHERE avdeling_id = 2; -- Anne er sjef for Markedsføring
UPDATE Avdeling SET sjef_id = 3 WHERE avdeling_id = 3; -- Petter er sjef for Økonomi

-- Eksempeldata for Prosjekt
INSERT INTO Prosjekt (navn, beskrivelse) VALUES ('Nettbutikk', 'Implementering av ny nettbutikk');
INSERT INTO Prosjekt (navn, beskrivelse) VALUES ('Markedskampanje', 'Sommerkampanje 2022');
INSERT INTO Prosjekt (navn, beskrivelse) VALUES ('Nytt regnskapssystem', 'Migrering til nytt regnskapssystem');

-- Eksempeldata for Prosjektdeltagelse
INSERT INTO Prosjektdeltagelse (ansatt_id, prosjekt_id, rolle, timer) VALUES (1, 1, 'Prosjektleder', 120);
INSERT INTO Prosjektdeltagelse (ansatt_id, prosjekt_id, rolle, timer) VALUES (4, 1, 'Utvikler', 200);
INSERT INTO Prosjektdeltagelse (ansatt_id, prosjekt_id, rolle, timer) VALUES (2, 2, 'Ansvarlig', 80);
INSERT INTO Prosjektdeltagelse (ansatt_id, prosjekt_id, rolle, timer) VALUES (5, 2, 'Assistent', 100);
INSERT INTO Prosjektdeltagelse (ansatt_id, prosjekt_id, rolle, timer) VALUES (3, 3, 'Prosjektleder', 150);
