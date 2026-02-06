package dao;

import beans.Absence;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AbsenceDAO {
    
    public boolean enregistrerAbsence(Absence absence) {
        String query = "INSERT INTO Absence (id_etudiant, id_enseignant, id_matiere, numseance, date_absence) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, absence.getIdEtudiant());
            stmt.setInt(2, absence.getIdEnseignant());
            stmt.setInt(3, absence.getIdMatiere());
            stmt.setInt(4, absence.getNumSeance());
            stmt.setDate(5, new java.sql.Date(absence.getDateAbsence().getTime()));
            
            int rows = stmt.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'enregistrement de l'absence: " + e.getMessage());
            return false;
        }
    }
    
    public List<Absence> getAbsencesParEtudiant(int idEtudiant) {
        List<Absence> absences = new ArrayList<>();
        String query = "SELECT a.*, e.nom as nom_etudiant, m.libelle as nom_matiere " +
                      "FROM Absence a " +
                      "JOIN Etudiant e ON a.id_etudiant = e.id_etudiant " +
                      "JOIN Matiere m ON a.id_matiere = m.id_matiere " +
                      "WHERE a.id_etudiant = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, idEtudiant);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Absence absence = new Absence();
                absence.setIdAbsence(rs.getInt("id_absence"));
                absence.setIdEtudiant(rs.getInt("id_etudiant"));
                absence.setIdEnseignant(rs.getInt("id_enseignant"));
                absence.setIdMatiere(rs.getInt("id_matiere"));
                absence.setNumSeance(rs.getInt("numseance"));
                absence.setDateAbsence(rs.getDate("date_absence"));
                absence.setEstJustifie(rs.getBoolean("est_justifie"));
                absence.setNomEtudiant(rs.getString("nom_etudiant"));
                absence.setNomMatiere(rs.getString("nom_matiere"));
                absences.add(absence);
            }
        } catch (SQLException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
        return absences;
    }
    
    public List<Absence> getAbsencesParClasse(int idClasse) {
        List<Absence> absences = new ArrayList<>();
        String query = "SELECT a.*, e.nom as nom_etudiant, m.libelle as nom_matiere " +
                      "FROM Absence a " +
                      "JOIN Etudiant e ON a.id_etudiant = e.id_etudiant " +
                      "JOIN Matiere m ON a.id_matiere = m.id_matiere " +
                      "WHERE e.id_classe = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, idClasse);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Absence absence = new Absence();
                absence.setIdAbsence(rs.getInt("id_absence"));
                absence.setIdEtudiant(rs.getInt("id_etudiant"));
                absence.setIdEnseignant(rs.getInt("id_enseignant"));
                absence.setIdMatiere(rs.getInt("id_matiere"));
                absence.setNumSeance(rs.getInt("numseance"));
                absence.setDateAbsence(rs.getDate("date_absence"));
                absence.setEstJustifie(rs.getBoolean("est_justifie"));
                absence.setNomEtudiant(rs.getString("nom_etudiant"));
                absence.setNomMatiere(rs.getString("nom_matiere"));
                absences.add(absence);
            }
        } catch (SQLException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
        return absences;
    }

    public List<Absence> getToutesAbsences() {
        List<Absence> absences = new ArrayList<>();
        String query = "SELECT a.*, e.nom as nom_etudiant, m.libelle as nom_matiere " +
                      "FROM Absence a " +
                      "JOIN Etudiant e ON a.id_etudiant = e.id_etudiant " +
                      "JOIN Matiere m ON a.id_matiere = m.id_matiere";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Absence absence = new Absence();
                absence.setIdAbsence(rs.getInt("id_absence"));
                absence.setIdEtudiant(rs.getInt("id_etudiant"));
                absence.setIdEnseignant(rs.getInt("id_enseignant"));
                absence.setIdMatiere(rs.getInt("id_matiere"));
                absence.setNumSeance(rs.getInt("numseance"));
                absence.setDateAbsence(rs.getDate("date_absence"));
                absence.setEstJustifie(rs.getBoolean("est_justifie"));
                absence.setNomEtudiant(rs.getString("nom_etudiant"));
                absence.setNomMatiere(rs.getString("nom_matiere"));
                absences.add(absence);
            }
        } catch (SQLException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
        return absences;
    }
    
    public boolean justifierAbsence(int idAbsence) {
        String query = "UPDATE Absence SET est_justifie = true WHERE id_absence = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, idAbsence);
            int rows = stmt.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            System.out.println("Erreur lors de la justification: " + e.getMessage());
            return false;
        }
    }
    
    public boolean supprimerAbsence(int idAbsence) {
        String query = "DELETE FROM Absence WHERE id_absence = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, idAbsence);
            int rows = stmt.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression: " + e.getMessage());
            return false;
        }
    }
}