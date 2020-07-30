import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class Utilisateur {

    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;
    private String ville;
    private String email;
    private String mdp;
    private boolean estAdmin;

    public Utilisateur(String nom, String prenom, String telephone, String adresse, String ville, String email, String mdp, boolean estAdmin) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.ville = ville;
        this.email = email;
        this.mdp = mdp;
        this.estAdmin = estAdmin;
    }

    public String chiffrerMDP(String mdp){
        return Hashing.sha256().hashString(mdp, StandardCharsets.UTF_8).toString();
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getVille() {
        return ville;
    }

    public String getEmail() {
        return email;
    }

    public String getMdp() {
        return mdp;
    }

    public boolean isEstAdmin() {
        return estAdmin;
    }
}
