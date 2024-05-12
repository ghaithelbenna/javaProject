package tn.esprit.controllers;

import entities.evenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.services.EmailService;

public class CardFront {

    @FXML
    private AnchorPane BoxEvent;

    @FXML
    private Label nom;

    @FXML
    private Label type;

    @FXML
    private Label duree;

    @FXML
    private Label date;

    private evenement event;

    // Email service instance
    private EmailService emailService;

    // Constructor
    public CardFront() {
        // Initialize the EmailService with your email credentials
        this.emailService = new EmailService("jaffela2011@gmail.com", "wfji kyxj mpow ehtg");
    }

    // Method to set event data
    public void setEvent(evenement event) {
        this.event = event;
        if (event != null) {
            nom.setText(event.getNom_event());
            type.setText(event.getType_event());
            duree.setText(event.getDuree_event());
            date.setText(event.getDate_event().toString());
        }
    }

    // Method to handle button click for sending email
    @FXML
    void mail(ActionEvent event) {
        // Check if the event is not null
        if (this.event != null) {
            // Construct email subject and body using event details
            String subject = "Invitation to " + this.event.getNom_event();
            String body = "You are invited to attend " + this.event.getNom_event() + ".\n"
                    + "Type: " + this.event.getType_event() + "\n"
                    + "Destination: " + this.event.getDuree_event() + "\n"
                    + "Date: " + this.event.getDate_event().toString() + "\n"
                    + "Please join us!";
            // Send email
            emailService.sendEmail("haythem.khamassi@esprit.tn", subject, body);

            // Show success message
            showAlert("Success", "Mail sent successfully!");
        } else {
            System.out.println("Event is null. Cannot send email.");
        }
    }

    // Method to show an alert dialog
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
