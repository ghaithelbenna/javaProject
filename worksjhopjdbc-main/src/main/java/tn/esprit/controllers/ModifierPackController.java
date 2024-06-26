package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.Pack;
import tn.esprit.services.ServicePack;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class ModifierPackController {

    @FXML
    private TextField NomT;

    @FXML
    private TextField DescriptionT;

    @FXML
    private TextField PrixT;

    @FXML
    private DatePicker DateT;

    @FXML
    private Button ImageT;

    @FXML
    private CheckBox DisponibleT;

    @FXML
    private ImageView imageView;

    private Pack pack;
    private AfficherPackController afficherPackController;

    @FXML
    void handleImageSelection(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir une image");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
            );
            File selectedFile = fileChooser.showOpenDialog(imageView.getScene().getWindow());
            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                imageView.setImage(image);
                pack.setImage(selectedFile.toURI().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void modifier(ActionEvent event) {
        if (pack != null) {
            pack.setNomPack(NomT.getText());
            pack.setDescriptionPack(DescriptionT.getText());
            pack.setPrix(Float.valueOf(PrixT.getText()));
            pack.setDate(Date.valueOf(DateT.getValue()));
            pack.setDisponible(DisponibleT.isSelected());

            ServicePack servicePack = new ServicePack();
            servicePack.update(pack);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modification réussie");
            alert.setHeaderText(null);
            alert.setContentText("Les détails du pack ont été modifiés avec succès !");
            alert.showAndWait();

            if (afficherPackController != null) {
                afficherPackController.affichage();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Aucun pack à modifier !");
            alert.showAndWait();
        }
    }


    public void initData(Pack pack, AfficherPackController afficherPackController) {
        this.pack = pack;
        this.afficherPackController = afficherPackController;
        if (pack != null) {
            NomT.setText(pack.getNomPack());
            DescriptionT.setText(pack.getDescriptionPack());
            PrixT.setText(String.valueOf(pack.getPrix()));
            if (pack.getDate() != null) {
                DateT.setValue(LocalDate.ofEpochDay(pack.getDate().getDate()));
            }
            DisponibleT.setSelected(pack.isDisponible());

            if (pack.getImage() != null && !pack.getImage().isEmpty()) {
                File imageFile = new File(pack.getImage());
                if (imageFile.exists()) {
                    String imageUrl = imageFile.toURI().toString();
                    Image image = new Image(imageUrl);
                    imageView.setImage(image);
                } else {
                    System.out.println("Le fichier image n'existe pas : " + pack.getImage());
                }
            } else {
                System.out.println("L'URL de l'image est vide ou nulle.");
            }

        }
    }

    public void setAfficherPackController(AfficherPackController afficherPackController) {
        this.afficherPackController = afficherPackController;
    }
    @FXML
    void retourALaListe(ActionEvent event) {
        try {
            // Charger le fichier FXML de la vue de la liste des catégories
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPack.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Accéder à la fenêtre principale
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Définir la scène sur la fenêtre principale
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            afficherAlerteErreur("Erreur lors du chargement de la liste des catégories !");
        }
    }
    private void afficherAlerteErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
