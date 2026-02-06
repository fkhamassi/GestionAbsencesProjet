package application;

import beans.Etudiant;
import beans.Absence;
import dao.AbsenceDAO;
import services.RapportService;
import java.util.List;
import java.util.Scanner;

public class MenuEtudiant {
    private static Scanner scanner = new Scanner(System.in);
    private static AbsenceDAO absenceDAO = new AbsenceDAO();
    private static RapportService rapportService = new RapportService();
    
    public static void menu(Etudiant etudiant) {
        while (true) {
            System.out.println("\n=== Menu Étudiant ===");
            System.out.println("1. Consulter mes absences");
            System.out.println("2. Déposer une justification");
            System.out.println("3. Retour au menu principal");
            System.out.print("Votre choix: ");
            
            int choix = scanner.nextInt();
            scanner.nextLine();
            
            switch (choix) {
                case 1:
                    consulterAbsences(etudiant);
                    break;
                case 2:
                    deposerJustification();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Choix invalide !");
            }
        }
    }
    
    private static void consulterAbsences(Etudiant etudiant) {
        List<Absence> absences = absenceDAO.getAbsencesParEtudiant(etudiant.getId());
        
        if (absences.isEmpty()) {
            System.out.println("\nAucune absence enregistrée.");
        } else {
            rapportService.genererRapportAbsences(absences, "Mes Absences");
            
            int absencesNonJustifiees = 0;
            for (Absence absence : absences) {
                if (!absence.isEstJustifie()) {
                    absencesNonJustifiees++;
                }
            }
            
            System.out.println("\nRésumé:");
            System.out.println("- Absences totales: " + absences.size());
            System.out.println("- Absences non justifiées: " + absencesNonJustifiees);
            System.out.println("- Taux d'absentéisme: " + 
                              String.format("%.2f", (absences.size() * 100.0 / 30)) + "%");
        }
    }
    
    private static void deposerJustification() {
        System.out.print("ID de l'absence à justifier: ");
        int idAbsence = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Motif de la justification: ");
        scanner.nextLine();
        
        System.out.print("Joindre un document (O/N): ");
        scanner.nextLine();
        
        if (absenceDAO.justifierAbsence(idAbsence)) {
            System.out.println("Justification enregistrée avec succès !");
        } else {
            System.out.println("Erreur lors de l'enregistrement de la justification !");
        }
    }
}