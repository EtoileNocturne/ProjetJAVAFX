import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class ConnexionBDD {
    private Connection maConnexion;
    private String user;
    private String password;
    private String dbUrl;
    private String cheminFichierProperties;
    private PreparedStatement ps;

    public ConnexionBDD(String cheminFichierProperties) throws IOException, SQLException {
        this.cheminFichierProperties = cheminFichierProperties;
    }

    public void seConnecter() throws SQLException, IOException {
        Properties props = new Properties();
        props.load(new FileInputStream(cheminFichierProperties));
        user = props.getProperty("user");
        password = props.getProperty("password");
        dbUrl = props.getProperty("dburl");

        maConnexion = DriverManager.getConnection(dbUrl, user, password);
    }

    public Connection getMaConnexion() {
        return maConnexion;
    }

    public void ajouterUtilisateur(Utilisateur uti){
        String query = "INSERT INTO utilisateur (nom,prenom,telephone," +
                "adresse,ville,email,mdp,estAdmin) VALUES (?,?,?,?,?,?,?,?);";
        try {
            PreparedStatement ps = maConnexion.prepareStatement(query);

            ps.setString(1,uti.getNom());
            ps.setString(2,uti.getPrenom());
            ps.setString(3,uti.getTelephone());
            ps.setString(4,uti.getAdresse());
            ps.setString(5,uti.getVille());
            ps.setString(6,uti.getEmail());
            ps.setString(7,uti.chiffrerMDP(uti.getMdp()));
            ps.setBoolean(8,uti.isEstAdmin());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet afficherUtilisateurs(){
        String query = "Select id, nom, prenom, email from UTILISATEUR";

        try {
            PreparedStatement ps = maConnexion.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public String rechercherUtilisateur (String email){
        String query = "Select id, mdp from utilisateur where email = ?";

        try {
            PreparedStatement ps = maConnexion.prepareStatement(query);

            ps.setString(1,email);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                return rs.getString("mdp");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void modifierMdp (String email,String NewMdp){
        String query = "UPDATE from utilisateur set mdp = ? WHERE email = ?";

        try {
            PreparedStatement ps = maConnexion.prepareStatement(query);

            ps.setString(1,NewMdp);
            ps.setString(2,email);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void seDeconnecter(){
        try {
            maConnexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //CRUD classe
    
    public void ajouterClasse(Classe classe){
        String query = "INSERT INTO classe (nom,capacite) VALUES (?,?);";
        try {
            PreparedStatement ps = maConnexion.prepareStatement(query);

            ps.setString(1,classe.getNom());
            ps.setInt(2,classe.getCapacite());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void modifierClasse(Classe classe){
        String query = "UPDATE classe SET nom = ?, capacite = ? where id = ? ";
        try {
            PreparedStatement ps = maConnexion.prepareStatement(query);

            ps.setString(1,classe.getNom());
            ps.setInt(2,classe.getCapacite());
            ps.setInt(3,classe.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void supprimerClasse(Classe classe){
        String query = "DELETE classe where id = ? ";
        try {
            PreparedStatement ps = maConnexion.prepareStatement(query);
            
            ps.setInt(1,classe.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
