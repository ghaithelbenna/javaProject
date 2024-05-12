package tn.esprit.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import tn.esprit.models.Pack;

import java.util.List;

public class StatistiquePacks {

    private ServicePack servicePack;

    public StatistiquePacks() {
        this.servicePack = new ServicePack();
    }

    public ObservableList<PieChart.Data> generateStats() {
        // Récupérer les données sur les packs depuis votre service
        List<Pack> packs = servicePack.getAll();

        // Initialiser un compteur de packs disponibles et non disponibles
        int availablePacks = 0;
        int unavailablePacks = 0;

        // Compter le nombre de packs disponibles et non disponibles
        for (Pack pack : packs) {
            if (pack.isDisponible()) {
                availablePacks++;
            } else {
                unavailablePacks++;
            }
        }

        // Calculer le pourcentage de packs disponibles et non disponibles
        int totalPacks = packs.size();
        double percentageAvailable = (double) availablePacks / totalPacks * 500;
        double percentageUnavailable = (double) unavailablePacks / totalPacks * 500;

        // Créer un ensemble de données pour le diagramme circulaire
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        pieChartData.add(new PieChart.Data("Packs disponibles: " + String.format("%.2f", percentageAvailable) + "%", availablePacks));
        pieChartData.add(new PieChart.Data("Packs non disponibles: " + String.format("%.2f", percentageUnavailable) + "%", unavailablePacks));

        return pieChartData;
    }


}
