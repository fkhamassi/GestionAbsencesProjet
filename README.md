# Rapport Technique - Système de Gestion des Absences

## Table des matières
1. [Introduction](#introduction)
2. [Architecture du Projet](#architecture-du-projet)
3. [Structure des Données](#structure-des-données)
4. [Composants du Système](#composants-du-système)
5. [Flux de Fonctionnement](#flux-de-fonctionnement)
6. [Guide d'Utilisation](#guide-dutilisation)

---

## Introduction

### Objectif
Le **Système de Gestion des Absences** est une application Java permettant de gérer les absences des étudiants dans un établissement d'enseignement. Le système offre une interface différenciée pour trois types d'utilisateurs : les étudiants, les enseignants et les responsables administratifs.

### Caractéristiques Principales
- ✓ Authentification multi-profils (Étudiant, Enseignant, Responsable)
- ✓ Enregistrement des absences par matière et séance
- ✓ Justification des absences par les étudiants
- ✓ Génération de rapports et statistiques d'absentéisme
- ✓ Alertes par email
- ✓ Recommandations basées sur les taux d'absentéisme

---

## Architecture du Projet

### Vue d'ensemble
```
GestionAbsencesProjet/
├── src/
│   ├── application/          # Couche présentation (Interface utilisateur)
│   │   ├── Main.java
│   │   ├── MenuEnseignant.java
│   │   ├── MenuEtudiant.java
│   │   └── MenuResponsable.java
│   │
│   ├── beans/                # Couche métier (Modèles de données)
│   │   ├── Utilisateur.java (classe abstraite)
│   │   ├── Etudiant.java
│   │   ├── Enseignant.java
│   │   ├── Responsable.java
│   │   ├── Absence.java
│   │   ├── Classe.java
│   │   ├── Matiere.java
│   │   └── (autres beans)
│   │
│   ├── dao/                  # Couche données (Accès à la base de données)
│   │   ├── DatabaseConnection.java
│   │   ├── UtilisateurDAO.java
│   │   ├── EtudiantDAO.java
│   │   ├── EnseignantDAO.java
│   │   ├── AbsenceDAO.java
│   │   └── StatistiqueDAO.java
│   │
│   ├── services/             # Couche métier (Logique applicative)
│   │   ├── AuthentificationService.java
│   │   ├── EmailService.java
│   │   ├── RapportService.java
│   │   ├── StatistiqueService.java
│   │   └── (autres services)
│   │
│   └── utils/                # Utilitaires
│       └── TextUtil.java
│
├── database/
│   └── script.sql           # Scripts de création de base de données
└── lib/                      # Dépendances externes
```

### Modèle Architectural
Le projet suit le **pattern MVC (Model-View-Controller)** avec une séparation claire en 3 couches :

```
┌─────────────────────────────────────────────────┐
│      APPLICATION (Présentation)                 │
│  Main.java, MenuEnseignant.java, etc.          │
└─────────────────────────────────────────────────┘
                        ↓
┌─────────────────────────────────────────────────┐
│      SERVICES (Logique Métier)                  │
│  AuthentificationService, EmailService, etc.   │
└─────────────────────────────────────────────────┘
                        ↓
┌─────────────────────────────────────────────────┐
│      DAO (Accès Données)                        │
│  EtudiantDAO, AbsenceDAO, etc.                  │
└─────────────────────────────────────────────────┘
                        ↓
┌─────────────────────────────────────────────────┐
│      BASE DE DONNÉES (MySQL)                    │
└─────────────────────────────────────────────────┘
```

---

## Structure des Données

### Entités Principales

#### 1. **Utilisateur (Classe Abstraite)**
```java
public abstract class Utilisateur {
    - id: int                  // Identifiant unique
    - nom: String             // Nom de famille
    - prenom: String          // Prénom
    - login: String           // Identifiant de connexion
    - password: String        // Mot de passe (hashé)
}
```
**Rôle** : Classe parent définissant les propriétés communes à tous les utilisateurs du système.

---

#### 2. **Étudiant**
```java
public class Etudiant extends Utilisateur {
    - idClasse: int           // Classe de l'étudiant
    - email: String           // Email (généré: login@univ.fr)
}
```
**Rôle** : Représente un étudiant inscrit dans une classe. Hérite des propriétés de Utilisateur.

---

#### 3. **Enseignant**
```java
public class Enseignant extends Utilisateur {
    // Propriétés héritées de Utilisateur
    // Les enseignants peuvent enseigner plusieurs matières à plusieurs classes
}
```
**Rôle** : Représente un membre du personnel enseignant responsable de l'enregistrement des absences.

---

#### 4. **Responsable**
```java
public class Responsable extends Utilisateur {
    // Propriétés héritées de Utilisateur
    // Accès complet aux statistiques et recommandations
}
```
**Rôle** : Représente un responsable administratif pour la gestion et l'analyse des absences.

---

#### 5. **Absence**
```java
public class Absence {
    - idAbsence: int          // Identifiant unique
    - idEtudiant: int         // Référence à l'étudiant
    - idEnseignant: int       // Référence à l'enseignant qui l'a enregistrée
    - idMatiere: int          // Référence à la matière
    - numSeance: int          // Numéro de la séance
    - dateAbsence: Date       // Date de l'absence
    - estJustifie: boolean    // État de justification
    - nomEtudiant: String     // Nom de l'étudiant (cache)
    - nomMatiere: String      // Nom de la matière (cache)
}
```
**Rôle** : Enregistre une absence d'un étudiant pour une matière donnée.

---

#### 6. **Classe**
```java
public class Classe {
    - idClasse: int           // Identifiant unique
    - libelle: String         // Nom de la classe (ex: L1-Informatique)
    - filiere: String         // Filière d'études
}
```
**Rôle** : Regroupe les étudiants par classe et filière.

---

#### 7. **Matière**
```java
public class Matiere {
    - idMatiere: int          // Identifiant unique
    - libelle: String         // Nom de la matière
    - nbrHeures: int          // Nombre d'heures
}
```
**Rôle** : Représente une matière enseignée dans l'établissement.

---

## Composants du Système

### 1. Couche Présentation (application/)

#### **Main.java**
**Objectif** : Point d'entrée de l'application.

**Fonctionnalités** :
- Affiche le menu de connexion principal
- Gère l'authentification des trois types d'utilisateurs
- Redirige vers le menu approprié selon le profil
- Gère la boucle principale de l'application

**Flux** :
```
1. Affiche le menu de connexion
2. Demande le type d'utilisateur (Étudiant/Enseignant/Responsable)
3. Récupère les identifiants (login/password)
4. Appelle AuthentificationService pour vérifier les droits
5. Redirige vers le menu du profil
```

---

#### **MenuEtudiant.java**
**Objectif** : Interface spécifique aux étudiants.

**Fonctionnalités** :
```
1. Consulter mes absences
   - Affiche la liste des absences avec détails
   - Calcule le taux d'absentéisme
   - Affiche les absences justifiées/non justifiées

2. Déposer une justification
   - Sélectionne une absence
   - Fournit un motif
   - Option pour joindre un document
```

---

#### **MenuEnseignant.java**
**Objectif** : Interface dédiée aux enseignants.

**Fonctionnalités** :
```
1. Remplir liste de présence
   - Sélectionne la classe et la matière
   - Enregistre la séance
   - Marque les étudiants présents/absents
   - Enregistre les absences en base

2. Consulter absences de ma classe
   - Affiche les absences par classe
```

---

#### **MenuResponsable.java**
**Objectif** : Interface de gestion administrative complète.

**Fonctionnalités** :
```
1. Consulter toutes les absences
   - Affiche les absences par classe (0 = toutes)
   
2. Annuler une absence
   - Supprime une absence de la base de données
   
3. Imprimer liste d'absences
   - Génère un fichier texte
   
4. Générer statistiques
   - Par classe, par filière, ou rapport complet
   - Affiche les taux d'absentéisme
   
5. Envoyer alertes par email
   - Alerte personnalisée ou standard
   - À tous les étudiants ou un seul
   
6. Générer recommandations
   - Automatiques (basées sur taux)
   - Personnalisées (saisie libre)
```

---

### 2. Couche Données (dao/)

#### **DatabaseConnection.java**
**Objectif** : Gère la connexion à la base de données MySQL.

**Responsabilités** :
- Établit la connexion à MySQL
- Gère les paramètres de connexion (URL, user, password)
- Fournit une connexion réutilisable
- Gère les exceptions de connexion

```java
public class DatabaseConnection {
    public static Connection getConnection() {
        // Établit la connexion avec les paramètres MySQL
        // Retourne un objet Connection
    }
}
```

---

#### **UtilisateurDAO.java**
**Objectif** : Authentifier les utilisateurs.

**Méthodes** :
```java
public Object authentifier(String login, String password, String type)
```

**Logique** :
1. Reçoit le login, password et type (etudiant/enseignant/responsable)
2. Construit une requête SQL SELECT vers la table appropriée
3. Vérifie les identifiants
4. Retourne l'objet utilisateur (Etudiant/Enseignant/Responsable) ou null

**Sécurité** : Utilise PreparedStatement pour éviter les injections SQL.

---

#### **EtudiantDAO.java**
**Objectif** : Accès aux données des étudiants.

**Méthodes** :
```java
public List<Etudiant> getEtudiantsParClasse(int idClasse)
// Récupère tous les étudiants d'une classe
// Utilisé par: MenuEnseignant (remplir liste de présence)

public Etudiant getEtudiantById(int id)
// Récupère un étudiant par son ID
```

---

#### **EnseignantDAO.java**
**Objectif** : Accès aux données des enseignants.

**Méthodes** :
```java
public Enseignant getEnseignantById(int id)
// Récupère un enseignant par son ID
```

---

#### **AbsenceDAO.java**
**Objectif** : Gestion complète des absences.

**Méthodes** :
```java
public boolean enregistrerAbsence(Absence absence)
// INSERT: Enregistre une nouvelle absence
// Retourne true si succès

public List<Absence> getAbsencesParEtudiant(int idEtudiant)
// SELECT: Récupère toutes les absences d'un étudiant
// Jointure avec Etudiant et Matiere pour les noms

public List<Absence> getAbsencesParClasse(int idClasse)
// SELECT: Récupère toutes les absences d'une classe

public List<Absence> getToutesAbsences()
// SELECT: Récupère l'ensemble des absences

public boolean justifierAbsence(int idAbsence)
// UPDATE: Marque une absence comme justifiée
// Retourne true si succès

public boolean supprimerAbsence(int idAbsence)
// DELETE: Supprime une absence
// Retourne true si succès
```

---

#### **StatistiqueDAO.java**
**Objectif** : Calcul des statistiques d'absentéisme.

**Méthodes** :
```java
public Map<String, Double> getTauxAbsentismeParClasse()
// Calcule le taux d'absence (%) par classe
// Utilise des agrégations SQL complexes
// Retour: {NomClasse -> Pourcentage}

public Map<String, Double> getTauxAbsentismeParFiliere()
// Calcule le taux d'absence (%) par filière
// Retour: {NomFiliere -> Pourcentage}
```

**Formule de calcul** :
```
Taux d'absentéisme = (Nombre d'absences / (Nombre d'étudiants × Nombre de matières)) × 100
```

---

### 3. Couche Métier (services/)

#### **AuthentificationService.java**
**Objectif** : Encapsuler la logique d'authentification.

**Méthode** :
```java
public Object connecter(String login, String password, String type)
// Délègue à UtilisateurDAO.authentifier()
// Retourne l'utilisateur authentifié
```

**Rôle** : Intermédiaire entre Main et UtilisateurDAO.

---

#### **RapportService.java**
**Objectif** : Générer des rapports sur les absences.

**Méthodes** :
```java
public void genererRapportAbsences(List<Absence> absences, String titre)
// Affiche un rapport formaté en console
// Tableau avec: ID, Étudiant, Matière, Date, Séance, Justifié
// Format ASCII avec barres de séparation

public boolean genererRapportAbsencesDansFichier(List<Absence> absences, String titre, String fichier)
// Crée un fichier .txt contenant le rapport
// Utilise BufferedWriter et UTF-8
// Retourne true si succès
```

**Format du rapport** :
```
╔══════════════════════════════════════════════════════╗
║          LISTE DES ABSENCES                         ║
╠══════════════════════════════════════════════════════╣
║ ID │ Étudiant │ Matière │ Date │ Séance │ Justifié ║
╚══════════════════════════════════════════════════════╝
```

---

#### **EmailService.java**
**Objectif** : Gérer les alertes par email (simulation).

**Méthodes** :
```java
public void envoyerAlerte(Etudiant etudiant, List<Absence> absences)
// Alerte standard formatée
// Affiche le contenu en console (simulation)
// Détail des absences non justifiées

public void envoyerAlertePersonnalisee(Etudiant etudiant, List<Absence> absences, 
                                        String sujet, String message)
// Alerte avec sujet et message personnalisé
// Ajoute les détails des absences

public void genererRapportAlertes(List<Etudiant> etudiants)
// Rapport sur les étudiants à alerter
// Tableau: Nom | Email | Nombre d'absences
```

**Note** : Les emails sont simulés (affichage en console). Une intégration réelle nécessiterait javax.mail.

---

#### **StatistiqueService.java**
**Objectif** : Analyser et présenter les statistiques.

**Méthodes** :
```java
public void genererStatistiquesParClasse()
// Affiche statistiques avec graphiques ASCII
// Format: Classe | Taux (%) | [████░░░░]

public void genererStatistiquesParFiliere()
// Statistiques groupées par filière
// Calcule et affiche la moyenne générale

public double calculerTauxClasse(String nomClasse)
// Retourne le taux d'une classe spécifique

public double calculerTauxFiliere(String nomFiliere)
// Retourne le taux d'une filière spécifique

public void genererRecommandations()
// Recommandations basées sur les taux:
// - Taux > 30% : CRITIQUE (actions immédiates)
// - Taux > 20% : ÉLEVÉ (surveillance accrue)
// - Taux > 10% : MODÉRÉ (vigilance recommandée)
// - Taux ≤ 10% : OK (situation normale)
```

**Graphiques ASCII** :
```
Classe: L1-Info        Taux: 25.50%  [████████████  ]
Classe: L2-Info        Taux: 15.20%  [███████░░░░░░]
Classe: L3-Math        Taux: 35.80%  [██████████████████░]
```

---

### 4. Utilitaires (utils/)

#### **TextUtil.java**
**Objectif** : Utilitaires de manipulation de texte.

**Méthode** :
```java
public static String repeat(String s, int n)
// Répète une chaîne n fois
// Utilisé pour les traits de séparation
// Exemple: TextUtil.repeat("=", 50) → "=================================================="
```

---

## Flux de Fonctionnement

### 1. Flux de Connexion
```
┌─ START ─────────────────────────────────────────┐
│                                                  │
│  1. Affiche menu de connexion                   │
│  2. Demande le type d'utilisateur               │
│  3. Saisie du login et password                 │
│  4. AuthentificationService.connecter()         │
│      ├─ UtilisateurDAO.authentifier()           │
│      │  ├─ SELECT * FROM [table]                │
│      │  └─ Retourne l'objet ou null             │
│  5. Si authentification réussie:                │
│      ├─ MenuEtudiant / MenuEnseignant           │
│      └─ ou MenuResponsable                      │
│  6. Sinon: Affiche "Identifiants incorrects"    │
│                                                  │
└────────────────────────────────────────────────┘
```

---

### 2. Flux d'Enregistrement d'Absence
```
┌─ Enseignant ─────────────────────────────┐
│                                           │
│  1. Sélectionne classe et matière        │
│  2. MenuEnseignant.remplirListePresence()│
│  3. EtudiantDAO.getEtudiantsParClasse()  │
│      └─ Récupère la liste                │
│  4. Pour chaque étudiant:                │
│      ├─ Demande présent (O/N)            │
│      ├─ Si absent:                       │
│      │  ├─ Crée Absence                  │
│      │  └─ AbsenceDAO.enregistrerAbsence()│
│      │     └─ INSERT dans la BD          │
│      └─ Incrémente compteur               │
│  5. Affiche résumé                       │
│                                           │
└───────────────────────────────────────────┘
```

---

### 3. Flux de Consultation des Statistiques
```
┌─ Responsable ────────────────────────────┐
│                                           │
│  1. Sélectionne option statistiques      │
│  2. StatistiqueService                   │
│      ├─ genererStatistiquesParClasse()   │
│      │  ├─ StatistiqueDAO.getTaux...()   │
│      │  └─ Affiche graphiques            │
│      │                                    │
│      ├─ ou genererStatistiquesParFiliere()│
│      │  ├─ StatistiqueDAO.getTaux...()   │
│      │  └─ Affiche avec moyennes         │
│      │                                    │
│      └─ ou rapport complet               │
│  3. Affiche recommandations              │
│                                           │
└───────────────────────────────────────────┘
```

---

## Guide d'Utilisation

### Installation et Configuration

#### Prérequis
- Java JDK 8 ou supérieur
- MySQL 5.7 ou supérieur
- Pilote JDBC MySQL

#### 1. Créer la Base de Données
```sql
-- Exécuter script.sql
-- Cree les tables: Utilisateur, Etudiant, Enseignant, 
--                   Responsable, Absence, Classe, Matiere
```

#### 2. Configurer la Connexion
Modifier `DatabaseConnection.java` :
```java
private static final String URL = "jdbc:mysql://localhost:3306/gestion_absences";
private static final String USER = "root";
private static final String PASSWORD = "votre_password";
```

#### 3. Compiler et Exécuter
```bash
javac -d bin src/**/*.java
java -cp bin:lib/* application.Main
```

---

### Scénarios d'Utilisation

#### Scénario 1: Enseignant enregistre les absences
```
1. Connexion → Choisir "Enseignant"
2. Login: enseignant1 / Password: pass123
3. Menu → Option 1 (Remplir liste de présence)
4. Saisir ID classe et ID matière
5. Pour chaque étudiant: répondre O (présent) ou N (absent)
6. Confirmation d'enregistrement
```

#### Scénario 2: Étudiant consulte ses absences
```
1. Connexion → Choisir "Étudiant"
2. Login: etudiant1 / Password: pass123
3. Menu → Option 1 (Consulter mes absences)
4. Affichage: liste formatée + statistiques
5. Option 2: Déposer justification pour une absence
```

#### Scénario 3: Responsable génère statistiques
```
1. Connexion → Choisir "Responsable"
2. Login: admin / Password: admin123
3. Menu → Option 4 (Générer statistiques)
4. Choisir: par classe, par filière, ou rapport complet
5. Affichage des graphiques et taux
6. Option 6: Générer recommandations
```

---

## Conclusion

Ce système offre une **solution complète et intégrée** pour la gestion des absences dans un établissement d'enseignement. L'architecture modulaire permet des **évolutions futures** :

- Intégration d'une vraie API email (JavaMail)
- Interface graphique (Swing/JavaFX)
- API REST pour intégration mobile
- Persistance avec JPA/Hibernate
- Authentification sécurisée (hachage bcrypt)

---

**Document généré le : 6 février 2026**  
**Version : 1.0**  
**Auteur : Système de Gestion des Absences**
