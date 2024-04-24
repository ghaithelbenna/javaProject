package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import tn.esprit.models.typePack;
import tn.esprit.services.TypePackService;

import java.util.Optional;

public class ItempkController {

    @FXML
    private HBox itemD; // Mise à jour du nom du champ

    @FXML
    private Label nomTypePack;

    private TypePackService typePackService;
    private typePack typePack;

    public ItempkController() {
        this.typePackService = new TypePackService();
    }

    public void initData(typePack typePack) {
        this.typePack = typePack;
        nomTypePack.setText(typePack.getNomTypePack());
    }

    @FXML
    void afficherDetails(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Détails du TypePack");
        alert.setHeaderText(null);
        alert.setContentText("Nom : " + typePack.getNomTypePack());
        alert.showAndWait();
    }

    @FXML
    void modifierTypePack(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Modification du TypePack");
        dialog.setHeaderText("Modifier le nom du TypePack");
        dialog.setContentText("Nouveau nom :");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(nouveauNom -> {
            typePack.setNomTypePack(nouveauNom);
            typePackService.update(typePack);
            // Afficher une boîte de dialogue ou un message pour confirmer la modification réussie
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modification réussie");
            alert.setHeaderText(null);
            alert.setContentText("Le type pack a été modifié avec succès !");
            alert.showAndWait();
        });
    }


    @FXML
    void supprimerTypePack(ActionEvent event) {
        // Vérifier s'il existe des enregistrements de pack liés à ce type de pack
        boolean packExists = typePackService.packExistsForTypePack(typePack);

        if (packExists) {
            // Afficher une alerte pour informer l'utilisateur que des packs sont liés à ce type de pack
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Suppression impossible");
            alert.setHeaderText(null);
            alert.setContentText("Des packs sont associés à ce type de pack. Veuillez d'abord supprimer les packs associés.");
            alert.showAndWait();
        } else {
            // Si aucun pack n'est associé, procéder à la suppression
            boolean deleted = typePackService.delete(typePack);
            if (deleted) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Suppression réussie");
                alert.setHeaderText(null);
                alert.setContentText("Le type pack a été supprimé avec succès !");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Erreur lors de la suppression du type pack !");
                alert.showAndWait();
            }
        }
    }


    public void setAffichageTypePackController(AffichageTypePackController affichageTypePackController) {
    }
}
