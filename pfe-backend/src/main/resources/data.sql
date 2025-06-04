INSERT INTO admin (id, username, password) VALUES (1, 'admin', 'admin'), (2, 'user1', 'pass1'), (3, 'user2', 'pass2');

INSERT INTO filiere (id, nom, nbr_etd) VALUES
    (1, 'Informatique', 120),
    (2, 'Génie Civil', 90),
    (3, 'Mécanique', 100),
    (4, 'Électrique', 80),
    (5, 'Chimie', 70);

INSERT INTO salle (id, nom_salle, capacite, filiere_id, type_salle) VALUES
    (1, 'Salle A1', 50, 1, 'COURT'),
    (2, 'Salle B2', 40, 2, 'TD'),
    (3, 'Salle C3', 60, 1, 'COURT'),
    (4, 'Labo Info', 30, 1, 'TP'),
    (5, 'Amphi A', 150, NULL, 'COURT'),
    (6, 'Salle D4', 45, 3, 'TD'),
    (7, 'Labo Chimie', 20, 5, 'TP');

INSERT INTO seance (id, jour, nom_seance, number_of_students, periode, filiere_id, salle_id, type_seance) VALUES
    (1, 'MONDAY', 'Cours Java', 30, 'P1', 1, 1, 'COURT'),
    (2, 'TUESDAY', 'Travaux Dirigés Algèbre', 25, 'P2', 2, 2, 'TD'),
    (3, 'WEDNESDAY', 'Cours de thermodynamique', 35, 'P3', 3, 3, 'COURT'),
    (4, 'THURSDAY', 'TP Réseaux', 28, 'P1', 1, 4, 'TP'),
    (5, 'FRIDAY', 'Cours Électronique', 40, 'P2', 4, 3, 'COURT'),
    (6, 'MONDAY', 'TD Mécanique', 30, 'P2', 3, 6, 'TD'),
    (7, 'TUESDAY', 'TP Chimie', 20, 'P3', 5, 7, 'TP'),
    (8, 'WEDNESDAY', 'Cours Bases de Données', 50, 'P1', 1, 1, 'COURT'),
    (9, 'THURSDAY', 'Cours Algorithmique', 45, 'P3', 1, 3, 'COURT'),
    (10, 'FRIDAY', 'TD Électronique', 20, 'P1', 4, 2, 'TD'),
    (11, 'MONDAY', 'Intro Algo', 30, 'P2', 1, 1, 'COURT'),
    (12, 'TUESDAY', 'Structure de données', 30, 'P3', 1, 4, 'TP'),
    (13, 'MONDAY', 'Statique', 25, 'P1', 2, 2, 'TD'),
    (14, 'WEDNESDAY', 'Dynamique', 25, 'P3', 2, 2, 'TD'),
    (15, 'THURSDAY', 'Matériaux de construction', 25, 'P2', 2, 2, 'COURT'),
    (16, 'FRIDAY', 'Dessin technique', 25, 'P3', 2, 2, 'TD'),
    (17, 'MONDAY', 'Topographie', 25, 'P4', 2, 2, 'COURT'),
    (18, 'TUESDAY', 'Mécanique des fluides', 35, 'P1', 3, 6, 'COURT'),
    (19, 'WEDNESDAY', 'RDM', 35, 'P2', 3, 3, 'TD'),
    (20, 'THURSDAY', 'Conception assistée par ordinateur', 35, 'P4', 3, 6, 'TP'),
    (21, 'FRIDAY', 'Systèmes mécaniques', 35, 'P4', 3, 3, 'COURT'),
    (22, 'MONDAY', 'Circuits électriques', 40, 'P3', 4, 3, 'COURT'),
    (23, 'TUESDAY', 'Électronique de puissance', 40, 'P1', 4, 2, 'COURT'),
    (24, 'WEDNESDAY', 'Machines électriques', 40, 'P2', 4, 3, 'TD'),
    (25, 'THURSDAY', 'Automatisme', 40, 'P4', 4, 2, 'TP'),
    (26, 'MONDAY', 'Chimie organique', 20, 'P1', 5, 7, 'COURT'),
    (27, 'WEDNESDAY', 'Chimie inorganique', 20, 'P2', 5, 7, 'TD'),
    (28, 'THURSDAY', 'Thermochimie', 20, 'P1', 5, 7, 'COURT'),
    (29, 'FRIDAY', 'Cinétique chimique', 20, 'P4', 5, 7, 'TP'),
    (30, 'MONDAY', 'Spectroscopie', 20, 'P5', 5, 7, 'COURT'); 