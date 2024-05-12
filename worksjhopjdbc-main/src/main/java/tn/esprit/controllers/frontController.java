package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class frontController {
    private void navigateToFXML(String fxmlFileName, ActionEvent event) {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Remplacer la scène actuelle par la nouvelle scène
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    @FXML
    public void Transport(ActionEvent event) {
        navigateToFXML("/afficherFrontVehicule.fxml", event);
    }
    @FXML
    public void Pack(ActionEvent event) {
        navigateToFXML("/AffichageFront.fxml", event);
    }
    @FXML
    public void Profil(ActionEvent actionEvent) {
    }

    public void Hebergement(ActionEvent actionEvent) {
    }

    public void Event(ActionEvent actionEvent) {
    }

    public void Blog(ActionEvent actionEvent) {
    }

    public void LoginClicked(ActionEvent actionEvent) {
        try {
            // Load the login screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();

            // Get the current stage from the action event source
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
