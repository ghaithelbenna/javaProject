package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.voiture;
import services.voitureServices;

import java.io.File;
import java.io.IOException;

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
    private TextField nbr_places;

    @FXML
    private TextField couleur;

    @FXML
    private TextField prixdelocation;

    @FXML
    private ImageView imageView;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
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
        nbr_places.setText(String.valueOf(voiture.getNbr_places()));
        couleur.setText(voiture.getCouleur());
        prixdelocation.setText(String.valueOf(voiture.getPrixdelocation()));
        setImage(new Image(new File(voiture.getImagePath()).toURI().toString()));
    }

    @FXML
    public void modifierVoiture() {
        // Récupérer les nouvelles valeurs des champs de modification
        String nouveauImmatriculation = immatriculation.getText();
        String nouveauModele = modele.getText();
        int nouveauNbrPlaces = Integer.parseInt(nbr_places.getText());
        String nouvelleCouleur = couleur.getText();
        int nouveauPrixdelocation = Integer.parseInt(prixdelocation.getText());

        // Mettre à jour la voiture avec les nouvelles valeurs
        voiture.setImmatriculation(nouveauImmatriculation);
        voiture.setModele(nouveauModele);
        voiture.setNbr_places(nouveauNbrPlaces);
        voiture.setCouleur(nouvelleCouleur);
        voiture.setPrixdelocation(nouveauPrixdelocation);

        // Appeler le service ou le gestionnaire approprié pour effectuer la modification dans la base de données ou dans la source de données
        vs.update(voiture);

        // Recharger les voitures dans la fenêtre d'affichage
        afficherController.afficherVoitures();
    }

    @FXML
    void delete(ActionEvent event) {
        // Appeler le service pour supprimer la voiture
        vs.delete(voiture);

        // Fermer la fenêtre de modification
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        // Ouvrir la fenêtre d'affichage
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherVoitures.fxml"));
            Parent root = loader.load();

            Stage afficherStage = new Stage();
            afficherStage.setTitle("Afficher Voitures");
            afficherStage.setScene(new Scene(root));
            afficherStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void versAffichage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherVoitures.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Afficher Voitures");
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre d'affichage actuelle
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
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


}
