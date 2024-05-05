package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.esprit.models.Pack;
import tn.esprit.services.ServicePack;

import java.io.File;
import java.io.IOException;

public class ItemPController {

    @FXML
    private HBox itemC;

    @FXML
    private Label nomPack;

    @FXML
    private Label descriptionPack;

    @FXML
    private Label prix;

    @FXML
    private Label date;

    @FXML
    private ImageView imageView;

    @FXML
    private Label disponible;
    @FXML
    private Label typePack;
    private Pack pack;
    private AfficherPackController afficherPackController;

    public void initData(Pack pack, AfficherPackController afficherPackController) {
        this.pack = pack;
        this.afficherPackController = afficherPackController;

        // Afficher les informations du pack dans les labels correspondants
        typePack.setText(pack.getTypePack().getNomTypePack());
        nomPack.setText(pack.getNomPack());
        descriptionPack.setText(pack.getDescriptionPack());
        prix.setText(String.valueOf(pack.getPrix()));
        date.setText(String.valueOf(pack.getDate()));
        disponible.setText(pack.isDisponible() ? "Disponible" : "Non disponible");

        // Charger l'image du pack s'il y en a une
        if (pack.getImage() != null && !pack.getImage().isEmpty()) {
            File file = new File(pack.getImage());
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);
            }
        }
    }

    @FXML
    public void afficherDetails(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Détails du pack");
        alert.setHeaderText(null);
        alert.setContentText("Nom: " + pack.getNomPack() + "\n"
                + "Description: " + pack.getDescriptionPack() + "\n"
                + "Prix: " + pack.getPrix() + "\n"
                + "Date: " + pack.getDate() + "\n"
                + "Disponible: " + (pack.isDisponible() ? "Oui" : "Non"));
        alert.showAndWait();
    }

    @FXML
    void modifierPack(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page de modification
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierPack.fxml"));
            AnchorPane root = loader.load();

            // Obtenez le contrôleur ModifierPackController
            ModifierPackController controller = loader.getController();

            // Initialisez les données dans le contrôleur
            controller.initData(pack, afficherPackController);

            // Accédez au parent de l'AnchorPane (itemC)
            Scene currentScene = itemC.getScene();
            Parent parent = currentScene.getRoot();

            // Remplacez le contenu actuel par le contenu de la page de modification
            ((AnchorPane) parent).getChildren().setAll(root);

        } catch (IOException e) {
            e.printStackTrace();
            afficherAlerteErreur("Erreur lors du chargement de la page de modification !");
        }
    }



    private void afficherAlerteErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    void supprimerPack(ActionEvent event) {
        ServicePack servicePack = new ServicePack();
        boolean deleted = servicePack.delete(pack);
        if (deleted) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Suppression réussie");
            alert.setHeaderText(null);
            alert.setContentText("Le pack a été supprimé avec succès !");
            alert.showAndWait();

            // Rafraîchir l'affichage dans AfficherPackController après suppression
            afficherPackController.affichage();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de la suppression du pack !");
            alert.showAndWait();
        }
    }
}
