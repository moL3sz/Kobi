CREATE DATABASE kobi_adatb
DEFAULT CHARACTER SET utf8 COLLATE utf8_hungarian_ci;
USE kobi_adatb;

CREATE TABLE felhasznalok(
	id INT NOT NULL AUTO_INCREMENT,
	email VARCHAR(30),
	username VARCHAR(20),
	jelszo VARCHAR(20),
	token VARCHAR(40),
	/*avatar*/
	CONSTRAINT pk_felh PRIMARY KEY (id)
);

CREATE TABLE ital_kategoriak(
	id INT NOT NULL AUTO_INCREMENT,	
	nev VARCHAR(20),
	CONSTRAINT pk_ik PRIMARY KEY (id)
);

CREATE TABLE full_ital_lista(
	id INT NOT NULL AUTO_INCREMENT,	
	kat_id INT,
	nev VARCHAR(20),
	alksz DOUBLE,
	/*icon*/
	szinkod VARCHAR(8),
	CONSTRAINT pk_ik PRIMARY KEY (id)
);

CREATE TABLE achivmentek(
	id INT NOT NULL AUTO_INCREMENT,	
	nev VARCHAR(30),
	ponthatar INT,
	CONSTRAINT pk_ach PRIMARY KEY (id)
);

CREATE TABLE megszerzett_achivmentek(
	id INT NOT NULL AUTO_INCREMENT,
	fh_id INT,
	ach_id INT,
	CONSTRAINT pk_mszach PRIMARY KEY (id)
);

CREATE TABLE koktel_kiegeszitok(
	id INT NOT NULL AUTO_INCREMENT,	
	nev VARCHAR(20),
	CONSTRAINT pk_kieg PRIMARY KEY (id)
);

CREATE TABLE elkeszitett_koktelok(
	id INT NOT NULL AUTO_INCREMENT,
	fh_id INT,
	felh_ital_id INT,
	felh_kieg_id INT,
	ar INT,
	CONSTRAINT pk_ek PRIMARY KEY (id)
);

ALTER TABLE elkeszitett_koktelok ADD CONSTRAINT fk_koktelok_italok FOREIGN KEY (felh_ital_id) REFERENCES full_ital_lista (id);
ALTER TABLE elkeszitett_koktelok ADD CONSTRAINT fk_koktelok_kiegeszitok FOREIGN KEY (felh_kieg_id) REFERENCES koktel_kiegeszitok (id);
ALTER TABLE elkeszitett_koktelok ADD CONSTRAINT fk_koktelok_felhasznalok FOREIGN KEY (fh_id) REFERENCES felhasznalok (id);
ALTER TABLE full_ital_lista ADD CONSTRAINT fk_italok_kategoriak FOREIGN KEY (kat_id) REFERENCES ital_kategoriak (id);
ALTER TABLE megszerzett_achivmentek ADD CONSTRAINT fk_machivmentek_achivmentek FOREIGN KEY (ach_id) REFERENCES achivmentek (id);
ALTER TABLE megszerzett_achivmentek ADD CONSTRAINT fk_machivmentek_felhasznalok FOREIGN KEY (fh_id) REFERENCES felhasznalok (id);
/*INSERT INTO felhasznalok(felhasznalonev, jelszo, beosztas, f_id) VALUES ("admin","Admin123","főnök",1),("kisfonok","Kisfonok123","csoportvezető",2);
ALTER TABLE felhasznalok ADD CONSTRAINT fk_felh_szellemi FOREIGN KEY (f_id) REFERENCES szellemi_munkasok (id);*/