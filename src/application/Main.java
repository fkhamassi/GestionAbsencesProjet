package application;

import services.AuthentificationService;
import beans.*;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static AuthentificationService authService = new AuthentificationService();
    
    public static void main(String[] args) {
        System.out.println("=== Système de Gestion des Absences ===");
        
        while (true) {
            System.out.println("\n--- Connexion ---");
            System.out.println("1. Étudiant");
            System.out.println("2. Enseignant");
            System.out.println("3. Responsable administratif");
            System.out.println("4. Quitter");
            System.out.print("Votre choix: ");
            
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la nouvelle ligne
            
            switch (choix) {
                case 1:
                    connexionEtudiant();
                    break;
                case 2:
                    connexionEnseignant();
                    break;
                case 3:
                    connexionResponsable();
                    break;
                case 4:
                    System.out.println("Au revoir !");
                    System.exit(0);
                default:
                    System.out.println("Choix invalide !");
            }
        }
    }
    
    private static void connexionEtudiant() {
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Mot de passe: ");
        String password = scanner.nextLine();
        
        Object utilisateur = authService.connecter(login, password, "etudiant");
        
        if (utilisateur instanceof Etudiant) {
            Etudiant etudiant = (Etudiant) utilisateur;
            System.out.println("Bienvenue " + etudiant.getPrenom() + " " + etudiant.getNom());
            MenuEtudiant.menu(etudiant);
        } else {
            System.out.println("Identifiants incorrects !");
        }
    }
    
    private static void connexionEnseignant() {
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Mot de passe: ");
        String password = scanner.nextLine();
        
        Object utilisateur = authService.connecter(login, password, "enseignant");
        
        if (utilisateur instanceof Enseignant) {
            Enseignant enseignant = (Enseignant) utilisateur;
            System.out.println("Bienvenue " + enseignant.getPrenom() + " " + enseignant.getNom());
            MenuEnseignant.menu(enseignant);
        } else {
            System.out.println("Identifiants incorrects !");
        }
    }
    
    private static void connexionResponsable() {
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Mot de passe: ");
        String password = scanner.nextLine();
        
        Object utilisateur = authService.connecter(login, password, "responsable");
        
        if (utilisateur instanceof Responsable) {
            Responsable responsable = (Responsable) utilisateur;
            System.out.println("Bienvenue " + responsable.getPrenom() + " " + responsable.getNom());
            MenuResponsable.menu(responsable);
        } else {
            System.out.println("Identifiants incorrects !");
        }
    }
}