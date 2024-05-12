package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.models.User;
import tn.esprit.services.UserService;

import java.io.IOException;

public class HomeController {
    private int userId; // Variable to store the user ID
    public void initData(int userId) {
        this.userId = userId;
        populateUserProfile();
    }
    @FXML
    private AnchorPane rootPane; // Root pane of your layout

    // Event handler for the "Pack" button
    @FXML
    private void Pack(ActionEvent event) {
        navigateToFXML("/AfficherPack.fxml", event);
    }

    // Event handler for the "Véhicule" button
    @FXML
    private void Véhicule(ActionEvent event) {
        // Implement navigation logic to navigate to the Véhicule view
        navigateToFXML("/ajouterVoitures.fxml", event);
    }

    // Event handler for the "Hebergement" button
    @FXML
    private void Hebergement(ActionEvent event) {
        // Implement navigation logic to navigate to the Hebergement view
        navigateToFXML("/List.fxml", event);
    }

    // Event handler for the "Blog" button
    @FXML
    private void Blog(ActionEvent event) {
        // Implement navigation logic to navigate to the Blog view
        navigateToFXML("/FXMLPost.fxml", event);
    }

    // Event handler for the "Event" button
    @FXML
    private void Event(ActionEvent event) {
        // Implement navigation logic to navigate to the Event view
        navigateToFXML("/ListEvenement.fxml", event);
    }

    // Event handler for the "Utilisateur" button
    @FXML
    private void Utilisateur(ActionEvent event) {
        // Implement navigation logic to navigate to the Utilisateur view
        navigateToFXML("/BackPage.fxml", event);
    }

    // Method to load an FXML file
    // Method to load an FXML file
    private void navigateToFXML(String fxmlFileName, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }




}
    private void populateUserProfile() {
        // Use the user ID to retrieve user data from the database
        User user = UserService.getUserById(userId);

        // Check if the user exists
        if (user != null) {
            // Populate user profile fields with user data
            // For example:
            // nameLabel.setText(user.getName());
            // emailLabel.setText(user.getEmail());
        } else {
            // If user does not exist, display an error message
            showAlert(Alert.AlertType.ERROR, "User Not Found", "Error", "No user found with this ID.");
        }
    }

    // Method to show an alert dialog
    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
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
