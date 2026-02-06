package services;

import beans.Absence;
import java.util.List;
import utils.TextUtil;

public class RapportService {
    
    public void genererRapportAbsences(List<Absence> absences, String titre) {
        System.out.println("\n=== " + titre + " ===");
        System.out.println(TextUtil.repeat("=", 80));
        System.out.printf("%-5s %-20s %-20s %-15s %-10s %-10s\n", 
                         "ID", "Étudiant", "Matière", "Date", "Séance", "Justifié");
        System.out.println(TextUtil.repeat("=", 80));
        
        for (Absence absence : absences) {
            System.out.printf("%-5d %-20s %-20s %-15s %-10d %-10s\n",
                            absence.getIdAbsence(),
                            absence.getNomEtudiant(),
                            absence.getNomMatiere(),
                            absence.getDateAbsence().toString(),
                            absence.getNumSeance(),
                            absence.isEstJustifie() ? "Oui" : "Non");
        }
        System.out.println(TextUtil.repeat("=", 80));
        System.out.println("Total des absences: " + absences.size());
    }

    public boolean genererRapportAbsencesDansFichier(List<Absence> absences, String titre, String fichier) {
        java.io.File out = new java.io.File(fichier);
        java.io.File parent = out.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        try (java.io.OutputStream os = new java.io.FileOutputStream(out);
             java.io.OutputStreamWriter osw = new java.io.OutputStreamWriter(os, java.nio.charset.StandardCharsets.UTF_8);
             java.io.BufferedWriter bw = new java.io.BufferedWriter(osw)) {
            bw.write("=== " + titre + " ===\n");
            bw.write(TextUtil.repeat("=", 80) + "\n");
            bw.write(String.format("%-5s %-20s %-20s %-15s %-10s %-10s\n",
                                   "ID", "Étudiant", "Matière", "Date", "Séance", "Justifié"));
            bw.write(TextUtil.repeat("=", 80) + "\n");
            for (Absence absence : absences) {
                bw.write(String.format("%-5d %-20s %-20s %-15s %-10d %-10s\n",
                                       absence.getIdAbsence(),
                                       absence.getNomEtudiant(),
                                       absence.getNomMatiere(),
                                       absence.getDateAbsence().toString(),
                                       absence.getNumSeance(),
                                       absence.isEstJustifie() ? "Oui" : "Non"));
            }
            bw.write(TextUtil.repeat("=", 80) + "\n");
            bw.write("Total des absences: " + absences.size() + "\n");
            return true;
        } catch (Exception ex) {
            System.out.println("Erreur lors de l'écriture du fichier: " + ex.getMessage());
            return false;
        }
    }
}