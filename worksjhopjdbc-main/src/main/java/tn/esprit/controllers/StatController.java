package tn.esprit.controllers;

import tn.esprit.models.guide;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

import java.util.List;

public class StatController {

    @FXML
    private PieChart pieChart;

    public void initialize(List<guide> guides) {
        System.out.println("Initializing StatController");

        int totalGuides = guides.size();
        int boysCount = 0;
        int girlsCount = 0;

        // Count the number of boys and girls
        for (guide guide : guides) {
            if (guide.getSexe_guide().equalsIgnoreCase("male")) {
                boysCount++;
            } else if (guide.getSexe_guide().equalsIgnoreCase("female")) {
                girlsCount++;
            }
        }

        System.out.println("Total Guides: " + totalGuides);
        System.out.println("Boys Count: " + boysCount);
        System.out.println("Girls Count: " + girlsCount);

        // Calculate percentages
        double boysPercentage = ((double) boysCount / totalGuides) * 100;
        double girlsPercentage = ((double) girlsCount / totalGuides) * 100;

        System.out.println("Boys Percentage: " + boysPercentage);
        System.out.println("Girls Percentage: " + girlsPercentage);

        // Create PieChart Data
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Boys (" + String.format("%.2f", boysPercentage) + "%)", boysCount),
                        new PieChart.Data("Girls (" + String.format("%.2f", girlsPercentage) + "%)", girlsCount)
                );

        // Add data to pie chart
        pieChart.setData(pieChartData);
    }
}
