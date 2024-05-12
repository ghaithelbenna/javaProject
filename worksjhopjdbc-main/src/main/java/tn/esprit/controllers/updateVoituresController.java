package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.voiture;
import tn.esprit.services.voitureServices;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class updateVoituresController {

    private voiture voiture; // Voiture à modifier
    private afficherVoituresController afficherController;

    private final voitureServices vs = new voitureServices();

    @FXML
    private Button modifierVoiture;

    @FXML
    private Button versAffichage;

    @FXML
    private Button delete;

    @FXML
    private TextField immatriculation;

    @FXML
    private TextField modele;

    @FXML
    private ComboBox<Integer> nbr_places;


    @FXML
    private TextField couleur;

    @FXML
    private TextField prixdelocation;

    @FXML
    private ImageView imageView;

    @FXML
    private ComboBox<String> agenceComboBox;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize() {
        // Récupérer les noms des agences depuis la table locationvoitures
        List<String> nomsAgences = vs.getNomsAgences();

        // Ajouter les noms des agences au ComboBox
        agenceComboBox.getItems().addAll(nomsAgences);
    }

    public void setImage(Image image) {
        imageView.setImage(image);
    }

    public void setAfficherController(afficherVoituresController afficherController) {
        this.afficherController = afficherController;
    }

    public void setVoiture(voiture voiture) {
        this.voiture = voiture;
        // Pré-remplir les champs de modification avec les données de la voiture
        immatriculation.setText(voiture.getImmatriculation());
        modele.setText(voiture.getModele());
        nbr_places.getSelectionModel().select(voiture.getNbr_places());
        ObservableList<Integer> placesOptions = FXCollections.observableArrayList(2, 5, 7); // Ajoutez les options souhaitées
        nbr_places.setItems(placesOptions);
        nbr_places.setValue(voiture.getNbr_places());
        couleur.setText(voiture.getCouleur());
        prixdelocation.setText(String.valueOf(voiture.getPrixdelocation()));
        // Afficher l'image
        Image image = new Image(new File(voiture.getImagePath()).toURI().toString());
        setImage(image);

        // Sélectionner le nom de l'agence dans la ComboBox
        agenceComboBox.setValue(voiture.getNom_agence());
    }

    @FXML
    public void modifierVoiture() {
        if (!validateInput()) {
            return;
        }

        // Récupérer les nouvelles valeurs des champs de modification
        String nouveauImmatriculation = immatriculation.getText();
        String nouveauModele = modele.getText();
        Integer nouveauNbrPlaces = nbr_places.getValue();
        String nouvelleCouleur = couleur.getText();
        String nomAgence = agenceComboBox.getValue();
        int nouveauPrixdelocation = Integer.parseInt(prixdelocation.getText());


        // Vérification réussie, mettre à jour les détails de la voiture
        if (nouveauNbrPlaces != null) {
            voiture.setImmatriculation(nouveauImmatriculation);
            voiture.setModele(nouveauModele);
            voiture.setNbr_places(nouveauNbrPlaces.intValue());
            voiture.setCouleur(nouvelleCouleur);
            voiture.setPrixdelocation(nouveauPrixdelocation);

            // Appeler le service ou le gestionnaire approprié pour effectuer la modification dans la base de données ou dans la source de données
            vs.update(voiture);

            // Recharger les voitures dans la fenêtre d'affichage
            afficherController.afficherVoitures();
        } else {
            System.out.println("Aucun nombre de places sélectionné.");
        }
    }

    private boolean validateInput() {
        if (immatriculation.getText().isEmpty() || modele.getText().isEmpty() || nbr_places.getValue() == null
                || couleur.getText().isEmpty() || prixdelocation.getText().isEmpty() || imageView.getImage() == null
                || agenceComboBox.getValue() == null) {
            showErrorAlert("Veuillez remplir tous les champs !");
            return false;
        }

        // Validate immatriculation format
        String immatriculationText = immatriculation.getText();
        String immatriculationPattern = "\\d{3}\\sTUNIS\\s\\d{4}";

        if (!immatriculationText.matches(immatriculationPattern)) {
            showErrorAlert("L'immatriculation doit être au format comme cet exemple: 111 TUNIS 2222");
            return false;
        }

        try {
            String selectedValue = prixdelocation.getText();
            Integer.parseInt(selectedValue);

            String selectedNbrPlaces = nbr_places.getValue().toString();
            Integer.parseInt(selectedNbrPlaces);

            // Add more validations if needed...

            return true;
        } catch (NumberFormatException e) {
            showErrorAlert("Le prix de location doit être un nombre valide !");
            return false;
        }
    }

    @FXML
    void delete(ActionEvent event) {
        // Appeler le service pour supprimer la voiture
        vs.delete(voiture);

        // Charger le fichier FXML de la nouvelle scène
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherVoitures.fxml"));
            Parent root = loader.load();

            // Récupérer la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacer le contenu de la scène actuelle par le nouveau Parent
            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void versAffichage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherVoitures.fxml"));
            Parent root = loader.load();

            // Récupérer la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacer le contenu de la scène actuelle par le nouveau Parent
            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void downloadButtonUpdateClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            // Mettre à jour l'image avec la nouvelle image sélectionnée
            setImage(new Image(selectedFile.toURI().toString()));

            // Mettre à jour le chemin de l'image dans l'objet voiture si nécessaire
            voiture.setImagePath(selectedFile.getPath());
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void naviguerVersAgences(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherAgences.fxml"));
            Parent root = loader.load();

            // Récupérer la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacer le contenu de la scène actuelle par le nouveau Parent
            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
