package beans;

public class Classe {
    private int idClasse;
    private String libelle;
    private String niveau;
    private String filiere;

    public Classe() {}

    public Classe(String libelle, String niveau, String filiere) {
        this.libelle = libelle;
        this.niveau = niveau;
        this.filiere = filiere;
    }

    // Getters et Setters
    public int getIdClasse() { return idClasse; }
    public void setIdClasse(int idClasse) { this.idClasse = idClasse; }
    
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    
    public String getNiveau() { return niveau; }
    public void setNiveau(String niveau) { this.niveau = niveau; }
    
    public String getFiliere() { return filiere; }
    public void setFiliere(String filiere) { this.filiere = filiere; }
}