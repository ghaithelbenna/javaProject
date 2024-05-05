package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.models.typePack;
import tn.esprit.services.TypePackService;

import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.List;
import java.util.ResourceBundle;

public class AffichageTypePackController implements Initializable {

    @FXML
    private VBox pkItems;

    private final TypePackService typePackService = new TypePackService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        affichage();
    }

    public void affichage() {
        pkItems.getChildren().clear();

        List<typePack> typePacks = typePackService.getAll();

        for (typePack typePack : typePacks) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Itempk.fxml"));
                HBox node = loader.load();

                ItempkController itemController = loader.getController();
                itemController.initData(typePack);

                // Passer la référence à AffichageTypePackController à ItempkController
                itemController.setAffichageTypePackController(this);

                pkItems.getChildren().add(node);
            } catch (IOException ex) {
                ex.printStackTrace(); // Affiche l'erreur dans la console
            }
        }
    }

    @FXML
    void refreshData() {
        affichage();
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
