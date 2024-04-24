package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.controllers.CarteItemController;
import tn.esprit.models.Pack;
import tn.esprit.services.ServicePack;

import java.io.IOException;
import java.util.EventObject;
import java.util.List;

public class AffichageFrontController {

    @FXML
    private VBox vboxPacks;

    private final ServicePack servicePack = new ServicePack();

    public void initialize() {
        afficherPacks();
    }

    private void afficherPacks() {
        // Nettoyer le contenu précédent
        vboxPacks.getChildren().clear();

        // Récupérer la liste des packs depuis le service
        List<Pack> packs = servicePack.afficherListe();

        // Ajouter les cartes des packs à l'interface utilisateur
        for (Pack pack : packs) {
            try {
                // Charger la carte FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Carte.fxml"));
                AnchorPane carteNode = loader.load();

                // Récupérer le contrôleur de la carte
                CarteItemController carteController = loader.getController();

                // Initialiser les données de la carte avec le pack actuel
                carteController.initData(pack);

                // Ajouter la carte à la liste des cartes
                vboxPacks.getChildren().add(carteNode);
            } catch (IOException e) {
                e.printStackTrace();
                // Gérer l'exception IOException ici
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
