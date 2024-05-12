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
import tn.esprit.models.categorie;
import tn.esprit.services.CategorieService;

import java.io.IOException;
import java.util.EventObject;

public class AjoutCategorieController {

    @FXML
    private TextField nomCategorieTextField;

    private final CategorieService categorieService = new CategorieService();

    @FXML
    void ajouterCategorie(ActionEvent event) {
        String nomCategorie = nomCategorieTextField.getText();

        // Validate input
        if (!validateNomCategorie(nomCategorie)) {
            return; // Exit method if validation fails
        }

        // Creating a new instance of the category with the specified name
        categorie nouvelleCategorie = new categorie();
        nouvelleCategorie.setNomcategorie(nomCategorie);

        // Adding the new category to the database
        boolean ajoutReussi = categorieService.addCategorie(nouvelleCategorie);

        // Show appropriate alert based on the result of adding the category
        if (ajoutReussi) {
            showAlert(Alert.AlertType.INFORMATION, "Ajout réussi", "La catégorie a été ajoutée avec succès !");
            // Clear the text field after adding
            nomCategorieTextField.clear();
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de l'ajout de la catégorie !");
        }
    }

    private boolean validateNomCategorie(String nomCategorie) {
        if (nomCategorie.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Champ vide", "Veuillez saisir le nom de la catégorie !");
            return false;
        }
        if (nomCategorie.length() < 4) {
            showAlert(Alert.AlertType.ERROR, "Nom de Catégorie", "Le nom de la catégorie doit avoir au moins 4 caractères !");
            return false;
        }
        if (!nomCategorie.matches(".*[a-zA-Z].*")) {
            showAlert(Alert.AlertType.ERROR, "Nom de Catégorie", "Le nom de la catégorie doit contenir au moins une lettre !");
            return false;
        }
        return true;
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void navigate(String fxmlFile, EventObject event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void afficherPack(ActionEvent actionEvent) throws IOException {
        navigate("/AfficherPack.fxml", actionEvent);
    }


    public void ajouterPack(ActionEvent actionEvent)throws IOException {
        navigate("/Pack.fxml", actionEvent);
    }

    public void affichercategorie(ActionEvent actionEvent) throws IOException{
        navigate("/affichageCategorie.fxml", actionEvent);
    }

    public void ajoutercategorie(ActionEvent actionEvent)throws IOException{
        navigate("/AjoutCategorie.fxml", actionEvent);
    }

    public void affichertypePack(ActionEvent actionEvent) throws IOException{
        navigate("/AffichageTypePack.fxml", actionEvent);
    }

    public void ajoutertypePack(ActionEvent actionEvent) throws IOException{
        navigate("/ajoutTypePack.fxml", actionEvent);
    }
    private Scene previousScene;
    public void retourALaListe(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/affichageCategorie.fxml"));
            Parent root = loader.load();
            previousScene = ((Node) event.getSource()).getScene(); // Conserve la référence à la scène précédente

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
