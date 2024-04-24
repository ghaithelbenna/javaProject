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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.programme;
import tn.esprit.services.ProgrammeService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.EventObject;

public class AjoutProgrammeController {

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TextField dureeTextField;

    @FXML
    private TextField prixTextField;

    @FXML
    private ChoiceBox<programme> categorieChoiceBox;

    @FXML
    private Button ajouterImage;

    @FXML
    private Button ajouterProgrammeButton;

    @FXML
    private ImageView imageView;

    private String selectedImagePath;

    private ProgrammeService programmeService;

    public AjoutProgrammeController() {
        this.programmeService = new ProgrammeService();
    }

    @FXML
    void initialize() throws SQLException {
        initCategorieChoiceBox();
    }

    private void initCategorieChoiceBox() throws SQLException {
        ObservableList<programme> programmes = FXCollections.observableArrayList(programmeService.getAll());
        categorieChoiceBox.setItems(programmes);
    }

    @FXML
    void ajouterImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
        );

        Stage stage = (Stage) descriptionTextArea.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            selectedImagePath = selectedFile.getAbsolutePath();
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
        }
    }

    @FXML
    void ajouterProgramme(ActionEvent event) {
        String description = descriptionTextArea.getText();
        String dureeStr = dureeTextField.getText();
        double prix = Double.parseDouble(prixTextField.getText());
        programme categorie = categorieChoiceBox.getValue();

        programme nouveauProgramme = new programme(0, categorie.getId_categorie(), selectedImagePath,
                null, prix, description, true); // Remplacer null par la bonne valeur pour la duree
        programmeService.ajouterProgramme(nouveauProgramme);
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
