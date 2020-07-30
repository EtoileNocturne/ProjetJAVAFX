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


    public void seDeconnecter(){
        try {
            maConnexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
