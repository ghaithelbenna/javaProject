package tn.esprit.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import tn.esprit.services.StatistiquePacks;

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
}
