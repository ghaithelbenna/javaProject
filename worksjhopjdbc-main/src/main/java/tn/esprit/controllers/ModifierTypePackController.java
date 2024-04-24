package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.typePack;
import tn.esprit.services.ServiceTypePack;

import java.io.IOException;
import java.util.EventObject;

public class ModifierTypePackController {

    @FXML
    private TextField nouveauNomTypePackTextField;

    private typePack typePack;
    private ServiceTypePack serviceTypePack;
    private AffichageTypePackController affichageTypePackController;

    public void initData(typePack typePack, AffichageTypePackController afficherTypePackController) {
        this.typePack = typePack;
        this.affichageTypePackController = afficherTypePackController;
        nouveauNomTypePackTextField.setText(typePack.getNomTypePack());
        serviceTypePack = new ServiceTypePack();
    }

    @FXML
    void modifierTypePack(ActionEvent event) {
        String nouveauNom = nouveauNomTypePackTextField.getText();
        if (!nouveauNom.isEmpty()) {
            typePack.setNomTypePack(nouveauNom);
            serviceTypePack.update(typePack);
            affichageTypePackController.affichage();
        }
    }



    public void setAffichageTypePackController(AffichageTypePackController affichageTypePackController) {
        this.affichageTypePackController = affichageTypePackController;
    }
    private void navigate(String fxmlFile, EventObject event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void afficherPack(ActionEvent actionEvent) throws IOException {
        navigate("/AfficherPack.fxml", actionEvent);
    }


    public void ajouterPack(ActionEvent actionEvent)throws IOException {
        navigate("/Pack.fxml", actionEvent);
    }

    public void affichercategorie(ActionEvent actionEvent) throws IOException{
        navigate("/affichageCategorie.fxml", actionEvent);
    }

    public void ajoutercategorie(ActionEvent actionEvent)throws IOException{
        navigate("/AjoutCategorie.fxml", actionEvent);
    }

    public void affichertypePack(ActionEvent actionEvent) throws IOException{
        navigate("/AffichageTypePack.fxml", actionEvent);
    }

    public void ajoutertypePack(ActionEvent actionEvent) throws IOException{
        navigate("/ajoutTypePack.fxml", actionEvent);
    }
}
