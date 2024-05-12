package tn.esprit.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;
import tn.esprit.services.StatistiquePacks;

import java.io.IOException;
import java.util.EventObject;

public class StatistiquesController {

    @FXML
    private PieChart pieChart;

    private StatistiquePacks statistiquePacks = new StatistiquePacks();

    @FXML
    public void initialize() {
        setData(statistiquePacks.generateStats());
    }

    public void setData(ObservableList<PieChart.Data> data) {
        pieChart.setData(data);
    }

    private void navigate(String fxmlFile, EventObject event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    public void table(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page des statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPack.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Remplacer la scène actuelle par la nouvelle scène des statistiques
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
