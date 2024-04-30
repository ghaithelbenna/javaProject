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
import java.sql.SQLException;
import java.util.EventObject;

public class AjoutTypePackController {

    @FXML
    private TextField nomTypePackTextField;

    // Instanciation du service pour gérer les types de pack
    private final TypePackService serviceTypePack = new TypePackService();


    @FXML
    void ajouter(ActionEvent event) {
        String nomTypePack = nomTypePackTextField.getText();
        if (!nomTypePack.isEmpty()) {
            typePack nouveauTypePack = new typePack();
            nouveauTypePack.setNomTypePack(nomTypePack);
            serviceTypePack.add(nouveauTypePack);
            afficherMessageConfirmation("Type de pack ajouté avec succès !");
        } else {
            afficherMessageErreur("Veuillez saisir un nom pour le type de pack.");
        }
    }

    private void afficherMessageConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherMessageErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
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
