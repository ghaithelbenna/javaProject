package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.typePack;
import tn.esprit.services.ServiceTypePack;

import java.io.IOException;
import java.sql.SQLException;
import java.util.EventObject;

public class AjoutTypePackController {

    @FXML
    private TextField nomTypePackTextField;

    // Instanciation du service pour gérer les types de pack
    private final ServiceTypePack serviceTypePack = new ServiceTypePack();

    @FXML
    void ajouter(ActionEvent event) {
        // Récupérer le nom du type de pack saisi par l'utilisateur
        String nomTypePack = nomTypePackTextField.getText();

        // Vérifier si le champ de texte n'est pas vide
        if (!nomTypePack.isEmpty()) {
            // Créer un nouvel objet typePack avec le nom saisi
            typePack nouveauTypePack = new typePack();
            nouveauTypePack.setNomTypePack(nomTypePack);

            try {
                // Ajouter le nouveau type de pack à la base de données
                serviceTypePack.ajouterTypePack(nouveauTypePack);

                // Vous pouvez également ajouter un message de confirmation ici
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérer les erreurs liées à l'ajout du type de pack à la base de données
            }
        } else {
            // Afficher un message d'erreur ou effectuer une autre action appropriée si le champ est vide
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
