package tn.esprit.controllers;

import entities.evenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.services.ServiceEvenement;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AjoutEvenementController {

    @FXML
    private TextField DureeEvent;

    @FXML
    private TextField NomEvent;

    @FXML
    private TextField TypeEvent;

    @FXML
    private DatePicker datePick;

    private ServiceEvenement serviceEvenement;


    public AjoutEvenementController() {
        serviceEvenement = new ServiceEvenement();
    }

    @FXML
    void SaveBtn(ActionEvent event) {
        String nomEvent = NomEvent.getText();
        String typeEvent = TypeEvent.getText();
        String dureeEvent = DureeEvent.getText();
        LocalDate dateEvent = datePick.getValue();

        if (nomEvent.isEmpty()  || typeEvent.isEmpty() || dureeEvent.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Ajout impossible", "Veuillez remplir tous les champs.");
            return;
        }

        if (nomEvent.length() > 10) {
            showAlert(Alert.AlertType.WARNING, "Ajout impossible", "Le champ Nom Event ne doit pas dépasser 10 caractères.");
            return;
        }

        if (!nomEvent.matches("^[a-zA-Z]+$")) {
            showAlert(Alert.AlertType.WARNING, "Ajout impossible", "Le champ Nom Event doit contenir uniquement des caractères alphabétiques.");
            return;
        }

        if (!isValidDate(dateEvent) || !isValidDate(dateEvent)) {
            showAlert(Alert.AlertType.WARNING, "Dates incorrectes", "Le date doivent être au format jj/mm/aaaa ");
            return;
        }

        if (dureeEvent.length() > 5) {
            showAlert(Alert.AlertType.WARNING, "Ajout impossible", "Le champ Duree Event ne doit pas dépasser 10 caractères.");
            return;
        }

        if (!typeEvent.matches("^[a-zA-Z]+$")) {
            showAlert(Alert.AlertType.WARNING, "Ajout impossible", "Le champ Type Event doit contenir uniquement des caractères alphabétiques.");
            return;
        }




        evenement newEvenement = new evenement(nomEvent, typeEvent, dureeEvent, dateEvent);

        try {
            serviceEvenement.ajouter(newEvenement);
            System.out.println("Evenement ajouté avec succès !");

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListEvenement.fxml"));
            Parent root = loader.load();
            tn.esprit.controllers.ListEvenementController listController = loader.getController();
            listController.loadEvents();

            Scene scene = new Scene(root);
            Stage listStage = new Stage();
            listStage.setScene(scene);
            listStage.setTitle("Liste des Événements");
            listStage.show();
        } catch (SQLException | IOException e) {
            System.err.println("Erreur lors de l'ajout de l'événement : " + e.getMessage());
        }
    }

    private void clearFields() {
        NomEvent.clear();
        TypeEvent.clear();
        DureeEvent.clear();
        datePick.setValue(null);
    }


    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean isValidDate(LocalDate date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            formatter.format(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }


    }

