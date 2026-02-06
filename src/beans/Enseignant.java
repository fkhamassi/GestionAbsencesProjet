package beans;

public class Enseignant extends Utilisateur {
    
    public Enseignant() {}

    public Enseignant(String nom, String prenom, String login, String password) {
        super(nom, prenom, login, password);
    }
}