package dao;

import beans.Enseignant;
import java.sql.*;

public class EnseignantDAO {
    
    // Récupérer un enseignant par ID
    public Enseignant getEnseignantById(int id) {
        String query = "SELECT * FROM Enseignant WHERE id_enseignant = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Enseignant enseignant = new Enseignant();
                enseignant.setId(rs.getInt("id_enseignant"));
                enseignant.setNom(rs.getString("nom"));
                enseignant.setPrenom(rs.getString("prenom"));
                enseignant.setLogin(rs.getString("login"));
                enseignant.setPassword(rs.getString("pwd"));
                return enseignant;
            }
        } catch (SQLException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
        return null;
    }
}