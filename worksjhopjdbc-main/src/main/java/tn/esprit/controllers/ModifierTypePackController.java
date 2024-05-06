package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.typePack;
import tn.esprit.services.TypePackService;

import java.io.IOException;
import java.util.EventObject;

public class ModifierTypePackController {

    @FXML
    private TextField nouveauNomTypePackTextField;

    private typePack typePack;

    private TypePackService typePackService;
    private AffichageTypePackController affichageTypePackController;


    public void initData(typePack typePack, AffichageTypePackController affichageTypePackController) {
        this.typePack = typePack;
        this.affichageTypePackController = affichageTypePackController;
        nouveauNomTypePackTextField.setText(typePack.getNomTypePack());
        typePackService = new TypePackService();
    }

    @FXML
    void modifierTypePack(ActionEvent event) {
        String nouveauNom = nouveauNomTypePackTextField.getText();
        if (!nouveauNom.isEmpty()) {
            typePack.setNomTypePack(nouveauNom);
            typePackService.update(typePack);

            // Actualiser la liste affichée après modification
            affichageTypePackController.affichage();

            // Afficher une boîte de dialogue ou un message pour confirmer la modification réussie
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modification réussie");
            alert.setHeaderText(null);
            alert.setContentText("Le type pack a été modifié avec succès !");
            alert.showAndWait();
        }
    }

    @FXML
    void retourALaListe(ActionEvent event) {
        try {
            // Charger le fichier FXML de la vue de la liste des catégories
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichageCategorie.fxml"));
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
