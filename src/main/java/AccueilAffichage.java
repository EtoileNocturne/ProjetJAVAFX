import javafx.event.EventHandler;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
 
public class AccueilAffichage extends Application {
 
   @Override
   public void start(Stage primaryStage) throws Exception {
       GridPane root = new GridPane();
 
       root.setPadding(new Insets(20));
       root.setHgap(25);
       root.setVgap(15);
 
       Label labelTitle = new Label("Connexion");
 
       root.add(labelTitle, 1, 0, 1, 1);
 
       Label labelUserName = new Label("Email");
       TextField fieldUserName = new TextField();
 
       Label labelPassword = new Label("Mot de passe");
 
       PasswordField fieldPassword = new PasswordField();
 
       Button loginButton = new Button("Connexion");
       loginButton.setOnAction(new EventHandler<ActionEvent>() {

           @Override
           public void handle(ActionEvent event) {
              try {
				ConnexionBDD connexion = new ConnexionBDD();
				connexion.seConnecter();
				Utilisateur uti = connexion.rechercherUtilisateur("antoine.houlbert@gmail.com");
				System.out.println(uti.getNom());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           }
       });
 
       GridPane.setHalignment(labelUserName, HPos.RIGHT);
 
       root.add(labelUserName, 0, 1);
 
        
       GridPane.setHalignment(labelPassword, HPos.RIGHT);
       root.add(labelPassword, 0, 2);
 
       GridPane.setHalignment(fieldUserName, HPos.LEFT);
       root.add(fieldUserName, 1, 1);
 
       GridPane.setHalignment(fieldPassword, HPos.LEFT);
       root.add(fieldPassword, 1, 2);
 
       GridPane.setHalignment(loginButton, HPos.RIGHT);
       root.add(loginButton, 1, 3);
 
       Scene scene = new Scene(root, 600, 600);
       primaryStage.setTitle("Gestionnaire d'école - Connexion");
       primaryStage.setScene(scene);
       primaryStage.show();
   }
 
   public static void main(String[] args) {
       launch(args);
   }
 
}