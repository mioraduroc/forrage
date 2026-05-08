DROP DATABASE IF EXISTS forage;
CREATE DATABASE forage;
USE forage;


CREATE TABLE client (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(20) ,
    contact VARCHAR(50) ,
    adresse VARCHAR(50)
);

CREATE TABLE region (
    id  INT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(50)
);

CREATE TABLE district (
    id INT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(50),
    idRegion INT NOT NULL ,

    FOREIGN KEY (idRegion) references region(id) 
);

CREATE TABLE commune (
    id INT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(50) ,
    idDistrict INT  NOT NULL ,

    FOREIGN KEY (idDistrict) references district(id)
);

CREATE TABLE demande (
    id INT AUTO_INCREMENT PRIMARY KEY,
    reference VARCHAR(20) UNIQUE ,
    idClient INT ,
    lieu VARCHAR(20) ,
    idCommune INT ,

    FOREIGN KEY (idClient) references client(id) ,
    FOREIGN KEY (idCommune) references commune(id) 
);

CREATE TABLE statut (
    id INT AUTO_INCREMENT PRIMARY KEY,
    statut VARCHAR(20) UNIQUE
);

CREATE TABLE statutDemande (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idDemande INT NOT NULL ,
    idStatut INT NOT NULL,
    dateStatut DATETIME ,

    FOREIGN KEY (idDemande) references demande(id) ,
    FOREIGN KEY (idStatut) references statut(id)
);

