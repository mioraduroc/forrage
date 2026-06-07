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
    sigle VARCHAR(20) UNIQUE,
    libelle VARCHAR(50) 
);

CREATE TABLE statutDemande (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idDemande INT NOT NULL ,
    idStatut INT NOT NULL,
    dateStatut DATETIME ,
    dt DECIMAL(10, 2),

    FOREIGN KEY (idDemande) references demande(id) ,
    FOREIGN KEY (idStatut) references statut(id)
);



CREATE TABLE type (
    id INT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(20) NOT NULL
);

CREATE TABLE devis (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idDemande INT NOT NULL,
    idType INT NOT NULL,
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    observation VARCHAR(50),

    FOREIGN KEY (idDemande) REFERENCES demande(id),
    FOREIGN KEY (idType) REFERENCES type(id)
);

CREATE TABLE detailDevis (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idDevis INT NOT NULL,
    libelle VARCHAR(50) NOT NULL,
    quantite INT NOT NULL,
    prixUnitaire DECIMAL(10,2) NOT NULL,

    FOREIGN KEY (idDevis) REFERENCES devis(id)
);


CREATE TABLE parametres(
    id INT AUTO_INCREMENT PRIMARY KEY,
    idStatut1 INT,
    idStatut2 INT,
    dt DECIMAL(10, 2),
    alerte VARCHAR(20)
);