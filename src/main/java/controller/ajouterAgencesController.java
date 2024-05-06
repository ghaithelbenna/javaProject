package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.Node;

import models.agencedelocation;
import models.voiture;
import services.agenceServices;
import services.voitureServices;

public class ajouterAgencesController {
    @FXML
    private TextField nom_agence;

    @FXML
    private TextField adresse_agence;

    @FXML
    private TextField nbrvoitures_dispo;

    private final agenceServices as = new agenceServices();



    //private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private Stage primaryStage;

    @FXML
    void ajouterAgence(ActionEvent event) {
        if (nom_agence.getText().isEmpty() || adresse_agence.getText().isEmpty() || nbrvoitures_dispo.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.showAndWait();
            return;
        }

        try {
            int nbrVoituresDispo = Integer.parseInt(nbrvoitures_dispo.getText());

            // Vérifier si le nombre de voitures est entier
            if (nbrVoituresDispo < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Le nombre de voitures doit être un entier positif !");
                alert.showAndWait();
                return;
            }

            // Ajouter l'agence si la validation réussit
            as.ajouterAgence(new agencedelocation(nom_agence.getText(), adresse_agence.getText(), nbrVoituresDispo));

            // Afficher une alerte de succès
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Succès");
            successAlert.setContentText("Agence ajoutée avec succès !");
            successAlert.showAndWait();
        } catch (NumberFormatException e) {
            // Gérer les erreurs de conversion de chaîne en entier
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Le nombre de voitures doit être un nombre entier !");
            alert.showAndWait();
        } catch (SQLException e) {
            // Gérer les erreurs de base de données
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void ajoutversAfffichage(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherAgences.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Afficher Agences");
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre d'ajout actuelle
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void naviguerVersVoitures(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterVoitures.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Ajouter Voitures");
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre d'ajout actuelle
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void naviguerVersAgences(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterAgences.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Ajouter Voitures");
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre d'ajout actuelle
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    }


