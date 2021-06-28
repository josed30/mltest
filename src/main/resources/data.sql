drop table if exists adn_verification;

CREATE TABLE adn_verification (
  id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  dna VARCHAR(10000) NOT NULL,
  mutant BOOLEAN NOT NULL,
  UNIQUE KEY DNA_UK (dna)
  );
