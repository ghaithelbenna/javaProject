package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.Node;

import models.voiture;
import services.voitureServices;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import java.io.File;
import java.util.List;

import javafx.scene.image.Image;

public class ajouterVoituresController {

    private final voitureServices vs = new voitureServices();

    @FXML
    private TextField immatriculation;

    @FXML

    private ComboBox<String> agenceComboBox;

    @FXML
    private TextField modele;

    @FXML
    private TextField nbr_places;

    @FXML
    private TextField couleur;

    @FXML
    private TextField prixdelocation;

    @FXML
    private ImageView imageView;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize() {
        // Récupérer les noms des agences depuis la table locationvoitures
        List<String> nomsAgences = vs.getNomsAgences();

        // Ajouter les noms des agences au ComboBox
        agenceComboBox.getItems().addAll(nomsAgences);
    }

    @FXML
    void ajouterVoiture(ActionEvent event) {
        if (immatriculation.getText().isEmpty() || modele.getText().isEmpty() || nbr_places.getText().isEmpty()
                || couleur.getText().isEmpty() || prixdelocation.getText().isEmpty() || imageView.getImage() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.showAndWait();
            return;
        }
        try {
            vs.ajouterVoiture(new voiture(immatriculation.getText(), modele.getText(),
                    Integer.parseInt(nbr_places.getText()), couleur.getText(),
                    Integer.parseInt(prixdelocation.getText()), imageView.getImage().getUrl()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Voiture ajoutée avec succès !");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void downloadButtonClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une image à télécharger");

        // Définir les extensions de fichiers autorisées pour les images
        ExtensionFilter extFilter = new ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);

        // Afficher la boîte de dialogue de sélection de fichier
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            // Chargement de l'image depuis le fichier sélectionné
            Image image = new Image(selectedFile.toURI().toString());

            // Définir l'image chargée dans l'ImageView
            imageView.setImage(image);

            // Affichez un message de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setContentText("L'image a été téléchargée avec succès !");
            alert.showAndWait();
        }
    }

    @FXML
    void naviguerVersAffichage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherVoitures.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Afficher Voitures");
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre d'ajout actuelle
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}