package org.example;

import controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
public class mainFX extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charger le fichier FXML
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterVoitures.fxml"));
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterAgences.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherFront.fxml"));
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterReservations.fxml"));
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/smsSender.fxml"));
        Parent root = loader.load();

        // Obtenir une référence au contrôleur associé
        //ajouterVoituresController ajouterController = loader.getController();
        //ajouterAgencesController ajouterController = loader.getController();
        affichageFrontController ajouterController = loader.getController();
        //ajouterReservationController ajouterController = loader.getController();
        //SMSController ajouterController = loader.getController();

        // Transmettre la référence à la Stage au contrôleur
        ajouterController.setPrimaryStage(primaryStage);
        //ajouterController.setStage(primaryStage); // Passer la référence de la fenêtre principale

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