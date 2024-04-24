package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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

    private categorie categorie;

    private final CategorieService categorieService = new CategorieService();

    public void initData(categorie categorie) {
        this.categorie = categorie;
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
            // Vérifier s'il existe des enregistrements liés à cette catégorie
            boolean enregistrementsLiesExistants = categorieService.supprimerCategorie(categorie);

            if (enregistrementsLiesExistants) {
                // Afficher une alerte pour informer l'utilisateur que des enregistrements sont liés à cette catégorie
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Suppression impossible");
                alert.setHeaderText(null);
                alert.setContentText("Des enregistrements sont associés à cette catégorie. Veuillez d'abord supprimer les enregistrements associés.");
                alert.showAndWait();
            } else {
                // Si aucun enregistrement n'est associé, procéder à la suppression
                boolean suppressionReussie = categorieService.supprimerCategorie(categorie);
                if (suppressionReussie) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Suppression réussie");
                    alert.setHeaderText(null);
                    alert.setContentText("La catégorie a été supprimée avec succès !");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Erreur lors de la suppression de la catégorie !");
                    alert.showAndWait();
                }
            }
        }
    }


    @FXML
    void modifierCategorie(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierCategorie.fxml"));
        Parent modifierCategorieParent;
        try {
            modifierCategorieParent = loader.load();
        } catch (IOException e) {
            afficherAlerteErreur("Erreur lors du chargement de la page de modification !");
            return;
        }

        ModifierCategorieController modifierCategorieController = loader.getController();
        modifierCategorieController.initData(categorie);

        Scene modifierCategorieScene = new Scene(modifierCategorieParent);
        Stage stage = new Stage();
        stage.setTitle("Modifier la catégorie");
        stage.setScene(modifierCategorieScene);
        stage.show();
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

    public void setAffichageCategorieController(AffichageCategorieController affichageCategorieController) {
    }
}
