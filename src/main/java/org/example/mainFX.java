package org.example;

import controller.ajouterVoituresController;
import controller.ajouterAgencesController;
import controller.affichageFrontController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import controller.afficherVoituresController;

import java.io.IOException;
public class mainFX extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charger le fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterVoitures.fxml"));
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterAgences.fxml"));
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherFront.fxml"));
        Parent root = loader.load();

        // Obtenir une référence au contrôleur associé
        ajouterVoituresController ajouterController = loader.getController();
        //ajouterAgencesController ajouterController = loader.getController();
        //affichageFrontController ajouterController = loader.getController();

        // Transmettre la référence à la Stage au contrôleur
        ajouterController.setPrimaryStage(primaryStage);

        // Définir la scène
        Scene scene = new Scene(root);
        primaryStage.setTitle("Gérer Voitures");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}