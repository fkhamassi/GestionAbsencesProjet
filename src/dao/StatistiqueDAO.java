package dao;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class StatistiqueDAO {
    
    public Map<String, Double> getTauxAbsentismeParClasse() {
        Map<String, Double> statistiques = new HashMap<>();
        String query = "SELECT c.libelle, " +
                      "COUNT(a.id_absence) * 100.0 / (COUNT(DISTINCT e.id_etudiant) * COUNT(DISTINCT m.id_matiere)) as taux " +
                      "FROM Etudiant e " +
                      "LEFT JOIN Absence a ON e.id_etudiant = a.id_etudiant " +
                      "LEFT JOIN Matiere m ON a.id_matiere = m.id_matiere " +
                      "JOIN Classe c ON e.id_classe = c.id_classe " +
                      "GROUP BY c.id_classe, c.libelle";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                statistiques.put(rs.getString("libelle"), rs.getDouble("taux"));
            }
        } catch (SQLException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
        return statistiques;
    }
    
    public Map<String, Double> getTauxAbsentismeParFiliere() {
        Map<String, Double> statistiques = new HashMap<>();
        String query = "SELECT c.filiere, " +
                      "COUNT(a.id_absence) * 100.0 / (COUNT(DISTINCT e.id_etudiant) * COUNT(DISTINCT m.id_matiere)) as taux " +
                      "FROM Etudiant e " +
                      "LEFT JOIN Absence a ON e.id_etudiant = a.id_etudiant " +
                      "LEFT JOIN Matiere m ON a.id_matiere = m.id_matiere " +
                      "JOIN Classe c ON e.id_classe = c.id_classe " +
                      "GROUP BY c.filiere";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                statistiques.put(rs.getString("filiere"), rs.getDouble("taux"));
            }
        } catch (SQLException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
        return statistiques;
    }
}