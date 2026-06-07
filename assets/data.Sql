INSERT INTO client (nom, contact, adresse) VALUES
('Rakoto', '0321234567', 'Antananarivo'),
('Rabe', '0339876543', 'Antsirabe'),
('Rasoa', '0341122334', 'Toamasina');

INSERT INTO region (libelle) VALUES
('Analamanga'),
('Vakinankaratra'),
('Atsinanana');

INSERT INTO district (libelle, idRegion) VALUES
('Antananarivo Renivohitra', 1),
('Antananarivo Avaradrano', 1),
('Antsirabe I', 2),
('Antsirabe II', 2),
('Toamasina I', 3);

INSERT INTO commune (libelle, idDistrict) VALUES
('Analakely', 1),
('Ankazomanga', 1),
('Ambohimangakely', 2),
('Antsirabe Centre', 3),
('Antsirabe Nord', 4),
('Toamasina Ville', 5);

INSERT INTO statut (sigle,libelle) VALUES
('DC','Demande Crée'),
('DEC',"Devis d'étude crée"),
('DER',"Devis d'étude refusé"),
('DEA',"Devis d'étude acceptée"),
('DFC',"Devis de forage crée"),
('DFR',"Devis forage refusé"),
('DFA',"Devis de forage acceptée"),
('TC',"Travail commencé"),
('TT',"Travail terminé") ;


INSERT INTO type (libelle) VALUES
("Devis d'etude"),
("Devis de forage");

INSERT INTO
    parametres (idStatut1, idStatut2, DT, alerte)
VALUES
    (1, 2, 160, 'Jaune'),
    (1, 2, 180, 'Rouge'),
    (2, 4, 100, 'Jaune'),
    (4, 5, 80, 'Jaune'),
    (4, 8, 160, 'Jaune'),
    (4, 8, 190, 'Rouge');


SELECT * FROM region;
SELECT * FROM district;
SELECT * FROM commune;