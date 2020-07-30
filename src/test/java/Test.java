

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class Test {

    public static void main(String[] args) throws SQLException, IOException, ParseException {



        Utilisateur uti = new Utilisateur("yildiz","tarik", "1997-11-18","0778213572","11 imp louis cretois","le mans","tarik_yildiz45@hotmail.fr","testestest",true);

        ConnexionBDD conn = new ConnexionBDD("C:\\Users\\tarik\\Documents\\CESI\\CesiProjet\\src\\SQL\\db.properties");

        conn.seConnecter();

        conn.ajouterUtilisateur(uti);
        System.out.println("OK");
        Utilisateur u = conn.rechercherUtilisateur("tarik_yildiz@hotmail.fr");

        System.out.println(u.toString());

        System.out.println("OK");
    }

}
