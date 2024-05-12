package tn.esprit.controllers;

import entities.evenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.services.ServiceEvenement;

import java.sql.SQLException;
import java.time.LocalDate;

public class ModifyController {

    @FXML
    private TextField DureeEvent;

    @FXML
    private TextField NomEvent;

    @FXML
    private TextField TypeEvent;

    @FXML
    private DatePicker datePick;

    private evenement selectedEvenement;
    private ServiceEvenement serviceEvenement;

    public void initData(evenement selectedEvenement, ServiceEvenement serviceEvenement) {
        this.selectedEvenement = selectedEvenement;
        this.serviceEvenement = serviceEvenement;

        NomEvent.setText(selectedEvenement.getNom_event());
        TypeEvent.setText(selectedEvenement.getType_event());
        DureeEvent.setText(selectedEvenement.getDuree_event());
        datePick.setValue(selectedEvenement.getDate_event());
    }

    @FXML
    void SaveBtn(ActionEvent event) {
        selectedEvenement.setNom_event(NomEvent.getText());
        selectedEvenement.setType_event(TypeEvent.getText());
        selectedEvenement.setDuree_event(DureeEvent.getText());
        selectedEvenement.setDate_event(datePick.getValue());



        try {
            serviceEvenement.modifier(selectedEvenement);
            Stage stage = (Stage) NomEvent.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
