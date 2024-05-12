package tn.esprit.controllers;

import tn.esprit.controllers.CardFront;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.esprit.services.ServiceEvenement;
import entities.evenement;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class EventFrontController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;

    private ServiceEvenement serviceEvenement = new ServiceEvenement();

    private List<evenement> evenements;

    @FXML
    public void initialize() {
        try {
            evenements = serviceEvenement.afficher();
            loadAllEvents();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAllEvents() {
        int columnCount = 3; // Number of columns in the grid
        int rowIndex = 0;
        int columnIndex = 0;

        for (evenement event : evenements) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/CardFront.fxml"));
                AnchorPane cardFrontPane = loader.load();
                CardFront controller = loader.getController();
                controller.setEvent(event);

                // Add padding to each card
                cardFrontPane.setStyle("-fx-padding: 10px;");

                // Add the card to the grid
                gridPane.add(cardFrontPane, columnIndex, rowIndex);

                // Increment columnIndex and move to the next row if necessary
                columnIndex++;
                if (columnIndex >= columnCount) {
                    columnIndex = 0;
                    rowIndex++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Adjust spacing between columns and rows
        gridPane.setHgap(20); // Horizontal spacing
        gridPane.setVgap(20); // Vertical spacing
    }


    @FXML
    void openChatbot() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Chatbot.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Chatbot");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
