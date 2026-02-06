-- Création de la base de données
CREATE DATABASE IF NOT EXISTS gestion_absences;
USE gestion_absences;

-- Table Classe
CREATE TABLE Classe (
    id_classe INT PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(50),
    niveau VARCHAR(20),
    filiere VARCHAR(50)
);

-- Table Etudiant
CREATE TABLE Etudiant (
    id_etudiant INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(50),
    prenom VARCHAR(50),
    login VARCHAR(50) UNIQUE,
    pwd VARCHAR(100),
    id_classe INT,
    FOREIGN KEY (id_classe) REFERENCES Classe(id_classe)
);

-- Table Enseignant
CREATE TABLE Enseignant (
    id_enseignant INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(50),
    prenom VARCHAR(50),
    login VARCHAR(50) UNIQUE,
    pwd VARCHAR(100)
);

-- Table Responsable
CREATE TABLE Responsable (
    id_responsable INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(50),
    prenom VARCHAR(50),
    login VARCHAR(50) UNIQUE,
    pwd VARCHAR(100)
);

-- Table Matiere
CREATE TABLE Matiere (
    id_matiere INT PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(100)
);

-- Table Absence
CREATE TABLE Absence (
    id_absence INT PRIMARY KEY AUTO_INCREMENT,
    id_etudiant INT,
    id_enseignant INT,
    id_matiere INT,
    numseance INT,
    date_absence DATE,
    est_justifie BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_etudiant) REFERENCES Etudiant(id_etudiant),
    FOREIGN KEY (id_enseignant) REFERENCES Enseignant(id_enseignant),
    FOREIGN KEY (id_matiere) REFERENCES Matiere(id_matiere)
);

-- Table Correspondance
CREATE TABLE Correspondance (
    id_enseignant INT,
    id_matiere INT,
    id_classe INT,
    PRIMARY KEY (id_enseignant, id_matiere, id_classe),
    FOREIGN KEY (id_enseignant) REFERENCES Enseignant(id_enseignant),
    FOREIGN KEY (id_matiere) REFERENCES Matiere(id_matiere),
    FOREIGN KEY (id_classe) REFERENCES Classe(id_classe)
);

-- Données de test avec noms et prénoms tunisiens
INSERT INTO Classe (libelle, niveau, filiere) VALUES 
('Classe A', 'L1', 'Informatique'),
('Classe B', 'L2', 'Mathématiques'),
('Classe C', 'L3', 'Électronique'),
('Classe D', 'M1', 'Génie Civil'),
('Classe E', 'M2', 'Mécanique');

-- Étudiants tunisiens (noms et prénoms courants)
INSERT INTO Etudiant (nom, prenom, login, pwd, id_classe) VALUES
('Ben Ali', 'Mohamed', 'mohamed.benali', '123', 1),
('Trabelsi', 'Fatma', 'fatma.trabelsi', '123', 1),
('Khalfallah', 'Amine', 'amine.khalfallah', '123', 1),
('Chaabane', 'Rania', 'rania.chaabane', '123', 1),
('Gharbi', 'Houssem', 'houssem.gharbi', '123', 1),

('Abid', 'Youssef', 'youssef.abid', '123', 2),
('Masmoudi', 'Sarra', 'sarra.masmoudi', '123', 2),
('Jlassi', 'Karim', 'karim.jlassi', '123', 2),
('Bouazizi', 'Noura', 'noura.bouazizi', '123', 2),
('Daoud', 'Aymen', 'aymen.daoud', '123', 2),

('Miled', 'Chaima', 'chaima.miled', '123', 3),
('Hammami', 'Khalil', 'khalil.hammami', '123', 3),
('Bougatef', 'Ines', 'ines.bougatef', '123', 3),
('Guedri', 'Zied', 'zied.guedri', '123', 3),
('Saidi', 'Marwa', 'marwa.saidi', '123', 3),

