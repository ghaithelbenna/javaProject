package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.voiture;
import services.voitureServices;

import java.io.File;
import java.io.IOException;

public class affichageFrontController {
    private final voitureServices voitureService = new voitureServices();

    @FXML
    private FlowPane carContainer;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize() {
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

        ImageView carImage = new ImageView();
        carImage.setFitWidth(200);
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

        Label immatriculationLabel = new Label("Immatriculation: " + voiture.getImmatriculation());
        Label modeleLabel = new Label("Modèle: " + voiture.getModele());
        Label placesLabel = new Label("Nombre de places: " + voiture.getNbr_places());

        // Appliquer des styles CSS aux labels si nécessaire
        immatriculationLabel.getStyleClass().add("car-info-label");
        modeleLabel.getStyleClass().add("car-info-label");
        placesLabel.getStyleClass().add("car-info-label");

        // Ajout des éléments à la carte de voiture
        carCard.getChildren().addAll(carImage, immatriculationLabel, modeleLabel, placesLabel);
        return carCard;
    }
}
