package beans;

public abstract class Utilisateur {
    protected int id;
    protected String nom;
    protected String prenom;
    protected String login;
    protected String password;

    public Utilisateur() {}

    public Utilisateur(String nom, String prenom, String login, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.password = password;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}