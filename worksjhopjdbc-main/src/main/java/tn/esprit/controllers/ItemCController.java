package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.models.categorie;
import tn.esprit.services.CategorieService;

import java.io.IOException;
import java.util.Optional;

public class ItemCController {

    @FXML
    private HBox itemCC;

    @FXML
    private Label nomcategorie;


    private final CategorieService categorieService = new CategorieService();
    private categorie categorie;
    private AffichageCategorieController affichageCategorieController;

    public void initData(categorie categorie, AffichageCategorieController affichageCategorieController) {
        this.categorie = categorie;
        this.affichageCategorieController = affichageCategorieController;

        nomcategorie.setText(categorie.getNomcategorie());
    }

    @FXML
    void afficherDetails(ActionEvent event) {
        // Afficher les détails de la catégorie dans une boîte de dialogue
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Détails de la catégorie");
        alert.setHeaderText(null);
        alert.setContentText("ID : " + categorie.getId_categorie() + "\n" +
                "Nom : " + categorie.getNomcategorie());
        alert.showAndWait();
    }



    @FXML
    void supprimeCategorie(ActionEvent event) {
        // Demande confirmation de suppression
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de suppression");
        confirmationAlert.setHeaderText("Supprimer la catégorie ?");
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer cette catégorie ?");

        // Attend la réponse de l'utilisateur
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Supprimer la catégorie
            boolean suppressionReussie = categorieService.supprimerCategorie(categorie);
            if (suppressionReussie) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Suppression réussie");
                alert.setHeaderText(null);
                alert.setContentText("La catégorie a été supprimée avec succès !");
                alert.showAndWait();

                // Rafraîchir les données après la suppression
                affichageCategorieController.affichage();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Erreur lors de la suppression de la catégorie !");
                alert.showAndWait();
            }
        }
    }


    public void setAffichageCategorieController(AffichageCategorieController affichageCategorieController) {
        this.affichageCategorieController = affichageCategorieController;
    }

    // Méthode modifierCategorie mise à jour avec une meilleure gestion des exceptions
    @FXML
    void modifierCategorie(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page de modification
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierCategorie.fxml"));
            Parent root = loader.load();

            // Obtenez le contrôleur ModifierCategorieController
            ModifierCategorieController controller = loader.getController();

            // Initialisez les données dans le contrôleur
            controller.initData(categorie, affichageCategorieController);

            // Accédez au parent du HBox (itemCC)
            Scene currentScene = itemCC.getScene();
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

    private void afficherAlerteInformation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modification réussie");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
