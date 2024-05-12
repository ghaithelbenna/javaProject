package tn.esprit.controllers;

import com.twilio.type.PhoneNumber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import tn.esprit.models.reservation;
import tn.esprit.services.reservationServices;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import tn.esprit.models.reservation;
import tn.esprit.services.reservationServices;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class ajouterReservationController {

    private int vehiculeId;

    public void setVehiculeId(int vehiculeId) {
        this.vehiculeId = vehiculeId;
    }

    // Obtention de la date actuelle
    LocalDate date = LocalDate.now();
    // Formattage de la date selon le format souhaité
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String formattedDate = date.format(formatter);

    @FXML
    private Button retourVersFront;

    @FXML
    private DatePicker dateDebutPicker;

    @FXML
    private DatePicker dateFinPicker;

    @FXML
    private Button ajouterReservation;
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setStage(Stage stage) {
        primaryStage = stage;
    }

    @FXML
    public void ajouterReservation() {
        LocalDate dateDebut = dateDebutPicker.getValue();
        LocalDate dateFin = dateFinPicker.getValue();

        if (dateDebut == null || dateFin == null) {
            // Afficher une alerte indiquant que les champs de date sont vides
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Champs de date vides");
            alert.setContentText("Veuillez sélectionner une date de début et une date de fin");
            alert.showAndWait();
            return;
        }

        // Vérifier si la date de début est antérieure à la date de fin
        if (dateDebut.isBefore(dateFin)) {
            // Vérifier si une réservation avec la même date de début existe déjà
            reservationServices service = new reservationServices();
            try {
                if (service.existeReservationDateDebut(dateDebut)) {
                    // Afficher un message d'erreur
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Réservation existante");
                    alert.setContentText("Malheureusement une réservation avec la même date de début existe déjà. Veuillez saisir une autre ");
                    alert.showAndWait();
                } else {
                    // Créer un objet de type reservation avec les valeurs saisies
                    reservation nouvelleReservation = new reservation(dateDebut.atStartOfDay(), dateFin.atStartOfDay());

                    // Appeler le service de réservation pour ajouter la réservation
                    service.ajouterReservation(nouvelleReservation);
                    System.out.println("Réservation ajoutée avec succès");

                    // Envoi du SMS de confirmation
                    String recipientPhoneNumber = "+21628263821"; // Numéro de téléphone du destinataire
                    String messageContent = "Votre réservation de  " + formattedDate + " a été confirmée avec succès ! Veuillez terminer les procedures du paiement ";

                    sendSMS(recipientPhoneNumber, messageContent);
                    // Revenir à l'interface utilisateur du front
                    retourVersFront(new ActionEvent());
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout de la réservation : " + e.getMessage());
            }
        } else {
            System.out.println("La date de début doit être antérieure à la date de fin");
        }
    }

    private void sendSMS(String recipientPhoneNumber, String messageContent) {
        // Initialisation des informations d'identification Twilio
        String ACCOUNT_SID = "ACc7dca76d585525acbc86e706c4b67c91";
        String AUTH_TOKEN = "cb7d611da08bbb8f534a68b4ab3d6566";
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Envoi du SMS
        Message message = Message.creator(
                        new PhoneNumber(recipientPhoneNumber), // Numéro de téléphone du destinataire
                        new PhoneNumber("+14088404213"), // Numéro de téléphone Twilio
                        messageContent)
                .create();

        System.out.println("SMS envoyé avec le SID : " + message.getSid());
    }



    @FXML
    private void retourVersFront(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherFrontVehicule.fxml"));
            Parent root = loader.load();

            Scene currentScene = ((Node) event.getSource()).getScene();
            Stage stage = (Stage) currentScene.getWindow();
            stage.setScene(new Scene(root));

            // Fermer la fenêtre d'ajout actuelle
            //stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
