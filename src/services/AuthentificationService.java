package services;

import dao.UtilisateurDAO;

public class AuthentificationService {
    private UtilisateurDAO utilisateurDAO;
    
    public AuthentificationService() {
        this.utilisateurDAO = new UtilisateurDAO();
    }
    
    public Object connecter(String login, String password, String type) {
        return utilisateurDAO.authentifier(login, password, type);
    }
}