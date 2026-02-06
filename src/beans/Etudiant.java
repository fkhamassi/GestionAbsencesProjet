package beans;

public class Etudiant extends Utilisateur {
    private int idClasse;
    private String email;

    public Etudiant() {}

    public Etudiant(String nom, String prenom, String login, String password, int idClasse) {
        super(nom, prenom, login, password);
        this.idClasse = idClasse;
        this.email = login + "@univ.fr";
    }

    public int getIdClasse() { return idClasse; }
    public void setIdClasse(int idClasse) { this.idClasse = idClasse; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}