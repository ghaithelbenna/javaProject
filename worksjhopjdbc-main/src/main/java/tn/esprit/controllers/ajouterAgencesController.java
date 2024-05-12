package tn.esprit.controllers;

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

import tn.esprit.models.agencedelocation;
import tn.esprit.models.voiture;
import tn.esprit.services.agenceServices;
import tn.esprit.services.voitureServices;

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
            // Charger le fichier FXML de la nouvelle scène
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherAgences.fxml"));
            Parent root = loader.load();

            // Récupérer la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacer le contenu de la scène actuelle par le nouveau Parent
            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void naviguerVersVoitures(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle scène
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterVoitures.fxml"));
            Parent root = loader.load();

            // Récupérer la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacer le contenu de la scène actuelle par le nouveau Parent
            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void naviguerVersAgences(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle scène
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterAgences.fxml"));
            Parent root = loader.load();

            // Récupérer la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacer le contenu de la scène actuelle par le nouveau Parent
            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    }


