package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javafx.scene.image.Image;
import tn.esprit.models.voiture;
import tn.esprit.services.voitureServices;

public class ajouterVoituresController {

    private final voitureServices vs = new voitureServices();

    @FXML
    private TextField immatriculation;

    @FXML
    private ComboBox<String> agenceComboBox;

    @FXML
    private TextField modele;

    @FXML
    private ComboBox<String> nbr_places;

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
        if (!validateInput()) {
            return;
        }


        String prixLocationText = prixdelocation.getText();
        if (!isValidNumber(prixLocationText)) {
            showErrorAlert("Le prix de location doit être un nombre valide.");
            return;
        }

        String immatriculationText = immatriculation.getText();

        try {
            // Vérifier si l'immatriculation existe déjà
            if (vs.immatriculationExists(immatriculationText)) {
                showErrorAlert("L'immatriculation existe déjà. Veuillez saisir une autre immatriculation.");
                return;
            }

            String nomAgence = agenceComboBox.getValue();
            int idAgence = vs.getIdAgenceByNom(nomAgence);
            int nbrPlaces = Integer.parseInt(nbr_places.getValue());
            voiture v = new voiture(immatriculation.getText(), modele.getText(), nbrPlaces, couleur.getText(), Integer.parseInt(prixLocationText), imageView.getImage().getUrl());
            v.setId_agence(idAgence);
            v.setNom_agence(nomAgence); // Ajout du nom de l'agence
            vs.ajouterVoiture(v);
            showSuccessAlert("Voiture ajoutée avec succès !");
        } catch (SQLException e) {
            showErrorAlert("Erreur lors de l'ajout de la voiture : " + e.getMessage());
        }
    }

    boolean isValidNumber(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
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

            showSuccessAlert("L'image a été téléchargée avec succès !");
        }
    }

    @FXML
    void naviguerVersAffichage(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle scène
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherVoitures.fxml"));
            Parent root = loader.load();

            // Récupérer la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacer le contenu de la scène actuelle par le nouveau Parent
            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean validateInput() {
        if (immatriculation.getText().isEmpty() || modele.getText().isEmpty() || nbr_places.getValue() == null
                || couleur.getText().isEmpty() || prixdelocation.getText().isEmpty() || imageView.getImage() == null
                || agenceComboBox.getValue() == null) {
            showErrorAlert("Veuillez remplir tous les champs !");
            return false;
        }

        try {
            String selectedValue = nbr_places.getValue();
            Integer nbrPlaces = Integer.parseInt(selectedValue);
            int prixLocation = Integer.parseInt(prixdelocation.getText());

            if (nbrPlaces <= 0 || prixLocation <= 0) {
                showErrorAlert("Le nombre de places et le prix de location doivent être supérieurs à zéro !");
                return false;
            }
        } catch (NumberFormatException e) {
            showErrorAlert("Le nombre de places et le prix de location doivent être des valeurs numériques !");
            return false;
        }

        // Validation de l'immatriculation
        String immatriculationText = immatriculation.getText();
        String immatriculationPattern = "\\d{3}\\sTUNIS\\s\\d{4}";

        if (!immatriculationText.matches(immatriculationPattern)) {
            showErrorAlert("L'immatriculation doit être au format : 111 TUNIS 2222");
            return false;
        }

        return true;
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void naviguerVersAgences(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle scène
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterAgences.fxml"));
            Parent root = loader.load();

            // Récupérer la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacer le contenu de la scène actuelle par le nouveau Parent
            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}