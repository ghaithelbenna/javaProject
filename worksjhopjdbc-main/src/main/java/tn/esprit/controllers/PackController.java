package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import tn.esprit.models.Pack;
import tn.esprit.services.ServicePack;
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
        // Validator pour le champ NomT (commence par une majuscule)
        NomT.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("^\\p{Lu}.*$") ? change : null));

        // Validator pour le champ DescriptionT (commence par une majuscule)
        DescriptionT.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("^\\p{Lu}.*$") ? change : null));

        // Validator pour le champ PrixT (format numérique positif)
        PrixT.setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), null, c -> {
            if (c.getText().matches("\\d*\\.?\\d*")) {
                return c;
            } else {
                return null;
            }
        }));

        // Validator pour le champ PrixT (positif)
        PrixT.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                PrixT.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
    @FXML
    void ajouter(ActionEvent event) {
        // Vérifier si une image a été sélectionnée
        if (image == null) {
            System.out.println("Veuillez sélectionner une image.");
            return; // Arrêter la méthode si aucune image n'a été sélectionnée
        }

        LocalDate localDate = DateT.getValue();
        if (localDate == null) {
            showAlert("Date invalide", "Veuillez sélectionner une date.");
            return; // Stop method execution if no date is selected
        }
        Date sqlDate = Date.valueOf(localDate);

        typePack selectedTypePack = TypePackComboBox.getValue();
        if (selectedTypePack == null) {
            System.out.println("Veuillez sélectionner un type de pack.");
            return; // Arrêter la méthode si aucun type de pack n'a été sélectionné
        }

        // Ajouter le pack en utilisant les données saisies dans les champs, l'image sélectionnée et le type de pack
        Pack pack = new Pack(
                selectedTypePack.getId_typepack(),
                selectedTypePack,
                NomT.getText(),
                DescriptionT.getText(),
                Double.parseDouble(PrixT.getText()),
                sqlDate,
                image,
                DisponibleT.isSelected()
        );
        sp.add(pack);

        // Afficher un message de confirmation
        System.out.println("Pack ajouté avec succès : " + pack.getNomPack());
        System.out.println("Description : " + pack.getDescriptionPack());
        System.out.println("Prix : " + pack.getPrix());
        System.out.println("Date : " + pack.getDate().toString());
        System.out.println("Image : " + pack.getImage());
        System.out.println("Type de pack : " + pack.getTypePack().getNomTypePack());
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
    private boolean validateInputFields() {
        String nomPack = NomT.getText();
        String descriptionPack = DescriptionT.getText();
        String prixText = PrixT.getText();
        LocalDate selectedDate = DateT.getValue();

        // Check if any of the fields are empty
        if (nomPack.isEmpty() || descriptionPack.isEmpty() || prixText.isEmpty() || selectedDate == null) {
            showAlert("Champs requis", "Veuillez remplir tous les champs.");
            return false;
        }

        // Validate price format
        try {
            double prix = Double.parseDouble(prixText);
            if (prix <= 0) {
                showAlert("Format de prix incorrect", "Le prix doit être supérieur à zéro.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Format de prix incorrect", "Veuillez saisir un prix numérique valide.");
            return false;
        }

        // Validate if text fields start with an uppercase letter
        if (!Character.isUpperCase(nomPack.charAt(0)) || !Character.isUpperCase(descriptionPack.charAt(0))) {
            showAlert("Format de texte incorrect", "Les champs doivent commencer par une lettre majuscule.");
            return false;
        }

        // Validate selected date
        LocalDate today = LocalDate.now();
        if (selectedDate.isBefore(today)) {
            showAlert("Date invalide", "La date doit être aujourd'hui ou ultérieure.");
            return false;
        }

        // All fields are valid
        return true;
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
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
}
