package beans;

import java.util.Date;

public class Absence {
    private int idAbsence;
    private int idEtudiant;
    private int idEnseignant;
    private int idMatiere;
    private int numSeance;
    private Date dateAbsence;
    private boolean estJustifie;
    private String nomEtudiant;
    private String nomMatiere;

    public Absence() {}

    public Absence(int idEtudiant, int idEnseignant, int idMatiere, int numSeance, Date dateAbsence) {
        this.idEtudiant = idEtudiant;
        this.idEnseignant = idEnseignant;
        this.idMatiere = idMatiere;
        this.numSeance = numSeance;
        this.dateAbsence = dateAbsence;
        this.estJustifie = false;
    }

    // Getters et Setters
    public int getIdAbsence() { return idAbsence; }
    public void setIdAbsence(int idAbsence) { this.idAbsence = idAbsence; }
    
    public int getIdEtudiant() { return idEtudiant; }
    public void setIdEtudiant(int idEtudiant) { this.idEtudiant = idEtudiant; }
    
    public int getIdEnseignant() { return idEnseignant; }
    public void setIdEnseignant(int idEnseignant) { this.idEnseignant = idEnseignant; }
    
    public int getIdMatiere() { return idMatiere; }
    public void setIdMatiere(int idMatiere) { this.idMatiere = idMatiere; }
    
    public int getNumSeance() { return numSeance; }
    public void setNumSeance(int numSeance) { this.numSeance = numSeance; }
    
    public Date getDateAbsence() { return dateAbsence; }
    public void setDateAbsence(Date dateAbsence) { this.dateAbsence = dateAbsence; }
    
    public boolean isEstJustifie() { return estJustifie; }
    public void setEstJustifie(boolean estJustifie) { this.estJustifie = estJustifie; }
    
    public String getNomEtudiant() { return nomEtudiant; }
    public void setNomEtudiant(String nomEtudiant) { this.nomEtudiant = nomEtudiant; }
    
    public String getNomMatiere() { return nomMatiere; }
    public void setNomMatiere(String nomMatiere) { this.nomMatiere = nomMatiere; }
}