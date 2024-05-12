package tn.esprit.controllers;

import tn.esprit.models.guide;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.esprit.services.serviceGuide;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ListGuideController {

    @FXML
    private ListView<guide> guideListView;

    private serviceGuide serviceGuide;

    public ListGuideController() {
        serviceGuide = new serviceGuide();
    }

    @FXML
    public void initialize() {
        guideListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        loadGuides();
        setCellFactory();
    }

    private void setCellFactory() {
        guideListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<guide> call(ListView<guide> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(guide item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(String.format("Nom: %s, Prénom: %s, Age: %s, Sexe: %s",
                                    item.getNom_guide(), item.getPrenom_guide(), item.getAge_guide(), item.getSexe_guide()));
                        }
                    }
                };
            }
        });
    }

    public void loadGuides() {
        try {
            List<guide> guides = serviceGuide.afficher();
            ObservableList<guide> observableGuides = FXCollections.observableArrayList(guides);
            guideListView.setItems(observableGuides);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void AddGuide(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutGuide.fxml"));
            Parent root = loader.load();

            Scene currentScene = guideListView.getScene();

            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ModifyGuide() {
        // Get the selected guide from the ListView
        guide selectedGuide = guideListView.getSelectionModel().getSelectedItem();
        if (selectedGuide == null) {
            // No guide selected, show an alert
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun guide sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un guide à modifier.");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyGuide.fxml"));
            Parent root = loader.load();
            tn.esprit.controllers.ModifyGuideController modifyGuideController = loader.getController();
            modifyGuideController.initData(selectedGuide,serviceGuide);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier un guide");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            loadGuides();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteSelectedGuides() {
        ObservableList<guide> selectedGuides = guideListView.getSelectionModel().getSelectedItems();
        if (selectedGuides.isEmpty()) {
            // No guides selected, show an alert
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun guide sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner au moins un guide à supprimer.");
            alert.showAndWait();
            return;
        }

        try {
            // Delete the selected guides from the database
            for (guide guide : selectedGuides) {
                serviceGuide.supprimer(guide);
            }

            // Remove the selected guides from the ObservableList
            guideListView.getItems().removeAll(selectedGuides);

        } catch (SQLException e) {
            e.printStackTrace();
            // Show an error alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur lors de la suppression");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite lors de la suppression des guides.");
            alert.showAndWait();
        }
    }


    @FXML
    void StatBtn(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Stat.fxml"));
            Parent root = fxmlLoader.load();

            // Get the list of guides
            List<guide> guides = serviceGuide.afficher(); // Assuming you have a method to retrieve all guides

            // Get the controller and initialize it with the guides data
            StatController statController = fxmlLoader.getController();
            statController.initialize(guides);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Statistics");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }


    private Scene previousScene;

    public void naviguer(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListEvenement.fxml"));
            Parent root = loader.load();
            previousScene = ((Node) event.getSource()).getScene(); // Conserve la référence à la scène précédente

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void RETOUR(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
            Parent root = loader.load();
            previousScene = ((Node) event.getSource()).getScene(); // Conserve la référence à la scène précédente

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
