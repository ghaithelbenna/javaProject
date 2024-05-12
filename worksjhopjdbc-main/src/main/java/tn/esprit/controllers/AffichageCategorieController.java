package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.models.categorie;
import tn.esprit.services.CategorieService;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.EventObject;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AffichageCategorieController implements Initializable {

    @FXML
    private VBox pkItemsCC;



    @FXML
    private TextField searchField;

    private final CategorieService categorieService = new CategorieService();
    private ObservableList<categorie> categories = FXCollections.observableArrayList();

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialisez le ComboBox et chargez les catégories initialement

        loadCategories();

        // Ajoutez un écouteur au champ de recherche
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterCategories(newValue));
    }

    private void loadCategories() {
        categories.setAll(categorieService.getAllCategories());
        affichage();
    }

    public  void affichage() {
        pkItemsCC.getChildren().clear();
        for (categorie categorie : categories) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ItemC.fxml"));
                HBox node = loader.load();

                ItemCController itemController = loader.getController();
                itemController.initData(categorie, this); // Pass 'this' as instance of AffichageCategorieController

                // Pass reference to AffichageCategorieController to ItemCController
                itemController.setAffichageCategorieController(this);

                pkItemsCC.getChildren().add(node);
            } catch (IOException ex) {
                ex.printStackTrace(); // Print error to console
            }
        }
    }

    private void filterCategories(String keyword) {
        ObservableList<categorie> filteredCategories = FXCollections.observableArrayList();
        if (keyword == null || keyword.trim().isEmpty()) {
            filteredCategories.addAll(categories);
        } else {
            filteredCategories.addAll(categories.stream()
                    .filter(cat -> cat.getNomcategorie().toLowerCase().contains(keyword.toLowerCase()))
                    .collect(Collectors.toList()));
        }
        pkItemsCC.getChildren().clear();
        for (categorie categorie : filteredCategories) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ItemC.fxml"));
                HBox node = loader.load();

                ItemCController itemController = loader.getController();
                itemController.initData(categorie, this); // Pass 'this' as instance of AffichageCategorieController

                // Pass reference to AffichageCategorieController to ItemCController
                itemController.setAffichageCategorieController(this);

                pkItemsCC.getChildren().add(node);
            } catch (IOException ex) {
                ex.printStackTrace(); // Print error to console
            }
        }
    }

    // Add event handler for sortComboBox selection change


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



    public void affichercategorie(ActionEvent actionEvent) throws IOException {
        navigate("/affichageCategorie.fxml", actionEvent);
    }
    @FXML
    public void ajouterCategorie(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutCategorie.fxml"));
            Parent root = loader.load();
            previousScene = ((Node) event.getSource()).getScene();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void affichertypePack(ActionEvent actionEvent) throws IOException {
        navigate("/AffichageTypePack.fxml", actionEvent);
    }



    private Scene previousScene;

    public void RETOUR(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
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
