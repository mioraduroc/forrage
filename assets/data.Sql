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

INSERT INTO statut (statut) VALUES
('En attente'),
('En cours'),
('Validée'),
('Refusée'),
('Terminée');




SELECT * FROM region;
SELECT * FROM district;
SELECT * FROM commune;