package tn.esprit.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.models.voiture;
import tn.esprit.services.voitureServices;

import java.io.File;
import java.io.IOException;

public class affichageFrontVehiculeController {
    private final voitureServices voitureService = new voitureServices();

    @FXML
    private FlowPane carContainer;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Button reserverButton;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize() {

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        carContainer.setHgap(20);
        afficherVoitures();
    }

    public void afficherVoitures() {
        ObservableList<voiture> voitures = voitureService.getAll();

        for (voiture voiture : voitures) {
            carContainer.getChildren().add(createCarCard(voiture));
        }
    }

    public Node createCarCard(voiture voiture) {
        VBox carCard = new VBox();
        carCard.getStyleClass().add("car-card");
        carCard.setSpacing(10);

        StackPane imageContainer = new StackPane();
        imageContainer.getStyleClass().add("car-image-container");
        ImageView carImage = new ImageView();
        carImage.setFitWidth(200);
        carImage.setFitHeight(200);
        carImage.setPreserveRatio(true);

        // Chargement de l'image à partir du chemin d'accès
        String imagePath = voiture.getImagePath();
        if (imagePath != null && !imagePath.isEmpty()) {
            File imageFile = new File(imagePath);
            Image image = new Image(imageFile.toURI().toString());
            carImage.setImage(image);
        } else {
            // Utilisation d'une image par défaut si aucun chemin d'accès n'est fourni
            Image defaultImage = new Image(getClass().getResourceAsStream("/default_car_image.png"));
            carImage.setImage(defaultImage);
        }

        imageContainer.getChildren().add(carImage);

        Label immatriculationLabel = new Label("Immatriculation: " + voiture.getImmatriculation());
        Label modeleLabel = new Label("Modèle: " + voiture.getModele());
        Label placesLabel = new Label("Nombre de places: " + voiture.getNbr_places());
        Label couleurLabel = new Label("Couleur: " + voiture.getCouleur());
        Label prixLabel = new Label("Prix: " + voiture.getPrixdelocation());

        // Appliquer des styles CSS aux labels si nécessaire
        immatriculationLabel.getStyleClass().add("car-info-label");
        modeleLabel.getStyleClass().add("car-info-label");
        placesLabel.getStyleClass().add("car-info-label");
        couleurLabel.getStyleClass().add("car-info-label");
        prixLabel.getStyleClass().add("car-info-label");

        // Création du bouton Réserver
        Button reserverButton = new Button("Réserver");
        reserverButton.getStyleClass().add("reserve-button");

        reserverButton.setOnAction(event -> {
            Scene currentScene = reserverButton.getScene();
            afficherFenetreReservation(currentScene, voiture);
        });



        // Ajout des éléments à la carte de voiture
        carCard.getChildren().addAll(imageContainer, immatriculationLabel, modeleLabel, placesLabel, couleurLabel, prixLabel, reserverButton);

        return carCard;
    }


    private void afficherFenetreReservation(Scene scene, voiture voiture) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterReservations.fxml"));
            Parent root = loader.load();
            ajouterReservationController reservationsController = loader.getController();
            reservationsController.setVehiculeId(voiture.getId_vehicule());

            // Remplacez simplement la scène donnée par la nouvelle scène
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Scene previousScene;
    public void RETOUR(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/front.fxml"));
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
