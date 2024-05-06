package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.agencedelocation;
import services.agenceServices;

import java.io.IOException;

public class updateAgencesController {

    private agenceServices as = new agenceServices();
    private agencedelocation agencedelocation; // Agence à modifier
    private afficherAgencesController afficherAgencesController;

    @FXML
    private TextField adresse_agence;

    @FXML
    private Button modifierAgence;

    @FXML
    private TextField nbrvoitures_dispo;

    @FXML
    private Button delete;

    @FXML
    private TextField nom_agence;

    public void setAfficherAgencesController(afficherAgencesController afficherAgencesController) {
        this.afficherAgencesController = afficherAgencesController;
    }

    public void setAgencedelocation(agencedelocation agencedelocation) {
        this.agencedelocation = agencedelocation;
        // Pré-remplir les champs de modification avec les données de l'agence
        nom_agence.setText(agencedelocation.getNom_agence());
        adresse_agence.setText(agencedelocation.getAdresse_agence());
        nbrvoitures_dispo.setText(String.valueOf(agencedelocation.getNbrvoitures_dispo()));
    }

    @FXML
    void modifierAgence(ActionEvent event) {
        // Récupérer les nouvelles valeurs des champs de modification
        String nouveauNom = nom_agence.getText();
        String nouvelleAdresse = adresse_agence.getText();
        int nouveauNbrvoitures = Integer.parseInt(nbrvoitures_dispo.getText());

        // Mettre à jour l'agence avec les nouvelles valeurs
        agencedelocation.setNom_agence(nouveauNom);
        agencedelocation.setAdresse_agence(nouvelleAdresse);
        agencedelocation.setNbrvoitures_dispo(nouveauNbrvoitures);

        // Appeler le service ou le gestionnaire approprié pour effectuer la modification dans la base de données ou dans la source de données
        as.updateAgence(agencedelocation);

        // Fermer la fenêtre de modification


        // Recharger les agences dans la fenêtre d'affichage
        afficherAgencesController.afficherAgences();
    }

    @FXML
    void deleteAgence(ActionEvent event) {
        // Appeler le service pour supprimer l'agence
        as.deleteAgence(agencedelocation);

        // Fermer la fenêtre de modification
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        // Ouvrir la fenêtre d'affichage
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherAgences.fxml"));
            Parent root = loader.load();

            Stage afficherStage = new Stage();
            afficherStage.setTitle("Afficher Agences");
            afficherStage.setScene(new Scene(root));
            afficherStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void versAffichage(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherAgences.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Afficher Agences");
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre d'affichage actuelle
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}