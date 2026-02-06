package dao;

import beans.Etudiant;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtudiantDAO {
    
    public List<Etudiant> getEtudiantsParClasse(int idClasse) {
        List<Etudiant> etudiants = new ArrayList<>();
        String query = "SELECT * FROM Etudiant WHERE id_classe = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, idClasse);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Etudiant etudiant = new Etudiant();
                etudiant.setId(rs.getInt("id_etudiant"));
                etudiant.setNom(rs.getString("nom"));
                etudiant.setPrenom(rs.getString("prenom"));
                etudiant.setLogin(rs.getString("login"));
                etudiant.setPassword(rs.getString("pwd"));
                etudiant.setIdClasse(rs.getInt("id_classe"));
                etudiant.setEmail(rs.getString("login") + "@univ.fr");
                etudiants.add(etudiant);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des étudiants: " + e.getMessage());
        }
        return etudiants;
    }
    
    public Etudiant getEtudiantById(int id) {
        String query = "SELECT * FROM Etudiant WHERE id_etudiant = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Etudiant etudiant = new Etudiant();
                etudiant.setId(rs.getInt("id_etudiant"));
                etudiant.setNom(rs.getString("nom"));
                etudiant.setPrenom(rs.getString("prenom"));
                etudiant.setLogin(rs.getString("login"));
                etudiant.setPassword(rs.getString("pwd"));
                etudiant.setIdClasse(rs.getInt("id_classe"));
                etudiant.setEmail(rs.getString("login") + "@univ.fr");
                return etudiant;
            }
        } catch (SQLException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
        return null;
    }
}