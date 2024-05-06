package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.esprit.models.typePack;
import tn.esprit.services.TypePackService;

import java.io.IOException;
import java.util.Optional;

public class ItempkController {

    @FXML
    private HBox itemD; // Mise à jour du nom du champ

    @FXML
    private Label nomTypePack;

    private TypePackService typePackService;
    private typePack typePack;
    private AffichageTypePackController affichageTypePackController;

    public ItempkController() {
        this.typePackService = new TypePackService();
    }

    public void initData(typePack typePack) {
        this.typePack = typePack;
        nomTypePack.setText(typePack.getNomTypePack());
    }

    @FXML
    void afficherDetails(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Détails du TypePack");
        alert.setHeaderText(null);
        alert.setContentText("Nom : " + typePack.getNomTypePack());
        alert.showAndWait();
    }

    @FXML
    void modifierTypePack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifierTypePack.fxml"));
            Parent root = loader.load();

            // Obtenez le contrôleur ModifierTypePackController
            ModifierTypePackController controller = loader.getController();

            // Initialisez les données dans le contrôleur
            controller.initData(typePack, affichageTypePackController);

            // Accédez au parent du HBox (itemCC)
            Scene currentScene = itemD.getScene();
            Parent parent = currentScene.getRoot();

            // Remplacez le contenu actuel par le contenu de la page de modification
            ((AnchorPane) parent).getChildren().setAll(root);

        } catch (IOException e) {
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
    void supprimerTypePack(ActionEvent event) {
        // Vérifier s'il existe des enregistrements de pack liés à ce type de pack
        boolean packExists = typePackService.packExistsForTypePack(typePack);

        if (packExists) {
            // Afficher une alerte pour informer l'utilisateur que des packs sont liés à ce type de pack
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Suppression impossible");
            alert.setHeaderText(null);
            alert.setContentText("Des packs sont associés à ce type de pack. Veuillez d'abord supprimer les packs associés.");
            alert.showAndWait();
        } else {
            // Si aucun pack n'est associé, procéder à la suppression
            boolean deleted = typePackService.delete(typePack);
            if (deleted) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Suppression réussie");
                alert.setHeaderText(null);
                alert.setContentText("Le type pack a été supprimé avec succès !");
                alert.showAndWait();
                // Rafraîchir automatiquement la liste des types de pack après la suppression
                affichageTypePackController.affichage();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Erreur lors de la suppression du type pack !");
                alert.showAndWait();
            }
        }
    }

    public void setAffichageTypePackController(AffichageTypePackController affichageTypePackController) {
        this.affichageTypePackController = affichageTypePackController;
    }
}
