import javafx.event.EventHandler;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
 
public class AccueilAffichage extends Application {
 
   @Override
   public void start(Stage primaryStage) throws Exception {

       final BorderPane root = new BorderPane();
       final VBox vboxMenu = new VBox();

       final VBox vBoxListeEleves = new VBox();

       vBoxListeEleves.setSpacing(20);
       vBoxListeEleves.setPadding(new Insets(100, 100, 100, 100));

       vboxMenu.setSpacing(20);
       vboxMenu.setPadding(new Insets(100, 100, 100, 100));

//       root.setPadding(new Insets(20));
//       root.setCenter().setHgap(25);
//       root.setVgap(15);
 
       Label labelTitle = new Label("Connexion");

       Label labelUserName = new Label("Email");
       final TextField fieldUserName = new TextField();
 
       Label labelPassword = new Label("Mot de passe");
 
       final PasswordField fieldPassword = new PasswordField();
 
       Button loginButton = new Button("Connexion");
       EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {

           @Override
           public void handle(ActionEvent event) {
              try {
				ConnexionBDD connexion = new ConnexionBDD();
				connexion.seConnecter();

//				Classe c = new Classe("cp",50);
//				connexion.ajouterClasse(c);
//				Eleve e = new Eleve("yildiz","tarik","1997-11-18","0778213572","", "","tarika","tarik","ril",1);
//				connexion.ajouterEleve(e);
//				Eleve f = new Eleve("houbert","antoine","1997-11-18","0778213572","", "","antoine","tarik","ril",1);
//                connexion.ajouterEleve(f);
//                System.exit(0);
				String userName = String.valueOf(fieldUserName.getText());
				Utilisateur uti = connexion.rechercherUtilisateur(userName);

				if(uti != null) {
				Alert a  = new Alert(AlertType.INFORMATION);
				if(uti.getMdp().replaceAll(" ","").compareTo(uti.chiffrerMDP(fieldPassword.getText())) == 0) {
					//Si l'utilisateur arrive à se connecter
					// Mettre un lien vers le nouveau layout


					MenuBar menuBar = new MenuBar();
					accueil(menuBar);
					GridPane.setValignment(menuBar, VPos.TOP);
				    root.setTop(menuBar);

                    TableView tableEleves = new TableView();
                    TableColumn<Object, Object> nomCol = new TableColumn("Nom");
                    nomCol.setCellValueFactory(new PropertyValueFactory<>("nomEleve"));
                    TableColumn<Object, Object> prenomCol = new TableColumn("Prenom");
                    prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenomEleve"));
                    TableColumn<Object, Object> idClasseCol = new TableColumn("NumClasse");
                    idClasseCol.setCellValueFactory(new PropertyValueFactory<>("idClasse"));
                    TableColumn<Object, Object> nomClasseCol = new TableColumn("Nom Classe");
                    nomClasseCol.setCellValueFactory(new PropertyValueFactory<>("nomClasse"));

                    tableEleves.getColumns().addAll(nomCol,prenomCol,idClasseCol,nomClasseCol);
                    ResultSet rs = connexion.afficherElevesEtClasses();
                    while(rs.next()){

                        tableEleves.getItems().add(new TableElevesClasse(rs.getString("nomEleve"),
                                rs.getString("prenomEleve"),rs.getInt("idClasse"),rs.getString("nomClasse") ));

                    }
                    vBoxListeEleves.getChildren().add(tableEleves);
                    root.setCenter(vBoxListeEleves);

				}else {
					a.setContentText("L'utilisateur n'est pas connecté. Vérifiez le mot de passe ou l'email de l'utilisateur.");
					a.showAndWait();
				}
            }
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           }
       };
       
       loginButton.setOnAction(event);


       vboxMenu.getChildren().add(labelUserName);
       vboxMenu.getChildren().add(fieldUserName);
       vboxMenu.getChildren().add(labelPassword);
       vboxMenu.getChildren().add(fieldPassword);
       vboxMenu.getChildren().add(loginButton);

       root.setCenter(vboxMenu);
       Scene scene = new Scene(root, 600, 600);
       primaryStage.setTitle("Gestionnaire d'école - Connexion");
       primaryStage.setScene(scene);
       primaryStage.show();
   }
 
   public static void main(String[] args) {
       launch(args);
   }

   public static void accueil(MenuBar menuBar)
   {
       
       
       // Create menus
       Menu eleveMenu = new Menu("Elève");
       Menu classeMenu = new Menu("Classe");
       
       // Create MenuItems
       MenuItem nouvelEleve = new MenuItem("Nouvel élève");
       MenuItem updateEleve = new MenuItem("Modifier élève");
       MenuItem supprimerEleve = new MenuItem("Supprimer élève");
       
       MenuItem nouvelleClasse = new MenuItem("Nouvelle classe");
       MenuItem updateClasse = new MenuItem("Modifier classe");
       MenuItem supprimerClasse = new MenuItem("Supprimer classe");
       
       // Add menuItems to the Menus
       eleveMenu.getItems().addAll(nouvelEleve, updateEleve, supprimerEleve);
       classeMenu.getItems().addAll(nouvelleClasse, updateClasse, supprimerClasse);
       
       // Add Menus to the MenuBar
       menuBar.getMenus().addAll(eleveMenu, classeMenu);
       
       BorderPane accueilBorderPane = new BorderPane();
       accueilBorderPane.setTop(menuBar);
   }
 
}