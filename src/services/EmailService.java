package services;

import beans.Absence;
import beans.Etudiant;
import dao.AbsenceDAO;
import java.util.List;
import utils.TextUtil;

public class EmailService {
    
    public void envoyerAlerte(Etudiant etudiant, List<Absence> absences) {
        // SIMULATION D'EMAIL - VERSION SIMPLIFIÉE
        // Pas besoin de javax.mail pour un projet scolaire
        
        System.out.println("\n" + TextUtil.repeat("=", 70));
        System.out.println("          [EMAIL] ENVOI D'EMAIL D'ALERTE");
        System.out.println(TextUtil.repeat("=", 70));
        
        // En-tête de l'email
        System.out.println("De: gestion.absences@univ.fr");
        System.out.println("À: " + etudiant.getEmail());
        System.out.println("Sujet: Alerte Absences - " + etudiant.getPrenom() + " " + etudiant.getNom());
        System.out.println(TextUtil.repeat("-", 70));
        
        // Corps de l'email
        System.out.println("Cher(e) " + etudiant.getPrenom() + " " + etudiant.getNom() + ",");
        System.out.println();
        
        int totalAbsences = absences.size();
        int nonJustifiees = 0;
        
        // Compter les absences non justifiées
        for (Absence absence : absences) {
            if (!absence.isEstJustifie()) {
                nonJustifiees++;
            }
        }
        
        System.out.println("Vous avez " + totalAbsences + " absence(s) enregistrée(s), " + 
                          "dont " + nonJustifiees + " non justifiée(s).");
        
        if (nonJustifiees > 0) {
            System.out.println("\nDétail des absences non justifiées:");
            System.out.println(TextUtil.repeat("-", 40));
            
            int count = 1;
            for (Absence absence : absences) {
                if (!absence.isEstJustifie()) {
                    System.out.println(count + ". " + absence.getNomMatiere() + 
                                     " - Séance " + absence.getNumSeance() + 
                                     " (" + absence.getDateAbsence() + ")");
                    count++;
                }
            }
        }
        
        System.out.println("\nVeuillez fournir une justification pour vos absences:");
        System.out.println("1. Connectez-vous à votre compte étudiant");
        System.out.println("2. Allez dans 'Mes absences'");
        System.out.println("3. Cliquez sur 'Déposer une justification'");
        System.out.println();
        
        System.out.println("Pour toute question, contactez le service administratif.");
        System.out.println();
        System.out.println("Cordialement,");
        System.out.println("Le service de gestion des absences");
        System.out.println("Université Tunis El Manar");
        System.out.println(TextUtil.repeat("=", 70));
    }
    
    // Méthode pour envoyer une alerte personnalisée
    public void envoyerAlertePersonnalisee(Etudiant etudiant, List<Absence> absences, String sujet, String message) {
        System.out.println("\n" + TextUtil.repeat("=", 70));
        System.out.println("          [EMAIL] ENVOI D'EMAIL PERSONNALISÉ");
        System.out.println(TextUtil.repeat("=", 70));
        
        // En-tête de l'email
        System.out.println("De: gestion.absences@univ.fr");
        System.out.println("À: " + etudiant.getEmail());
        System.out.println("Sujet: " + sujet);
        System.out.println(TextUtil.repeat("-", 70));
        
        // Corps personnalisé
        System.out.println("Cher(e) " + etudiant.getPrenom() + " " + etudiant.getNom() + ",");
        System.out.println();
        System.out.println(message);
        
        // Ajouter les détails des absences
        int totalAbsences = absences.size();
        int nonJustifiees = 0;
        
        for (Absence absence : absences) {
            if (!absence.isEstJustifie()) {
                nonJustifiees++;
            }
        }
        
        System.out.println("\nDétail de vos absences:");
        System.out.println("• Total: " + totalAbsences);
        System.out.println("• Non justifiées: " + nonJustifiees);
        
        if (nonJustifiees > 0) {
            System.out.println("\nAbsences non justifiées:");
            System.out.println(TextUtil.repeat("-", 40));
            
            int count = 1;
            for (Absence absence : absences) {
                if (!absence.isEstJustifie()) {
                    System.out.println(count + ". " + absence.getNomMatiere() + 
                                     " - Séance " + absence.getNumSeance() + 
                                     " (" + absence.getDateAbsence() + ")");
                    count++;
                }
            }
        }
        
        System.out.println("\nCordialement,");
        System.out.println("Le service de gestion des absences");
        System.out.println("Université Tunis El Manar");
        System.out.println(TextUtil.repeat("=", 70));
    }
    
    // Méthode supplémentaire pour générer un rapport d'alertes
    public void genererRapportAlertes(List<Etudiant> etudiants) {
        System.out.println("\n" + TextUtil.repeat("=", 70));
        System.out.println("   RAPPORT D'ALERTES PAR EMAIL");
        System.out.println(TextUtil.repeat("=", 70));
        
        int totalEtudiants = etudiants.size();
        int etudiantsAlerte = 0;
        AbsenceDAO absenceDAO = new AbsenceDAO();
        
        System.out.printf("%-25s %-30s %-10s\n", 
                         "Étudiant", "Email", "Absences");
        System.out.println(TextUtil.repeat("-", 70));
        
        for (Etudiant etudiant : etudiants) {
            List<Absence> absences = absenceDAO.getAbsencesParEtudiant(etudiant.getId());
            int nbAbsences = absences.size();
            
            System.out.printf("%-25s %-30s %-10d\n", 
                            etudiant.getPrenom() + " " + etudiant.getNom(),
                            etudiant.getEmail(),
                            nbAbsences);
            
            if (nbAbsences > 0) {
                etudiantsAlerte++;
            } else {
                System.out.println("   Aucune absence");
            }
        }
        
        System.out.println(TextUtil.repeat("-", 70));
        System.out.println("Résumé:");
        System.out.println("• Étudiants notifiés: " + etudiantsAlerte + "/" + totalEtudiants);
        System.out.println("• Statut: Alertes envoyées");
        System.out.println(TextUtil.repeat("=", 70));
    }
}