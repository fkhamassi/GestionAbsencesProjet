package application;

import beans.Absence;
import beans.Etudiant;
import beans.Responsable;
import dao.AbsenceDAO;
import dao.EtudiantDAO;
import dao.StatistiqueDAO;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import services.EmailService;
import services.RapportService;
import services.StatistiqueService;
import utils.TextUtil;

public class MenuResponsable {
    private static Scanner scanner = new Scanner(System.in);
    private static AbsenceDAO absenceDAO = new AbsenceDAO();
    private static EtudiantDAO etudiantDAO = new EtudiantDAO();
    private static StatistiqueDAO statistiqueDAO = new StatistiqueDAO();
    private static EmailService emailService = new EmailService();
    private static RapportService rapportService = new RapportService();
    private static StatistiqueService statistiqueService = new StatistiqueService();
    
    public static void menu(Responsable responsable) {
        while (true) {
            System.out.println("\n=== Menu Responsable ===");
            System.out.println("1. Consulter toutes les absences");
            System.out.println("2. Annuler une absence");
            System.out.println("3. Imprimer liste d'absences");
            System.out.println("4. Générer statistiques");
            System.out.println("5. Envoyer alertes par email");
            System.out.println("6. Générer recommandations");
            System.out.println("7. Retour au menu principal");
            System.out.print("Votre choix: ");
            
            int choix = scanner.nextInt();
            scanner.nextLine();
            
            switch (choix) {
                case 1:
                    consulterAbsences();
                    break;
                case 2:
                    annulerAbsence();
                    break;
                case 3:
                    imprimerListe();
                    break;
                case 4:
                    genererStatistiques();
                    break;
                case 5:
                    envoyerAlertes();
                    break;
                case 6:
                    genererRecommandations();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Choix invalide !");
            }
        }
    }
    
    private static void consulterAbsences() {
        System.out.print("ID de la classe (0 pour toutes): ");
        int idClasse = scanner.nextInt();
        scanner.nextLine();
        
        List<Absence> absences;
        if (idClasse == 0) {
            // Afficher les absences de toutes les classes
            System.out.println("\n=== Absences de toutes les classes ===");
            absences = absenceDAO.getToutesAbsences();
        } else {
            absences = absenceDAO.getAbsencesParClasse(idClasse);
        }
        
        if (absences.isEmpty()) {
            System.out.println("Aucune absence trouvée.");
        } else {
            rapportService.genererRapportAbsences(absences, "Liste des Absences");
        }
    }
    
    private static void annulerAbsence() {
        System.out.print("ID de l'absence à annuler: ");
        int idAbsence = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Motif de l'annulation: ");
        scanner.nextLine(); // Motif saisi mais non utilisé dans cette version
        
        if (absenceDAO.supprimerAbsence(idAbsence)) {
            System.out.println("[OK] Absence annulée avec succès !");
        } else {
            System.out.println("[ERREUR] Erreur lors de l'annulation !");
        }
    }
    
    private static void imprimerListe() {
        System.out.print("ID de la classe: ");
        int idClasse = scanner.nextInt();
        scanner.nextLine();
        
        List<Absence> absences = absenceDAO.getAbsencesParClasse(idClasse);
        
        System.out.println("\n=== Impression de la liste ===");
        String fichier = "liste_absences_classe_" + idClasse + ".txt";
        boolean ok = rapportService.genererRapportAbsencesDansFichier(absences, "Liste à Imprimer", fichier);
        System.out.println("Document généré: " + fichier);
        System.out.println("Nombre d'absences: " + absences.size());
        System.out.println("Statut: " + (ok ? "[OK] PRÊT À IMPRIMER" : "[ERREUR]"));
        
        if (!absences.isEmpty()) {
            rapportService.genererRapportAbsences(absences, "Liste à Imprimer");
        }
    }
    
    private static void genererStatistiques() {
        System.out.println("\n=== GÉNÉRATION DE STATISTIQUES ===");
        System.out.println("1. Statistiques par classe");
        System.out.println("2. Statistiques par filière");
        System.out.println("3. Rapport complet");
        System.out.print("Votre choix: ");
        
        int choix = scanner.nextInt();
        scanner.nextLine();
        
        switch (choix) {
            case 1:
                statistiqueService.genererStatistiquesParClasse();
                break;
            case 2:
                statistiqueService.genererStatistiquesParFiliere();
                break;
            case 3:
                genererRapportCompletStatistiques();
                break;
            default:
                System.out.println("Choix invalide !");
        }
    }
    
