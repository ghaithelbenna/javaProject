package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.models.Pack;
import tn.esprit.services.ServicePack;

import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherPackController implements Initializable {

    @FXML
    private VBox pnItems;

    private final ServicePack sp = new ServicePack();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        affichage();
    }

    public void affichage() {
        pnItems.getChildren().clear();

        List<Pack> lv = sp.afficherListe();

        for (Pack pack : lv) {
            try {
                FXMLLoader loader = new FXMLLoader(AfficherPackController.class.getResource("/ItemP.fxml"));
                HBox node = loader.load();

                ItemPController itemController = loader.getController();
                itemController.initData(pack, this);

                pnItems.getChildren().add(node);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
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
