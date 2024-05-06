package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;
import models.agencedelocation;
import services.agenceServices;

public class afficherAgencesController {

    private final agenceServices as = new agenceServices();

    @FXML
    private ListView<agencedelocation> listView;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize() {
        afficherAgences();
    }

    public void afficherAgences() {
        ObservableList<agencedelocation> agences = as.getAllAgences();
        listView.setItems(agences);

        listView.setCellFactory(param -> new ListCell<agencedelocation>() {
            @Override
            protected void updateItem(agencedelocation item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    String agenceInfo = "Nom: " + item.getNom_agence() + "\n" +
                            "Adresse: " + item.getAdresse_agence() + "\n" +
                            "Nombre de voitures disponibles: " + item.getNbrvoitures_dispo();
                    setText(agenceInfo);
                }
            }
        });

        listView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                agencedelocation agence = listView.getSelectionModel().getSelectedItem();
                if (agence != null) {
                    openModifierAgenceWindow(event, agence);
                }
            }
        });
    }

    public void openModifierAgenceWindow(MouseEvent event, agencedelocation agence) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateAgences.fxml"));
            Parent root = loader.load();

            updateAgencesController controller = loader.getController();
            controller.setAgencedelocation(agence);
            controller.setAfficherAgencesController(this);

            Stage stage = new Stage();
            stage.setTitle("Modifier Agence");
            stage.setScene(new Scene(root));
            stage.show();

            //Stage currentStage = (Stage) listView.getScene().getWindow();
            //currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ajoutAgenceBTN(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterAgences.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Ajouter Agence");
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}