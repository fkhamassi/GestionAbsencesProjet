package dao;

import beans.Etudiant;
import beans.Enseignant;
import beans.Responsable;
import java.sql.*;

public class UtilisateurDAO {
    
    public Object authentifier(String login, String password, String type) {
        String table = "";
        switch(type) {
            case "etudiant": table = "Etudiant"; break;
            case "enseignant": table = "Enseignant"; break;
            case "responsable": table = "Responsable"; break;
        }
        
        String query = "SELECT * FROM " + table + " WHERE login = ? AND pwd = ?";
        
           try (Connection conn = DatabaseConnection.getConnection();
               PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, login);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                switch(type) {
                    case "etudiant":
                        Etudiant etudiant = new Etudiant();
                        etudiant.setId(rs.getInt("id_etudiant"));
                        etudiant.setNom(rs.getString("nom"));
                        etudiant.setPrenom(rs.getString("prenom"));
                        etudiant.setLogin(rs.getString("login"));
                        etudiant.setPassword(rs.getString("pwd"));
                        etudiant.setIdClasse(rs.getInt("id_classe"));
                        return etudiant;
                        
                    case "enseignant":
                        Enseignant enseignant = new Enseignant();
                        enseignant.setId(rs.getInt("id_enseignant"));
                        enseignant.setNom(rs.getString("nom"));
                        enseignant.setPrenom(rs.getString("prenom"));
                        enseignant.setLogin(rs.getString("login"));
                        enseignant.setPassword(rs.getString("pwd"));
                        return enseignant;
                        
                    case "responsable":
                        Responsable responsable = new Responsable();
                        responsable.setId(rs.getInt("id_responsable"));
                        responsable.setNom(rs.getString("nom"));
                        responsable.setPrenom(rs.getString("prenom"));
                        responsable.setLogin(rs.getString("login"));
                        responsable.setPassword(rs.getString("pwd"));
                        return responsable;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'authentification: " + e.getMessage());
        }
        return null;
    }
}