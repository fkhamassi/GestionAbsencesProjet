package services;

import dao.StatistiqueDAO;
import java.util.Map;
import utils.TextUtil;

public class StatistiqueService {
    private StatistiqueDAO statistiqueDAO;
    
    public StatistiqueService() {
        this.statistiqueDAO = new StatistiqueDAO();
    }
    
    // Générer les statistiques par classe
    public void genererStatistiquesParClasse() {
        System.out.println("\n=== STATISTIQUES PAR CLASSE ===");
        System.out.println(TextUtil.repeat("=", 50));
        
        Map<String, Double> stats = statistiqueDAO.getTauxAbsentismeParClasse();
        
        if (stats.isEmpty()) {
            System.out.println("Aucune donnée disponible.");
            return;
        }
        
        System.out.printf("%-20s %-15s %-15s\n", "Classe", "Taux (%)", "Graphique");
        System.out.println(TextUtil.repeat("=", 50));
        
        for (Map.Entry<String, Double> entry : stats.entrySet()) {
            String classe = entry.getKey();
            double taux = entry.getValue();
            
            // Créer un graphique ASCII simple
            StringBuilder graph = new StringBuilder("[");
            int barLength = (int) (taux / 2); // Échelle: 50 caractères = 100%
            
            for (int i = 0; i < 50; i++) {
                if (i < barLength) {
                    graph.append("█");
                } else {
                    graph.append(" ");
                }
            }
            graph.append("]");
            
            System.out.printf("%-20s %-15.2f %-15s\n", 
                            classe, taux, graph.toString());
        }
        System.out.println(TextUtil.repeat("=", 50));
    }
    
    // Générer les statistiques par filière
    public void genererStatistiquesParFiliere() {
        System.out.println("\n=== STATISTIQUES PAR FILIÈRE ===");
        System.out.println(TextUtil.repeat("=", 70));
        
        Map<String, Double> stats = statistiqueDAO.getTauxAbsentismeParFiliere();
        
        if (stats.isEmpty()) {
            System.out.println("Aucune donnée disponible.");
            return;
        }
        
        System.out.printf("%-20s %-15s %-15s\n", "Filière", "Taux (%)", "Graphique");
        System.out.println(TextUtil.repeat("=", 70));
        
        double tauxTotal = 0;
        int nbFilieres = stats.size();
        
        for (Map.Entry<String, Double> entry : stats.entrySet()) {
            String filiere = entry.getKey();
            double taux = entry.getValue();
            tauxTotal += taux;
            
            // Créer un graphique ASCII simple
            StringBuilder graph = new StringBuilder("[");
            int barLength = (int) (taux / 2); // Échelle: 50 caractères = 100%
            
            for (int i = 0; i < 50; i++) {
                if (i < barLength) {
                    graph.append("█");
                } else {
                    graph.append(" ");
                }
            }
            graph.append("]");
            
            System.out.printf("%-20s %-15.2f %-15s\n", 
                            filiere, taux, graph.toString());
        }
        
        System.out.println(TextUtil.repeat("=", 70));
        if (nbFilieres > 0) {
            System.out.printf("Moyenne générale: %.2f%%\n", tauxTotal / nbFilieres);
        }
    }
}