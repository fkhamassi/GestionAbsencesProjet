package application;

import beans.Enseignant;
import beans.Etudiant;
import beans.Absence;
import dao.EtudiantDAO;
import dao.AbsenceDAO;
import services.RapportService;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import utils.TextUtil;

public class MenuEnseignant {
    private static Scanner scanner = new Scanner(System.in);
    private static EtudiantDAO etudiantDAO = new EtudiantDAO();
    private static AbsenceDAO absenceDAO = new AbsenceDAO();
    private static RapportService rapportService = new RapportService();
    
    public static void menu(Enseignant enseignant) {
        while (true) {
            System.out.println("\n=== Menu Enseignant ===");
            System.out.println("1. Remplir liste de présence");
            System.out.println("2. Consulter absences de ma classe");
            System.out.println("3. Retour au menu principal");
            System.out.print("Votre choix: ");
            
            int choix = scanner.nextInt();
            scanner.nextLine();
            
            switch (choix) {
                case 1:
                    remplirListePresence(enseignant);
                    break;
                case 2:
                    consulterAbsencesClasse();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Choix invalide !");
            }
        }
    }
    
    private static void remplirListePresence(Enseignant enseignant) {
        System.out.print("ID de la classe: ");
        int idClasse = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("ID de la matière: ");
        int idMatiere = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Numéro de séance: ");
        int numSeance = scanner.nextInt();
        scanner.nextLine();
        
        Date date = new Date(); // Date actuelle
        
        List<Etudiant> etudiants = etudiantDAO.getEtudiantsParClasse(idClasse);
        
        if (etudiants.isEmpty()) {
            System.out.println("Aucun étudiant trouvé dans cette classe.");
            return;
        }
        
        System.out.println("\n=== Liste des étudiants - Classe ID: " + idClasse + " ===");
        System.out.println("Matière ID: " + idMatiere + " | Séance: " + numSeance);
        System.out.println("Date: " + date);
        System.out.println(TextUtil.repeat("-", 50));
        
        int absencesEnregistrees = 0;
        
        for (Etudiant etudiant : etudiants) {
            System.out.print(etudiant.getPrenom() + " " + etudiant.getNom() + " - Présent (O/N)? ");
            String present = scanner.nextLine();
            
            if (present.equalsIgnoreCase("N")) {
                Absence absence = new Absence(etudiant.getId(), enseignant.getId(), 
                                            idMatiere, numSeance, date);
                if (absenceDAO.enregistrerAbsence(absence)) {
                    System.out.println(" Absence enregistrée");
                    absencesEnregistrees++;
                } else {
                    System.out.println(" Erreur lors de l'enregistrement");
                }
            } else {
                System.out.println(" Présent");
            }
        }
        
        System.out.println("\nListe de présence remplie avec succès !");
        System.out.println("Absences enregistrées: " + absencesEnregistrees + " / " + etudiants.size());
    }
    
    private static void consulterAbsencesClasse() {
        System.out.print("ID de la classe: ");
        int idClasse = scanner.nextInt();
        scanner.nextLine();
        
        List<Absence> absences = absenceDAO.getAbsencesParClasse(idClasse);
        
        if (absences.isEmpty()) {
            System.out.println("Aucune absence trouvée pour cette classe.");
        } else {
            rapportService.genererRapportAbsences(absences, "Absences de la Classe");
        }
    }
}