('Ben Ahmed', 'Nadia', 'nadia.benahmed', '123', 4),
('Rjab', 'Mohamed Ali', 'mohamedali.rjab', '123', 4),
('Mejri', 'Sana', 'sana.mejri', '123', 4),
('Zouari', 'Bassem', 'bassem.zouari', '123', 4),
('Baccouche', 'Hayet', 'hayet.baccouche', '123', 4),

('Karray', 'Raouf', 'raouf.karray', '123', 5),
('Maaouia', 'Selma', 'selma.maaouia', '123', 5),
('Ben Jemaa', 'Walid', 'walid.benjemaa', '123', 5),
('Dhraief', 'Mouna', 'mouna.dhraief', '123', 5),
('Makhlouf', 'Tarek', 'tarek.makhlouf', '123', 5);

-- Enseignants tunisiens
INSERT INTO Enseignant (nom, prenom, login, pwd) VALUES
('Ben Salem', 'Khaled', 'khaled.bensalem', '123'),
('Mezghani', 'Leila', 'leila.mezghani', '123'),
('Bouhlel', 'Mohamed Salah', 'mohamedsalah.bouhlel', '123'),
('Zghal', 'Henda', 'henda.zghal', '123'),
('Marrakchi', 'Rachid', 'rachid.marrakchi', '123'),
('Ghrairi', 'Amel', 'amel.ghrairi', '123'),
('Krichen', 'Samir', 'samir.krichen', '123');

-- Responsables administratifs
INSERT INTO Responsable (nom, prenom, login, pwd) VALUES
('Essid', 'Sami', 'sami.essid', 'admin123'),
('Ben Ammar', 'Nawel', 'nawel.benammar', 'admin123'),
('Jridi', 'Hatem', 'hatem.jridi', 'admin123');

-- Matières
INSERT INTO Matiere (libelle) VALUES
('Programmation Java'),
('Base de données'),
('Algorithmique'),
('Mathématiques Appliquées'),
('Électronique Numérique'),
('Réseaux Informatiques'),
('Génie Logiciel'),
('Systèmes d''Information'),
('Intelligence Artificielle'),
('Sécurité Informatique'),
('Statistiques'),
('Analyse Numérique'),
('Mécanique des Solides'),
('Résistance des Matériaux'),
('Architecture des Ordinateurs');

-- Correspondances Enseignant-Matière-Classe
INSERT INTO Correspondance (id_enseignant, id_matiere, id_classe) VALUES
-- Professeur Khaled Ben Salem
(1, 1, 1),  -- Java pour Classe A
(1, 2, 1),  -- BD pour Classe A
(1, 3, 1),  -- Algo pour Classe A

-- Professeur Leila Mezghani
(2, 4, 2),  -- Maths pour Classe B
(2, 11, 2), -- Stats pour Classe B
(2, 12, 2), -- Analyse Num pour Classe B

-- Professeur Mohamed Salah Bouhlel
(3, 5, 3),  -- Électronique pour Classe C
(3, 14, 3), -- Résistance Matériaux pour Classe C

-- Professeur Henda Zghal
(4, 6, 4),  -- Réseaux pour Classe D
(4, 10, 4), -- Sécurité Info pour Classe D

-- Professeur Rachid Marrakchi
(5, 7, 5),  -- Génie Logiciel pour Classe E
(5, 9, 5),  -- IA pour Classe E

-- Professeur Amel Ghrairi
(6, 8, 1),  -- SI pour Classe A
(6, 8, 2),  -- SI pour Classe B

-- Professeur Samir Krichen
(7, 15, 3), -- Architecture pour Classe C
(7, 15, 4); -- Architecture pour Classe D

-- Insertion de quelques absences de test
INSERT INTO Absence (id_etudiant, id_enseignant, id_matiere, numseance, date_absence, est_justifie) VALUES
-- Absences non justifiées
(1, 1, 1, 1, '2024-01-10', FALSE),
(2, 1, 1, 1, '2024-01-10', FALSE),
(6, 2, 4, 2, '2024-01-12', FALSE),
(11, 3, 5, 1, '2024-01-15', FALSE),
(16, 4, 6, 3, '2024-01-18', FALSE),

