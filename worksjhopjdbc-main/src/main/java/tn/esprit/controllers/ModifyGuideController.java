package tn.esprit.controllers;

import tn.esprit.models.guide;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.services.serviceGuide;
import java.sql.SQLException;

public class ModifyGuideController {

    @FXML
    private TextField NomGuide;

    @FXML
    private TextField PrenomGuide;

    @FXML
    private TextField AgeGuide;

    @FXML
    private TextField SexeGuide;

    private guide selectedGuide;
    private serviceGuide serviceGuide;

    public void initData(guide selectedGuide, serviceGuide serviceGuide) {
        this.selectedGuide = selectedGuide;
        this.serviceGuide = serviceGuide;

        NomGuide.setText(selectedGuide.getNom_guide());
        PrenomGuide.setText(selectedGuide.getPrenom_guide());
        AgeGuide.setText(selectedGuide.getAge_guide());
        SexeGuide.setText(selectedGuide.getSexe_guide());
    }

    @FXML
    void SaveBtn(ActionEvent event) {
        selectedGuide.setNom_guide(NomGuide.getText());
        selectedGuide.setPrenom_guide(PrenomGuide.getText());
        selectedGuide.setAge_guide(AgeGuide.getText());
        selectedGuide.setSexe_guide(SexeGuide.getText());

        try {
            serviceGuide.modifier(selectedGuide);
            Stage stage = (Stage) NomGuide.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