    private static void genererRapportCompletStatistiques() {
        System.out.println("\n" + TextUtil.repeat("=", 60));
        System.out.println("         RAPPORT COMPLET DES STATISTIQUES");
        System.out.println(TextUtil.repeat("=", 60));
        
        // Statistiques par classe
        System.out.println("\n1. STATISTIQUES PAR CLASSE");
        System.out.println(TextUtil.repeat("-", 40));
        Map<String, Double> statsClasse = statistiqueDAO.getTauxAbsentismeParClasse();
        
        for (Map.Entry<String, Double> entry : statsClasse.entrySet()) {
            System.out.printf("Classe: %-20s Taux: %6.2f%%\n", 
                            entry.getKey(), entry.getValue());
        }
        
        // Statistiques par filière
        System.out.println("\n2. STATISTIQUES PAR FILIÈRE");
        System.out.println(TextUtil.repeat("-", 40));
        Map<String, Double> statsFiliere = statistiqueDAO.getTauxAbsentismeParFiliere();
        
        for (Map.Entry<String, Double> entry : statsFiliere.entrySet()) {
            System.out.printf("Filière: %-20s Taux: %6.2f%%\n", 
                            entry.getKey(), entry.getValue());
        }
        
        // Résumé
        System.out.println("\n3. RÉSUMÉ");
        System.out.println(TextUtil.repeat("-", 40));
        double tauxMoyen = statsClasse.values().stream()
                                     .mapToDouble(Double::doubleValue)
                                     .average()
                                     .orElse(0.0);
        System.out.printf("Taux d'absentéisme moyen: %.2f%%\n", tauxMoyen);
        System.out.println(TextUtil.repeat("=", 60));
    }
    
    private static void envoyerAlertes() {
    System.out.print("ID de la classe: ");
    int idClasse = scanner.nextInt();
    scanner.nextLine();
    
    List<Etudiant> etudiants = etudiantDAO.getEtudiantsParClasse(idClasse);
    
    if (etudiants.isEmpty()) {
        System.out.println("Aucun étudiant dans cette classe.");
        return;
    }
    
    System.out.println("\n=== ENVOI D'ALERTES ===");
    System.out.println("Classe: " + idClasse);
    System.out.println("Nombre d'étudiants: " + etudiants.size());
    System.out.println(TextUtil.repeat("-", 50));
    
    // Demander si l'utilisateur veut personnaliser le message
    System.out.print("\nVoulez-vous personnaliser le message? (O/N): ");
    String personnaliser = scanner.nextLine();
    
    String sujetPersonnalise = null;
    String messagePersonnalise = null;
    
    if (personnaliser.equalsIgnoreCase("O")) {
        System.out.print("\nSujet de l'email: ");
        sujetPersonnalise = scanner.nextLine();
        
        System.out.println("\nCorps du message (tapez 'FIN' sur une ligne vide pour terminer):");
        StringBuilder message = new StringBuilder();
        String ligne;
        while (!(ligne = scanner.nextLine()).equals("FIN")) {
            message.append(ligne).append("\n");
        }
        messagePersonnalise = message.toString();
    }
    
    // Demander si envoi à tous les étudiants ou un seul
    System.out.print("Envoyer l'alerte à (T)ous ou (U)n étudiant: ");
    String choixEnvoi = scanner.nextLine();
    
    if (choixEnvoi.equalsIgnoreCase("T")) {
        // Envoyer à tous les étudiants
        emailService.genererRapportAlertes(etudiants);
        
        int compteur = 0;
        for (Etudiant etudiant : etudiants) {
            List<Absence> absences = absenceDAO.getAbsencesParEtudiant(etudiant.getId());
            
            if (!absences.isEmpty()) {
                System.out.println("\n--- Étudiant " + (compteur + 1) + "/" + etudiants.size() + " ---");
                if (personnaliser.equalsIgnoreCase("O")) {
                    emailService.envoyerAlertePersonnalisee(etudiant, absences, sujetPersonnalise, messagePersonnalise);
                } else {
                    emailService.envoyerAlerte(etudiant, absences);
                }
                compteur++;
            }
        }
        System.out.println(+ compteur + " emails envoyés avec succès");
    } else if (choixEnvoi.equalsIgnoreCase("U")) {
        // Envoyer à un étudiant spécifique
        System.out.println("\nListe des étudiants:");
        for (int i = 0; i < etudiants.size(); i++) {
            Etudiant e = etudiants.get(i);
            System.out.println((i + 1) + ". " + e.getPrenom() + " " + e.getNom());
        }
        
        System.out.print("Numéro de l'étudiant: ");
        int numEtudiant = scanner.nextInt();
        scanner.nextLine();
        
        if (numEtudiant > 0 && numEtudiant <= etudiants.size()) {
            Etudiant etudiant = etudiants.get(numEtudiant - 1);
            List<Absence> absences = absenceDAO.getAbsencesParEtudiant(etudiant.getId());
            
            System.out.println("\n--- Envoi à " + etudiant.getPrenom() + " " + etudiant.getNom() + " ---");
            if (personnaliser.equalsIgnoreCase("O")) {
                emailService.envoyerAlertePersonnalisee(etudiant, absences, sujetPersonnalise, messagePersonnalise);
            } else {
                emailService.envoyerAlerte(etudiant, absences);
            }
        } else {
            System.out.println("Étudiant introuvable");
        }
    }
}
    