-- Absences justifiées
(3, 1, 2, 2, '2024-01-11', TRUE),
(7, 2, 11, 3, '2024-01-13', TRUE),
(12, 3, 14, 2, '2024-01-16', TRUE),
(17, 4, 10, 1, '2024-01-19', TRUE),
(21, 5, 7, 2, '2024-01-20', TRUE),

-- Plus d'absences pour les statistiques
(4, 1, 3, 3, '2024-01-14', FALSE),
(5, 1, 1, 4, '2024-01-17', FALSE),
(8, 2, 12, 4, '2024-01-21', FALSE),
(9, 2, 4, 5, '2024-01-24', TRUE),
(10, 2, 11, 6, '2024-01-25', FALSE),
(13, 3, 5, 7, '2024-01-28', FALSE),
(14, 3, 14, 8, '2024-01-29', TRUE),
(15, 3, 5, 9, '2024-01-30', FALSE),
(18, 4, 6, 10, '2024-02-01', FALSE),
(19, 4, 10, 11, '2024-02-02', TRUE),
(20, 4, 6, 12, '2024-02-03', FALSE),
(22, 5, 9, 13, '2024-02-04', FALSE),
(23, 5, 7, 14, '2024-02-05', TRUE),
(24, 5, 9, 15, '2024-02-06', FALSE),
(25, 5, 7, 16, '2024-02-07', FALSE);

-- Création de vues utiles
CREATE VIEW Vue_Absences_Detaillees AS
SELECT 
    a.id_absence,
    e.id_etudiant,
    e.nom AS nom_etudiant,
    e.prenom AS prenom_etudiant,
    c.libelle AS classe,
    ens.nom AS nom_enseignant,
    ens.prenom AS prenom_enseignant,
    m.libelle AS matiere,
    a.numseance,
    a.date_absence,
    a.est_justifie,
    CASE 
        WHEN a.est_justifie = 1 THEN 'Oui'
        ELSE 'Non'
    END AS justification
FROM Absence a
JOIN Etudiant e ON a.id_etudiant = e.id_etudiant
JOIN Enseignant ens ON a.id_enseignant = ens.id_enseignant
JOIN Matiere m ON a.id_matiere = m.id_matiere
JOIN Classe c ON e.id_classe = c.id_classe;

CREATE VIEW Vue_Statistiques_Classe AS
SELECT 
    c.id_classe,
    c.libelle AS classe,
    c.filiere,
    COUNT(DISTINCT e.id_etudiant) AS nombre_etudiants,
    COUNT(a.id_absence) AS total_absences,
    COUNT(CASE WHEN a.est_justifie = 1 THEN 1 END) AS absences_justifiees,
    COUNT(CASE WHEN a.est_justifie = 0 THEN 1 END) AS absences_non_justifiees,
    ROUND(COUNT(a.id_absence) * 100.0 / NULLIF(COUNT(DISTINCT e.id_etudiant), 0), 2) AS taux_absences
FROM Classe c
LEFT JOIN Etudiant e ON c.id_classe = e.id_classe
LEFT JOIN Absence a ON e.id_etudiant = a.id_etudiant
GROUP BY c.id_classe, c.libelle, c.filiere;

CREATE VIEW Vue_Etudiants_Absentéistes AS
SELECT 
    e.id_etudiant,
    e.nom,
    e.prenom,
    e.login,
    c.libelle AS classe,
    COUNT(a.id_absence) AS nombre_absences,
    COUNT(CASE WHEN a.est_justifie = 0 THEN 1 END) AS absences_non_justifiees,
    GROUP_CONCAT(DISTINCT m.libelle SEPARATOR ', ') AS matieres_absences
