package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.Pack;
import tn.esprit.services.ServicePack;
import javafx.scene.control.ComboBox;
import tn.esprit.models.typePack;
import tn.esprit.services.ServiceTypePack;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.EventObject;

public class PackController {

    @FXML
    private TextField NomT;

    @FXML
    private TextField DescriptionT;

    @FXML
    private TextField PrixT;

    @FXML
    private DatePicker DateT;

    @FXML
    private Button ImageT;
    @FXML

    private ImageView imageView;
    @FXML
    private CheckBox DisponibleT;
    @FXML
    private ComboBox<typePack> TypePackComboBox;
    private String nomPack;
    private String descriptionPack;
    private double prix;
    private String image;
    private boolean disponible;
    private final ServicePack sp = new ServicePack();
    // Service pour récupérer les types de pack
    private final ServiceTypePack serviceTypePack = new ServiceTypePack();
    @FXML
    void initialize() throws SQLException {
        // Initialiser la ComboBox avec les types de pack disponibles
        initTypePackComboBox();
    }

    private void initTypePackComboBox() throws SQLException {
        // Récupérer la liste des types de pack depuis le service
        ObservableList<typePack> typePacks = FXCollections.observableArrayList(serviceTypePack.getAll());

        // Ajouter les types de pack à la ComboBox
        TypePackComboBox.setItems(typePacks);
    }
    @FXML
    void ajouter(ActionEvent event) {
        // Vérifier si une image a été sélectionnée
        if (image == null) {
            System.out.println("Veuillez sélectionner une image.");
            return; // Arrêter la méthode si aucune image n'a été sélectionnée
        }

        // Convertir la date sélectionnée en format SQL Date
        LocalDate localDate = DateT.getValue();
        Date sqlDate = Date.valueOf(localDate);

        typePack selectedTypePack = TypePackComboBox.getValue();
        if (selectedTypePack == null) {
            System.out.println("Veuillez sélectionner un type de pack.");
            return; // Arrêter la méthode si aucun type de pack n'a été sélectionné
        }

        // Ajouter le pack en utilisant les données saisies dans les champs, l'image sélectionnée et le type de pack
        sp.add(new Pack(selectedTypePack.getId_typepack(), 1, NomT.getText(), DescriptionT.getText(), Double.parseDouble(PrixT.getText()), sqlDate, image, DisponibleT.isSelected()));

        // Afficher un message de confirmation
        System.out.println("Pack ajouté avec succès : " + NomT.getText());
        System.out.println("Description : " + DescriptionT.getText());
        System.out.println("Prix : " + PrixT.getText());
        System.out.println("Date : " + sqlDate.toString());
        System.out.println("Image : " + image);
        System.out.println("Type de pack : " + selectedTypePack.getNomTypePack());

    }



    @FXML
    void handleImageSelection(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
        );

        Stage stage = (Stage) NomT.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            // Mettre à jour la variable image avec le chemin d'accès absolu du fichier sélectionné
            image = selectedFile.getAbsolutePath();

            // Faire quelque chose avec le fichier sélectionné, par exemple l'enregistrer ou l'afficher
            System.out.println("Image sélectionnée : " + image);
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
