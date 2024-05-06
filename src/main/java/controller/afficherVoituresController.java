package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.File;
import java.io.IOException;
import models.voiture;
import services.voitureServices;

public class afficherVoituresController {

    private final voitureServices vs = new voitureServices();

    @FXML
    private ListView<voiture> listView;

    @FXML
    private Button versAjout;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize() {
        afficherVoitures();
    }

    public void afficherVoitures() {
        ObservableList<voiture> voitures = vs.getAll();

        listView.setItems(voitures);

        // Définir une cellule personnalisée pour afficher les voitures
        listView.setCellFactory(param -> new ListCell<voiture>() {
            @Override
            protected void updateItem(voiture item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    String voitureInfo = "immatriculation: " + item.getImmatriculation() + "\n" +
                            "Modèle: " + item.getModele() + "\n" +
                            "nbr places: " + item.getNbr_places() + "\n" +
                            "Couleur: " + item.getCouleur() + "\n" +
                            "prix de location: " + item.getPrixdelocation() + "\n" +
                            "imagePath: " + item.getImagePath() + "\n" +
                            "Nom agence: " + item.getNom_agence(); // Ajout du nom de l'agence
                    setText(voitureInfo);
                }
            }
        });

        // Ajouter un gestionnaire d'événements pour écouter le clic sur une voiture dans la liste
        listView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) { // Double-clic
                voiture selectedVoiture = listView.getSelectionModel().getSelectedItem();
                if (selectedVoiture != null) {
                    openModifierVoitureWindow(event, selectedVoiture);
                }
            }
        });
    }

    public void openModifierVoitureWindow(MouseEvent event, voiture selectedVoiture) {
        if (selectedVoiture != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateVoitures.fxml"));
                Parent root = loader.load();

                // Obtenir la référence du contrôleur de la fenêtre de mise à jour
                updateVoituresController controller = loader.getController();
                controller.setVoiture(selectedVoiture);
                controller.setAfficherController(this); // Passer la référence de la fenêtre d'affichage

                // Charger l'image stockée dans la voiture et la passer au contrôleur de mise à jour
                String imagePath = selectedVoiture.getImagePath();
                if (imagePath != null && !imagePath.isEmpty()) {
                    File imageFile = new File(imagePath);
                    Image image = new Image(imageFile.toURI().toString());
                    controller.setImage(image); // Afficher l'image dans l'imageView
                } else {
                    // Afficher une image par défaut ou laisser l'imageView vide
                    // Par exemple:
                    Image defaultImage = new Image(getClass().getResourceAsStream("/default_image.png"));
                    controller.setImage(defaultImage); // Afficher l'image par défaut
                }

                Stage stage = new Stage();
                stage.setTitle("Modifier Voiture");
                stage.setScene(new Scene(root));
                stage.show();

                // Fermer la fenêtre d'affichage actuelle
                Stage currentStage = (Stage) listView.getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void versAjout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterVoitures.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Ajouter Voitures");
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre d'affichage actuelle
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}