FROM Etudiant e
JOIN Classe c ON e.id_classe = c.id_classe
LEFT JOIN Absence a ON e.id_etudiant = a.id_etudiant
LEFT JOIN Matiere m ON a.id_matiere = m.id_matiere
GROUP BY e.id_etudiant, e.nom, e.prenom, e.login, c.libelle
HAVING COUNT(a.id_absence) > 0
ORDER BY absences_non_justifiees DESC;

-- Procédure stockée pour générer un rapport d'absences
DELIMITER $$
CREATE PROCEDURE GenererRapportAbsences(
    IN p_id_classe INT,
    IN p_date_debut DATE,
    IN p_date_fin DATE
)
BEGIN
    SELECT 
        c.libelle AS classe,
        e.nom AS nom_etudiant,
        e.prenom AS prenom_etudiant,
        m.libelle AS matiere,
        ens.nom AS nom_enseignant,
        a.numseance,
        a.date_absence,
        CASE 
            WHEN a.est_justifie = 1 THEN 'Justifiée'
            ELSE 'Non justifiée'
        END AS statut
    FROM Absence a
    JOIN Etudiant e ON a.id_etudiant = e.id_etudiant
    JOIN Classe c ON e.id_classe = c.id_classe
    JOIN Matiere m ON a.id_matiere = m.id_matiere
    JOIN Enseignant ens ON a.id_enseignant = ens.id_enseignant
    WHERE (p_id_classe IS NULL OR e.id_classe = p_id_classe)
    AND (p_date_debut IS NULL OR a.date_absence >= p_date_debut)
    AND (p_date_fin IS NULL OR a.date_absence <= p_date_fin)
    ORDER BY a.date_absence DESC, e.nom, e.prenom;
END$$
DELIMITER ;

-- Trigger pour empêcher la suppression d'un étudiant ayant des absences
DELIMITER $$
CREATE TRIGGER before_delete_etudiant
BEFORE DELETE ON Etudiant
FOR EACH ROW
BEGIN
    DECLARE nb_absences INT;
    
    SELECT COUNT(*) INTO nb_absences
    FROM Absence
    WHERE id_etudiant = OLD.id_etudiant;
    
    IF nb_absences > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Impossible de supprimer un étudiant ayant des absences enregistrées.';
    END IF;
END$$
DELIMITER ;

-- Fonction pour calculer le taux d'absentéisme d'un étudiant
DELIMITER $$
CREATE FUNCTION CalculerTauxAbsentéismeEtudiant(p_id_etudiant INT)
RETURNS DECIMAL(5,2)
DETERMINISTIC
BEGIN
    DECLARE total_seances INT;
    DECLARE absences_count INT;
    DECLARE taux DECIMAL(5,2);
    
    -- Estimation: 20 séances par mois en moyenne
    SET total_seances = 20;
    
    -- Compter les absences de l'étudiant (non justifiées seulement)
    SELECT COUNT(*) INTO absences_count
    FROM Absence
    WHERE id_etudiant = p_id_etudiant
    AND est_justifie = 0;
    
    -- Calculer le taux
    SET taux = (absences_count * 100.0) / total_seances;
    
    RETURN IFNULL(taux, 0);
END$$
DELIMITER ;

-- Index pour améliorer les performances
CREATE INDEX idx_etudiant_classe ON Etudiant(id_classe);
CREATE INDEX idx_absence_etudiant ON Absence(id_etudiant);
CREATE INDEX idx_absence_date ON Absence(date_absence);
CREATE INDEX idx_absence_justifie ON Absence(est_justifie);

-- Utilisateur avec droits limités pour l'application
CREATE USER 'app_gestion'@'localhost' IDENTIFIED BY 'app_password123';
GRANT SELECT, INSERT, UPDATE ON gestion_absences.* TO 'app_gestion'@'localhost';
GRANT EXECUTE ON PROCEDURE gestion_absences.GenererRapportAbsences TO 'app_gestion'@'localhost';
FLUSH PRIVILEGES;

-- Message de confirmation
SELECT 'Base de données créée avec succès avec des données tunisiennes!' AS Message;