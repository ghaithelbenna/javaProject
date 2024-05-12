package tn.esprit.controllers;

import tn.esprit.models.guide;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.services.serviceGuide;
import java.io.IOException;
import java.sql.SQLException;

public class AjoutGuideController {

    @FXML
    private TextField nom_guide;

    @FXML
    private TextField prenom_guide;

    @FXML
    private TextField age_guide;

    @FXML
    private TextField sexe_guide;

    private final serviceGuide serviceGuide;

    public AjoutGuideController() {
        serviceGuide = new serviceGuide();
    }

    @FXML
    void SaveBtn(ActionEvent event) {
        String nom = nom_guide.getText();
        String prenom = prenom_guide.getText();
        String age = age_guide.getText();
        String sexe = sexe_guide.getText();


        if (nom.isEmpty() || prenom.isEmpty()   || age.isEmpty() || sexe.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Ajout impossible", "Veuillez remplir tous les champs.");
            return;
        }

        if (nom.length() > 10) {
            showAlert(Alert.AlertType.WARNING, "Ajout impossible", "Le champ Nom ne doit pas dépasser 10 caractères.");
            return;
        }

        if (!nom.matches("^[a-zA-Z]+$")) {
            showAlert(Alert.AlertType.WARNING, "Ajout impossible", "Le champ Nom  doit contenir uniquement des caractères alphabétiques.");
            return;
        }

        if (prenom.length() > 10) {
            showAlert(Alert.AlertType.WARNING, "Ajout impossible", "Le champ Prenom ne doit pas dépasser 10 caractères.");
            return;
        }

        if (!prenom.matches("^[a-zA-Z]+$")) {
            showAlert(Alert.AlertType.WARNING, "Ajout impossible", "Le champ Prenom doit contenir uniquement des caractères alphabétiques.");
            return;
        }

        if (sexe.length() > 10) {
            showAlert(Alert.AlertType.WARNING, "Ajout impossible", "Le champ sexe ne doit pas dépasser 10 caractères.");
            return;
        }
        if (!sexe.matches("^[a-zA-Z]+$")) {
            showAlert(Alert.AlertType.WARNING, "Ajout impossible", "Le champ sexe doit contenir uniquement des caractères alphabétiques.");
            return;
        }

        guide newGuide = new guide(nom, prenom, age, sexe);

        try {
            serviceGuide.ajouter(newGuide);
            System.out.println("Guide ajouté avec succès !");

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            Parent root = FXMLLoader.load(getClass().getResource("/ListGuide.fxml"));
            Scene scene = new Scene(root);
            Stage listStage = new Stage();
            listStage.setScene(scene);
            listStage.setTitle("Liste des Guides");
            listStage.show();
        } catch (SQLException | IOException e) {
            System.err.println("Erreur lors de l'ajout du guide : " + e.getMessage());
        }
    }

    private void clearFields() {
        nom_guide.clear();
        prenom_guide.clear();
        age_guide.clear();
        sexe_guide.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
