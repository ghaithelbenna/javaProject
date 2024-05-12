package tn.esprit.controllers;

import tn.esprit.models.hebergement;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class cardController {

    @FXML
    private Label description;

    @FXML
    private Label emplacement_label;

    @FXML
    private Label name_label;

    @FXML
    private Label nbr_etoiles_label;

    @FXML
    private Label prix_label;


    private hebergement hebergementData;


    public void SetData(hebergement hebergementData)
    {
        name_label.setText(hebergementData.getName());
        nbr_etoiles_label.setText(String.valueOf(hebergementData.getNbr_etoile()));
        prix_label.setText(String.valueOf(hebergementData.getPrix()));
        emplacement_label.setText(hebergementData.getEmplacement());
        description.setText(hebergementData.getDescription());
    }

}
