package beans;

public class Matiere {
    private int idMatiere;
    private String libelle;

    public Matiere() {}

    public Matiere(String libelle) {
        this.libelle = libelle;
    }

    // Getters et Setters
    public int getIdMatiere() { return idMatiere; }
    public void setIdMatiere(int idMatiere) { this.idMatiere = idMatiere; }
    
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
}