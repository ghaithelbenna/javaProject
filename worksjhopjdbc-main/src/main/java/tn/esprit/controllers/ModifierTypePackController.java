package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.typePack;
import tn.esprit.services.TypePackService;

import java.io.IOException;
import java.util.EventObject;

public class ModifierTypePackController {

    @FXML
    private TextField nouveauNomTypePackTextField;

    private typePack typePack;
    private TypePackService TypePackservice;
    private AffichageTypePackController affichageTypePackController;

    public void initData(typePack typePack, AffichageTypePackController affichageTypePackController) {
        this.typePack = typePack;
        this.affichageTypePackController = affichageTypePackController;
        nouveauNomTypePackTextField.setText(typePack.getNomTypePack());
        TypePackservice = new TypePackService();
    }

    @FXML
    void modifierTypePack(ActionEvent event) {
        String nouveauNom = nouveauNomTypePackTextField.getText();
        if (!nouveauNom.isEmpty()) {
            typePack.setNomTypePack(nouveauNom);
            TypePackservice.update(typePack);

            // Actualiser la liste affichée après modification
            affichageTypePackController.affichage();

            // Afficher une boîte de dialogue ou un message pour confirmer la modification réussie
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modification réussie");
            alert.setHeaderText(null);
            alert.setContentText("Le type pack a été modifié avec succès !");
            alert.showAndWait();
        }
    }


}
