public class Eleve {

    private String formation;
    private int idClasse;
    private int idUtilisateur;

    public Eleve(String formation) {
        this.formation = formation;
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
