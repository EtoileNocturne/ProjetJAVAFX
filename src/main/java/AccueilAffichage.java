
import javafx.event.EventHandler;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


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

    final static BorderPane root = new BorderPane();
    final VBox vboxMenu = new VBox();


    final VBox c = new VBox();
    final VBox vBoxListeEleves = new VBox();
    final VBox vBoxListeElevesNewEdit = new VBox();

    MenuItem nouvelEleve;

    static String userAModifier = "";
    static int classeAModifier = 0;

    static String nomBoutonModifier = "Modifier";


    @Override
    public void start(Stage primaryStage) throws Exception {


        vBoxListeEleves.setSpacing(20);
        vBoxListeEleves.setPadding(new Insets(100, 100, 100, 100));

        vboxMenu.setSpacing(20);
        vboxMenu.setPadding(new Insets(100, 100, 100, 100));


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

                    String userName = String.valueOf(fieldUserName.getText());
                    Utilisateur uti = connexion.rechercherUtilisateur(userName);

                    if (uti != null) {
                        Alert a = new Alert(AlertType.INFORMATION);
                        if (uti.getMdp().replaceAll(" ", "").compareTo(uti.chiffrerMDP(fieldPassword.getText())) == 0) {
                            //Si l'utilisateur arrive à se connecter
                            // Mettre un lien vers le nouveau layout


                            MenuBar menuBar = new MenuBar();
                            accueil(menuBar);
                            GridPane.setValignment(menuBar, VPos.TOP);
                            root.setTop(menuBar);

                            if(uti.isEstAdmin() == true) {
                                TableView tableEleves = new TableView();
                                TableColumn<Object, Object> nomCol = new TableColumn("Nom");
                                nomCol.setCellValueFactory(new PropertyValueFactory<>("nomEleve"));
                                TableColumn<Object, Object> prenomCol = new TableColumn("Prenom");
                                prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenomEleve"));
                                TableColumn<Object, Object> idClasseCol = new TableColumn("NumClasse");
                                idClasseCol.setCellValueFactory(new PropertyValueFactory<>("idClasse"));
                                TableColumn<Object, Object> nomClasseCol = new TableColumn("Nom Classe");
                                nomClasseCol.setCellValueFactory(new PropertyValueFactory<>("nomClasse"));

                                tableEleves.getColumns().addAll(nomCol, prenomCol, idClasseCol, nomClasseCol);
                                ResultSet rs = connexion.afficherElevesEtClasses();
                                while (rs.next()) {

                                    tableEleves.getItems().add(new TableElevesClasse(rs.getString("nomEleve"),
                                            rs.getString("prenomEleve"), rs.getInt("idClasse"), rs.getString("nomClasse")));

                                }
                                vBoxListeEleves.getChildren().add(tableEleves);
                                root.setCenter(vBoxListeEleves);
                            }else{
                                userAModifier = uti.getEmail();
                                root.setTop(null);
                                nouvelEleve.fire();
                            }
                        } else {
                            a.setContentText("L'utilisateur n'est pas connecté. Vérifiez le mot de passe ou l'email de l'utilisateur.");
                            a.showAndWait();
                        }
                    }

                    connexion.seDeconnecter();

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


        vboxMenu.getChildren().clear();
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


    public void accueil(MenuBar menuBar) {

        nouvelEleve = new MenuItem("Nouvel élève");
        MenuItem updateEleve = new MenuItem("Modifier élève");
        MenuItem supprimerEleve = new MenuItem("Supprimer élève");

        MenuItem nouvelleClasse = new MenuItem("Nouvelle classe");
        MenuItem updateClasse = new MenuItem("Modifier classe");
        MenuItem supprimerClasse = new MenuItem("Supprimer classe");

        final VBox vBoxNewEleve = new VBox();
        final VBox vBoxNewClasse = new VBox();


        vBoxNewEleve.setSpacing(5);
        vBoxNewEleve.setPadding(new Insets(0, 100, 0, 0));

        // Create menus
        final Menu eleveMenu = new Menu("Elève");
        Menu classeMenu = new Menu("Classe");

        EventHandler<ActionEvent> eventNewStudent = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Entré");

                Label labelNom = new Label("Nom :");
                final TextField fieldNom = new TextField();

                Label labelPrenom = new Label("Prenom :");
                final TextField fieldPrenom = new TextField();

                Label labelDate = new Label("DateNaissance :");
                final DatePicker fieldDate = new DatePicker(LocalDate.of(1990, 01, 01));


                Label labelTel = new Label("Telephone :");
                final TextField fieldTel = new TextField();

                Label labelAdresse = new Label("Adresse :");
                final TextField fieldAdresse = new TextField();

                Label labelVille = new Label("Ville :");
                final TextField fieldVille = new TextField();

                Label labelEmail = new Label("Email* :");
                final TextField fieldEmail = new TextField();

                Label labelMDP = new Label("MDP * :");
                final PasswordField fieldMDP = new PasswordField();

                Label labelFormation = new Label("Formation :");
                final TextField fieldFormation = new TextField();

                Label labelAdmin = new Label("Admin * :");
                final CheckBox checkAdmin = new CheckBox();

                System.out.println("aModifier " + userAModifier);
                if (userAModifier != "") {
                    try {
                        ConnexionBDD connexion = new ConnexionBDD();
                        connexion.seConnecter();
                        Eleve e = connexion.rechercherEleve(userAModifier);

                        if (e.getDateNaissance() != null) {
                            String date = e.getDateNaissance().replace("-", "");
                            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
                            fieldDate.setValue(LocalDate.parse(date, format));
                        }
                        fieldPrenom.setText(e.getPrenom());
                        fieldNom.setText(e.getNom());
                        fieldAdresse.setText(e.getAdresse());
                        fieldTel.setText(e.getTelephone());
                        fieldEmail.setText(e.getEmail());
                        fieldEmail.setEditable(false);
                        fieldFormation.setText(e.getFormation());
                        checkAdmin.setDisable(true);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                EventHandler<ActionEvent> eventValidateNewStudent = new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {

                        if (String.valueOf(fieldNom.getText()) == "" &&
                                String.valueOf(fieldPrenom.getText()) == "" &&
                                String.valueOf(fieldEmail.getText()) == "" &&
                                String.valueOf(fieldMDP.getText()) == "") {
                            Alert a = new Alert(AlertType.INFORMATION);
                            a.setContentText("Merci de remplir les champs avec un '*'");
                            a.showAndWait();
                        } else {
                            Eleve e = new Eleve(String.valueOf(fieldNom.getText()),
                                    String.valueOf(fieldPrenom.getText()),
                                    String.valueOf(fieldDate.getValue()),
                                    String.valueOf(fieldTel.getText()),
                                    String.valueOf(fieldAdresse.getText()),
                                    String.valueOf(fieldVille.getText()),
                                    String.valueOf(fieldEmail.getText()),
                                    String.valueOf(fieldMDP.getText()),
                                    String.valueOf(checkAdmin.isSelected()), 1);
                            try {
                                ConnexionBDD connexion = new ConnexionBDD();
                                connexion.seConnecter();

                                if (userAModifier != "") {
                                    connexion.modifierEleve(e);
                                    fieldEmail.setEditable(true);
                                    checkAdmin.setDisable(false);
                                } else {
                                    connexion.ajouterEleve(e);
                                }

                                Alert a = new Alert(AlertType.CONFIRMATION);
                                a.setContentText("Eleve Ajouté !");
                                a.showAndWait();
                                root.setCenter(vBoxListeEleves);
                                connexion.seDeconnecter();
                                userAModifier = "";
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }

                        }

                        userAModifier = "";
                    }
                };

                vBoxNewEleve.getChildren().clear();
                Button validerAjoutEleve = new Button("VALIDER");
                validerAjoutEleve.setOnAction(eventValidateNewStudent);
                vBoxNewEleve.getChildren().add(labelPrenom);
                vBoxNewEleve.getChildren().add(fieldPrenom);
                vBoxNewEleve.getChildren().add(labelNom);
                vBoxNewEleve.getChildren().add(fieldNom);
                vBoxNewEleve.getChildren().add(labelDate);
                vBoxNewEleve.getChildren().add(fieldDate);
                vBoxNewEleve.getChildren().add(labelTel);
                vBoxNewEleve.getChildren().add(fieldTel);
                vBoxNewEleve.getChildren().add(labelAdresse);
                vBoxNewEleve.getChildren().add(fieldAdresse);
                vBoxNewEleve.getChildren().add(labelVille);
                vBoxNewEleve.getChildren().add(fieldVille);
                vBoxNewEleve.getChildren().add(labelEmail);
                vBoxNewEleve.getChildren().add(fieldEmail);
                vBoxNewEleve.getChildren().add(labelMDP);
                vBoxNewEleve.getChildren().add(fieldMDP);
                vBoxNewEleve.getChildren().add(labelFormation);
                vBoxNewEleve.getChildren().add(fieldFormation);
                vBoxNewEleve.getChildren().add(labelAdmin);
                vBoxNewEleve.getChildren().add(checkAdmin);
                vBoxNewEleve.getChildren().add(validerAjoutEleve);


                root.setCenter(vBoxNewEleve);

            }
        };


        EventHandler<ActionEvent> eventEraseStudent = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                nomBoutonModifier = "Supprimer";
                updateEleve.fire();
            }
        };

        EventHandler<ActionEvent> eventEditStudent = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    ConnexionBDD conn = new ConnexionBDD();
                    conn.seConnecter();

                    ResultSet rs = conn.afficherEleves();
                    TableView<Eleve> tableEleves = new TableView<Eleve>();

                    TableColumn<Eleve, Object> nomCol = new TableColumn("Nom");
                    nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
                    TableColumn<Eleve, Object> prenomCol = new TableColumn("Prenom");
                    prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
                    TableColumn<Eleve, Object> emailCol = new TableColumn("Email");
                    emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

                    tableEleves.getColumns().addAll(nomCol, prenomCol, emailCol);

                    while (rs.next()) {
                        Eleve e = new Eleve(rs.getString("nom"),
                                rs.getString("prenom"),
                                rs.getString("dateNaissance"),
                                rs.getString("telephone"),
                                rs.getString("adresse"),
                                rs.getString("ville"),
                                rs.getString("email"),
                                rs.getString("mdp"),
                                rs.getString("formation"),
                                rs.getInt("idClasse"));
                        tableEleves.getItems().add(e);
                    }
                    if (tableEleves.getItems().size() > 0) {

                        Button btModifier = new Button(nomBoutonModifier);
                        EventHandler<ActionEvent> eventLancerEdit = new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                Eleve e = tableEleves.getSelectionModel().getSelectedItem();
                                System.out.println(e.getEmail());
                                if (nomBoutonModifier == "Supprimer") {
                                    Alert alert = new Alert(AlertType.CONFIRMATION);
                                    alert.setContentText("Voulez vous vraiment supprimer l'utilisateur " + e.getEmail() + " ?");
                                    ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                                    ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
                                    alert.getButtonTypes().setAll(okButton, noButton);
                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == okButton) {
                                        try {
                                            ConnexionBDD conn = new ConnexionBDD();
                                            conn.seConnecter();
                                            if (conn.supprimerEleve(e) == true) {
                                                Alert ok = new Alert(AlertType.INFORMATION);
                                                ok.setContentText("Utilisateur effacé !");
                                                ok.showAndWait();
                                                root.setCenter(vBoxListeEleves);

                                                nomBoutonModifier = "Modifier";
                                            } else {
                                                Alert pasOk = new Alert(AlertType.WARNING);
                                                pasOk.setContentText("Erreur ! Impossible d'effacer l'utilisateur !");
                                                pasOk.showAndWait();
                                                root.setCenter(vBoxListeEleves);

                                                nomBoutonModifier = "Modifier";

                                            }
                                            conn.seDeconnecter();
                                        } catch (IOException ex) {
                                            ex.printStackTrace();
                                        } catch (SQLException ex) {
                                            ex.printStackTrace();
                                        }

                                    } else {

                                    }
                                } else {
                                    System.out.println(e.getEmail());

                                    userAModifier = e.getEmail();
                                    System.out.print(userAModifier);
                                    btModifier.setOnAction(eventNewStudent);
                                    btModifier.fire();
                                }


                            }
                        };
                        btModifier.setOnAction(eventLancerEdit);

                        vBoxListeElevesNewEdit.getChildren().clear();
                        vBoxListeElevesNewEdit.getChildren().add(btModifier);
                        vBoxListeElevesNewEdit.getChildren().add(tableEleves);
                        root.setCenter(vBoxListeElevesNewEdit);
                    } else {
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setContentText("Aucun Eleve dans la base");
                        alert.showAndWait();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        };

        EventHandler<ActionEvent> eventNewClassRoom = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                Label labelNom = new Label("Nom :");
                final TextField fieldNom = new TextField();

                Label labelCapacite = new Label("Capacite :");
                final TextField fieldCapacite = new TextField();

                if (classeAModifier > 0) {
                    try {
                        ConnexionBDD connexion = new ConnexionBDD();
                        connexion.seConnecter();
                        Classe c = connexion.rechercherClasse(classeAModifier);


                        fieldNom.setText(c.getNom());
                        fieldCapacite.setText("" + c.getCapacite());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                EventHandler<ActionEvent> eventValidateNewClassRoom = new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {

                        if (String.valueOf(fieldNom.getText()) == "" &&
                                String.valueOf(fieldCapacite.getText()) == "" ) {
                            Alert a = new Alert(AlertType.INFORMATION);
                            a.setContentText("Merci de remplir les champs avec un '*'");
                            a.showAndWait();
                        } else {
                            Classe c = new Classe (String.valueOf(fieldNom.getText()),
                                    Integer.parseInt(String.valueOf(fieldCapacite.getText())));
                            try {
                                ConnexionBDD connexion = new ConnexionBDD();
                                connexion.seConnecter();

                                if (classeAModifier > 0) {
                                    connexion.modifierClasse(c);
                                } else {
                                    connexion.ajouterClasse(c);
                                }

                                Alert a = new Alert(AlertType.CONFIRMATION);
                                a.setContentText("Classe Ajoutée !");
                                a.showAndWait();
                                root.setCenter(vBoxListeEleves);
                                connexion.seDeconnecter();
                                classeAModifier = 0;
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }

                        }

                        userAModifier = "";
                    }
                };


        vBoxNewClasse.getChildren().clear();
        Button validerAjoutClasse = new Button(nomBoutonModifier);
        validerAjoutClasse.setOnAction(eventValidateNewClassRoom);
        vBoxNewClasse.getChildren().add(labelNom);
        vBoxNewClasse.getChildren().add(fieldNom);
        vBoxNewClasse.getChildren().add(labelCapacite);
        vBoxNewClasse.getChildren().add(fieldCapacite);
        vBoxNewClasse.getChildren().add(validerAjoutClasse);


        root.setCenter(vBoxNewClasse);

            }
        };

        EventHandler<ActionEvent> eventEditClassRoom = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    ConnexionBDD conn = new ConnexionBDD();
                    conn.seConnecter();

                    ResultSet rs = conn.afficherClasses();
                    TableView<Classe> tableClasses = new TableView<Classe>();

                    TableColumn<Classe, Object> idCol = new TableColumn("id");
                    idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                    TableColumn<Classe, Object> nomCol = new TableColumn("nom");
                    nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
                    TableColumn<Classe, Object> capaciteCol = new TableColumn("Capacite");
                    capaciteCol.setCellValueFactory(new PropertyValueFactory<>("capacite"));

                    tableClasses.getColumns().addAll(idCol, nomCol, capaciteCol);

                    while (rs.next()) {
                        Classe c = new Classe(rs.getString("nom"),
                                rs.getInt("capacite"));
                        c.setId(rs.getInt("id"));
                        tableClasses.getItems().add(c);
                    }
                    if(tableClasses.getItems().size() > 0) {

                        Button btModifier = new Button(nomBoutonModifier);
                        EventHandler<ActionEvent> eventLancerEdit = new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                Classe c = tableClasses.getSelectionModel().getSelectedItem();
                                if (nomBoutonModifier == "Supprimer") {
                                    Alert alert = new Alert(AlertType.CONFIRMATION);
                                    alert.setContentText("Voulez vous vraiment supprimer l'utilisateur " + c.getNom() + " ?");
                                    ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                                    ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
                                    alert.getButtonTypes().setAll(okButton, noButton);
                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == okButton) {
                                        try {
                                            ConnexionBDD conn = new ConnexionBDD();
                                            conn.seConnecter();
                                            if (conn.supprimerClasse(c) == true) {
                                                Alert ok = new Alert(AlertType.INFORMATION);
                                                ok.setContentText("Classe effacée !");
                                                ok.showAndWait();
                                                root.setCenter(vBoxListeEleves);

                                                nomBoutonModifier = "Modifier";
                                            } else {
                                                Alert pasOk = new Alert(AlertType.WARNING);
                                                pasOk.setContentText("Erreur ! Impossible d'effacer la classe !");
                                                pasOk.showAndWait();
                                                root.setCenter(vBoxListeEleves);

                                                nomBoutonModifier = "Modifier";

                                            }
                                            conn.seDeconnecter();
                                        } catch (IOException ex) {
                                            ex.printStackTrace();
                                        } catch (SQLException ex) {
                                            ex.printStackTrace();
                                        }

                                    }
                                } else {

                                    classeAModifier = c.getId();
                                    btModifier.setOnAction(eventNewClassRoom);
                                    btModifier.fire();
                                }


                            }
                        };
                        btModifier.setOnAction(eventLancerEdit);

                        vBoxListeElevesNewEdit.getChildren().clear();
                        vBoxListeElevesNewEdit.getChildren().add(btModifier);
                        vBoxListeElevesNewEdit.getChildren().add(tableClasses);
                        root.setCenter(vBoxListeElevesNewEdit);
                    }else{
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setContentText("Aucun Eleve dans la base");
                        alert.showAndWait();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        };

        EventHandler<ActionEvent> eventEraseClassRoom = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                nomBoutonModifier = "Supprimer";
                updateClasse.fire();
            }
        };



        // Create MenuItems
        nouvelEleve.setOnAction(eventNewStudent);
        updateEleve.setOnAction(eventEditStudent);
        supprimerEleve.setOnAction(eventEraseStudent);

        nouvelleClasse.setOnAction(eventNewClassRoom);
        updateClasse.setOnAction(eventEditClassRoom);
        supprimerClasse.setOnAction(eventEraseClassRoom);


        // Add menuItems to the Menus
        eleveMenu.getItems().addAll(nouvelEleve, updateEleve, supprimerEleve);
        classeMenu.getItems().addAll(nouvelleClasse, updateClasse, supprimerClasse);

        // Add Menus to the MenuBar
        menuBar.getMenus().addAll(eleveMenu, classeMenu);


        BorderPane accueilBorderPane = new BorderPane();
        accueilBorderPane.setTop(menuBar);

    }
}

