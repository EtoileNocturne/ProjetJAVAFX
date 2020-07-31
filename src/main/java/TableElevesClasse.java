public class TableElevesClasse {

    private String nomEleve;
    private String prenomEleve;
    private Integer idClasse;
    private String nomClasse;

    public TableElevesClasse(String nomEleve, String prenomEleve, Integer idClasse, String nomClasse) {
        this.nomEleve = nomEleve;
        this.prenomEleve = prenomEleve;
        this.idClasse = idClasse;
        this.nomClasse = nomClasse;
    }

    public String getNomEleve() {
        return nomEleve;
    }

    public String getPrenomEleve() {
        return prenomEleve;
    }

    public Integer getIdClasse() {
        return idClasse;
    }

    public String getNomClasse() {
        return nomClasse;
    }
}
