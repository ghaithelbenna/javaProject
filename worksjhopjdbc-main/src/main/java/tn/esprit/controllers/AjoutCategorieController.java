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

        if (!nomCategorie.isEmpty()) {
            // Créer une nouvelle instance de la catégorie avec le nom spécifié
            categorie nouvelleCategorie = new categorie();
            nouvelleCategorie.setNomcategorie(nomCategorie);

            // Ajouter la nouvelle catégorie à la base de données
            boolean ajoutReussi = categorieService.addCategorie(nouvelleCategorie);

            if (ajoutReussi) {
                // Afficher une confirmation
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ajout réussi");
                alert.setHeaderText(null);
                alert.setContentText("La catégorie a été ajoutée avec succès !");
                alert.showAndWait();
            } else {
                // Afficher une erreur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur s'est produite lors de l'ajout de la catégorie !");
                alert.showAndWait();
            }

            // Effacer le champ de texte après l'ajout
            nomCategorieTextField.clear();
        } else {
            // Afficher une erreur si le champ de texte est vide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Champ vide");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir le nom de la catégorie !");
            alert.showAndWait();
        }
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
}
