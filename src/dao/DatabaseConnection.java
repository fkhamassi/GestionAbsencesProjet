package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_absences";
    // Align with database/script.sql created user
    private static final String USER = "app_gestion";
    private static final String PASSWORD = "app_password123";
    
    private static Connection connection;

    private DatabaseConnection() {}

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("ERREUR CRITIQUE: Impossible de se connecter à la base de données MySQL !");
            System.err.println("Détails: " + e.getMessage());
            System.err.println("Vérifiez que MySQL est démarré et accessible sur localhost:3306");
            System.exit(1);
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                System.out.println("Erreur lors de la fermeture de la connexion: " + e.getMessage());
            }
        }
    }

}