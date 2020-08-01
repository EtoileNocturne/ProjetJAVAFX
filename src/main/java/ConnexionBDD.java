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

    public ConnexionBDD() throws IOException, SQLException {
        this.cheminFichierProperties = System.getProperty("user.dir")+"\\src\\SQL\\db.properties";
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
        String query = "INSERT INTO utilisateur (nom,prenom,dateNaissance,telephone," +
                "adresse,ville,email,mdp,estAdmin) VALUES (?,?,?,?,?,?,?,?,?);";
        try {
            PreparedStatement ps = maConnexion.prepareStatement(query);

            ps.setString(1,uti.getNom());
            ps.setString(2,uti.getPrenom());
            ps.setString(3,uti.getDateNaissance());
            ps.setString(4,uti.getTelephone());
            ps.setString(5,uti.getAdresse());
            ps.setString(6,uti.getVille());
            ps.setString(7,uti.getEmail());
            ps.setString(8,uti.chiffrerMDP(uti.getMdp()));
            ps.setBoolean(9,uti.isEstAdmin());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifierUtilisateur(Utilisateur uti){
        String query = "UPDATE utilisateur SET nom = ?, prenom = ?, dateNaissance = ?, " +
                "telephone = ?, adresse = ?, ville = ?, mdp = ?, estAdmin = ? WHERE " +
                " email = ?;";
        try {
            PreparedStatement ps = maConnexion.prepareStatement(query);

            ps.setString(1,uti.getNom());
            ps.setString(2,uti.getPrenom());
            ps.setString(3,uti.getDateNaissance());
            ps.setString(4,uti.getTelephone());
            ps.setString(5,uti.getAdresse());
            ps.setString(6,uti.getVille());
            ps.setString(7,uti.chiffrerMDP(uti.getMdp()));
            ps.setBoolean(8,uti.isEstAdmin());
            ps.setString(9,uti.getEmail());

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

    public Utilisateur rechercherUtilisateur (String email){
        String query = "Select * from utilisateur where email = ?";

        try {
            PreparedStatement ps = maConnexion.prepareStatement(query);

            ps.setString(1,email);


            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Utilisateur uti = new Utilisateur(rs.getInt("id"),rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("dateNaissance"),
                        rs.getString("telephone"),
                        rs.getString("Adresse"),
                        rs.getString("ville"),
                        rs.getString("email"),
                        rs.getString("mdp"),
                        rs.getBoolean("estAdmin"));
                return uti;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    public Classe rechercherClasse(int id){
        String query = "Select * from Classe where id = ?";

        try {
            PreparedStatement ps = maConnexion.prepareStatement(query);

            ps.setInt(1,id);


            ResultSet rs = ps.executeQuery();

            while (rs.next()){
               Classe c = new Classe(rs.getString("nom"),rs.getInt("capacite"));

               return c;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public ResultSet afficherClasses(){
        String query = "Select * from Classe";

        try {
            PreparedStatement ps = maConnexion.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

           return rs;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
    
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
    
    public boolean supprimerClasse(Classe classe){
        String query = "DELETE FROM classe where id = ? ";
        try {
            PreparedStatement ps = maConnexion.prepareStatement(query);
            
            ps.setInt(1,classe.getId());

            ps.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ResultSet afficherElevesEtClasses(){
        String query = "Select utilisateur.nom as nomEleve, utilisateur.prenom as prenomEleve" +
                ", eleve.idClasse as idClasse, classe.nom as nomClasse from utilisateur" +
                " inner join eleve on utilisateur.id = eleve.idUti inner join classe on classe.id = eleve.idClasse" +
                " ORDER BY classe.id asc";

        try {
            PreparedStatement ps = maConnexion.prepareStatement(query);

            return ps.executeQuery();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void ajouterEleve(Eleve eleve){
        ajouterUtilisateur(eleve);

        Utilisateur uti = rechercherUtilisateur(eleve.getEmail());

        String query = "INSERT INTO eleve (formation,idClasse,idUti) VALUES (?,?,?);";
        try {
            PreparedStatement ps = maConnexion.prepareStatement(query);

            ps.setString(1,eleve.getFormation());
            ps.setInt(2,eleve.getIdClasse());
            ps.setInt(3,uti.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void modifierEleve(Eleve eleve){
        modifierUtilisateur(eleve);

        Utilisateur uti = rechercherUtilisateur(eleve.getEmail());

        String query = "INSERT INTO eleve (formation,idClasse,idUti) VALUES (?,?,?);";
        try {
            PreparedStatement ps = maConnexion.prepareStatement(query);

            ps.setString(1,eleve.getFormation());
            ps.setInt(2,eleve.getIdClasse());
            ps.setInt(3,uti.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean supprimerEleve(Eleve e){
        String query = "DELETE FROM utilisateur WHERE email = ?";

        try {

            PreparedStatement ps = maConnexion.prepareStatement(query);
            ps.setString(1,e.getEmail());

            ps.execute();

            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public ResultSet afficherEleves(){
        String query = "Select * from eleve inner join Utilisateur on eleve.idUti = Utilisateur.id";

        try {
            PreparedStatement ps = maConnexion.prepareStatement(query);

            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public Eleve rechercherEleve (String email){
        String query = "Select * from Eleve inner join Utilisateur on Eleve.idUti = Utilisateur.id where email = ?";

        try {
            PreparedStatement ps = maConnexion.prepareStatement(query);

            ps.setString(1,email);


            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Eleve e = new Eleve(rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("dateNaissance"),
                        rs.getString("telephone"),
                        rs.getString("Adresse"),
                        rs.getString("ville"),
                        rs.getString("email"),
                        rs.getString("mdp"),
                        rs.getString("formation"),
                        rs.getInt("idClasse")
                        );
                return e;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
