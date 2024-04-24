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
import tn.esprit.services.CategorieService;
import tn.esprit.models.categorie;

import java.io.IOException;
import java.util.EventObject;

public class ModifierCategorieController {

    @FXML
    private TextField nomCategorieTextField;

    private categorie categorie;

    private final CategorieService categorieService = new CategorieService();

    public void initData(categorie categorie) {
        this.categorie = categorie;
        nomCategorieTextField.setText(categorie.getNomcategorie());
    }

    @FXML
    void modifierCategorie(ActionEvent actionEvent) {
        String nouveauNomCategorie = nomCategorieTextField.getText();

        if (nouveauNomCategorie.isEmpty()) {
            afficherAlerteErreur("Veuillez saisir un nouveau nom de catégorie !");
        } else {
            categorie.setNomcategorie(nouveauNomCategorie);
            boolean modificationReussie = categorieService.updateCategorie(categorie);
            if (modificationReussie) {
                afficherAlerteInformation("La catégorie a été modifiée avec succès !");
            } else {
                afficherAlerteErreur("Erreur lors de la modification de la catégorie !");
            }
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
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
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
}