    private static void genererRecommandations() {
        System.out.println("\n=== GÉNÉRATION DE RECOMMANDATIONS ===");
        
        System.out.println("1. Recommandations automatiques");
        System.out.println("2. Écrire mes propres recommandations");
        System.out.print("Votre choix: ");
        
        int choix = scanner.nextInt();
        scanner.nextLine();
        
        if (choix == 1) {
            genererRecommandationsAuto();
        } else if (choix == 2) {
            genererRecommandationsPersonnalisees();
        } else {
            System.out.println("Choix invalide");
        }
    }
    
    private static void genererRecommandationsAuto() {
        Map<String, Double> statsClasse = statistiqueDAO.getTauxAbsentismeParClasse();
        
        if (statsClasse.isEmpty()) {
            System.out.println("Aucune donnée disponible pour générer des recommandations.");
            return;
        }
        
        System.out.println("\nRecommandations par classe:");
        System.out.println(TextUtil.repeat("=", 50));
        
        for (Map.Entry<String, Double> entry : statsClasse.entrySet()) {
            String classe = entry.getKey();
            double taux = entry.getValue();
            
            System.out.printf("\nClasse: %s (Taux: %.2f%%)\n", classe, taux);
            System.out.println("Options de recommandations:");
            System.out.println("1. [CRITIQUE] NIVEAU CRITIQUE - Actions urgentes");
            System.out.println("   • Rencontre urgente avec le délégué");
            System.out.println("   • Courrier aux parents");
            System.out.println("   • Commission disciplinaire");
            System.out.println();
            System.out.println("2. [ÉLEVÉ] NIVEAU ÉLEVÉ - Surveillance accrue");
            System.out.println("   • Points réguliers sur les présences");
            System.out.println("   • Rappel du règlement");
            System.out.println("   • Suivi individuel");
            System.out.println();
            System.out.println("3. [MODÉRÉ] NIVEAU MODÉRÉ - Vigilance recommandée");
            System.out.println("   • Vérifier les justifications");
            System.out.println("   • Dialogue avec les étudiants");
            System.out.println();
            System.out.println("4. [OK] SITUATION NORMALE - Maintien");
            System.out.println("   • Maintenir la vigilance");
            System.out.println();
            System.out.print("Choisir le type de recommandation (1-4): ");
            
            int choix = scanner.nextInt();
            scanner.nextLine();
            
            System.out.println("\nRecommandations sélectionnées:");
            switch (choix) {
                case 1:
                    System.out.println("[CRITIQUE] NIVEAU CRITIQUE");
                    System.out.println("   • Rencontre urgente avec le délégué");
                    System.out.println("   • Courrier aux parents");
                    System.out.println("   • Commission disciplinaire");
                    break;
                case 2:
                    System.out.println("[ÉLEVÉ] NIVEAU ÉLEVÉ");
                    System.out.println("   • Points réguliers sur les présences");
                    System.out.println("   • Rappel du règlement");
                    System.out.println("   • Suivi individuel");
                    break;
                case 3:
                    System.out.println("[MODÉRÉ] NIVEAU MODÉRÉ");
                    System.out.println("   • Vérifier les justifications");
                    System.out.println("   • Dialogue avec les étudiants");
                    break;
                case 4:
                    System.out.println("[OK] SITUATION NORMALE");
                    System.out.println("   * Maintenir la vigilance");
                    break;
                default:
                    System.out.println("Choix invalide, situation normale appliquée");
                    System.out.println("[OK] SITUATION NORMALE");
                    System.out.println("   • Maintenir la vigilance");
            }
        }
        
        System.out.println("\n" + TextUtil.repeat("=", 50));
    }
    
    private static void genererRecommandationsPersonnalisees() {
        Map<String, Double> statsClasse = statistiqueDAO.getTauxAbsentismeParClasse();
        
        if (statsClasse.isEmpty()) {
            System.out.println("Aucune donnée disponible.");
            return;
        }
        
        System.out.println("\nRecommandations personnalisées:");
        System.out.println(TextUtil.repeat("=", 50));
        
        for (Map.Entry<String, Double> entry : statsClasse.entrySet()) {
            String classe = entry.getKey();
            double taux = entry.getValue();
            
            System.out.printf("\nClasse: %s (Taux: %.2f%%)\n", classe, taux);
            System.out.println("Entrez vos recommandations (tapez 'FIN' pour terminer):");
            
            StringBuilder recommandations = new StringBuilder();
            String ligne;
            while (!(ligne = scanner.nextLine()).equals("FIN")) {
                recommandations.append(ligne).append("\n");
            }
            
            System.out.println("Recommandations enregistrées:");
            System.out.println(recommandations.toString());
        }
        
        System.out.println(TextUtil.repeat("=", 50));
    }
}