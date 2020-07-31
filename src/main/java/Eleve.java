public class Eleve extends Utilisateur{

    private String formation;
    private int idClasse;
    private int idUtilisateur;

    public Eleve(String nom, String prenom, String dateNaissance, String telephone, String adresse, String ville, String email, String mdp,String formation,int idClasse) {
        super(nom,prenom,dateNaissance,telephone,adresse,ville,email,mdp,false);
        this.formation = formation;
        this.idClasse = idClasse;
    }

    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public int getIdClasse() {
        return idClasse;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }
}
