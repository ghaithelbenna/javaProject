package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.esprit.models.Pack;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class Panier implements Initializable {

    @FXML
    private TableView<Pack> selectedPacksTableView;

    @FXML
    private TableColumn<Pack, String> nomColumn;
    @FXML
    private TableColumn<Pack, String> descriptionColumn;

    @FXML
    private TableColumn<Pack, Float> prixColumn;

    @FXML
    private TableColumn<Pack, Date> dateColumn;

    @FXML
    private TableColumn<Pack, String> typePackColumn;

    @FXML
    private TableColumn<Pack, String> disponibleColumn;

    @FXML
    private Label Montant;

    @FXML
    private Label nombre;

    // Méthode d'initialisation appelée lorsque le contrôleur est chargé
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Associer les propriétés observables aux valeurs des colonnes
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nomPack"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("descriptionPack"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        disponibleColumn.setCellValueFactory(new PropertyValueFactory<>("disponible"));
        typePackColumn.setCellValueFactory(new PropertyValueFactory<>("typePack"));
// Ajouter la colonne de bouton de suppression
        TableColumn<Pack, Boolean> deleteColumn = new TableColumn<>("Supprimer");
        deleteColumn.setCellFactory(col -> new DeleteButtonCell());
        selectedPacksTableView.getColumns().add(deleteColumn);

        // Désactiver la réorganisation des colonnes par l'utilisateur
        selectedPacksTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    // Méthode pour passer une liste de packs à afficher dans le TableView
    public void setPacksSelectionnes(List<Pack> packsSelectionnes) {
        // Effacer les éléments existants et ajouter les nouveaux packs
        selectedPacksTableView.getItems().clear();
        selectedPacksTableView.getItems().addAll(packsSelectionnes);

        // Calculer le montant total
        float montantTotal = 0;
        for (Pack pack : packsSelectionnes) {
            montantTotal += pack.getPrix();
        }
        // Afficher le montant total dans le label
        Montant.setText(String.valueOf(montantTotal));

        // Afficher le nombre de packs sélectionnés dans le label
        nombre.setText(String.valueOf(packsSelectionnes.size()));
    }
    // Méthode pour supprimer un pack
    public void deletePack(Pack pack) {
        selectedPacksTableView.getItems().remove(pack);

        // Récupérer les packs restants après suppression
        List<Pack> packsRestants = selectedPacksTableView.getItems();
        // Mettre à jour le montant total et le nombre de packs sélectionnés
        updateMontantEtNombre(packsRestants);
    }

    // Méthode pour mettre à jour le montant total et le nombre de packs sélectionnés
    private void updateMontantEtNombre(List<Pack> packs) {
        float montantTotal = 0;
        for (Pack pack : packs) {
            montantTotal += pack.getPrix();
        }
        // Afficher le montant total dans le label
        Montant.setText(String.valueOf(montantTotal));

        // Afficher le nombre de packs sélectionnés dans le label
        nombre.setText(String.valueOf(packs.size()));
    }
    private Scene previousScene;
    @FXML
    public void paiement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/paiement.fxml"));
            Parent root = loader.load();
            PaiementController paiementController = loader.getController(); // Obtenez une référence vers le contrôleur de paiement
            paiementController.setMontant(Float.parseFloat(Montant.getText())); // Passez le montant et le nombre de packs sélectionnés
            paiementController.setPacksSelectionnes(selectedPacksTableView.getItems()); // Passez également les packs sélectionnés

            previousScene = ((Node) event.getSource()).getScene(); // Conserve la référence à la scène précédente
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    // Cell for delete button
    private class DeleteButtonCell extends TableCell<Pack, Boolean> {
        private final Button deleteButton = new Button("Delete");

        DeleteButtonCell() {
            deleteButton.setOnAction(event -> {
                Pack pack = getTableView().getItems().get(getIndex());
                deletePack(pack);
            });
        }

        @Override
        protected void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(deleteButton);
            }
        }
    }



    public void RETOUR(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichageFront.fxml"));
            Parent root = loader.load();
            previousScene = ((Node) event.getSource()).getScene(); // Conserve la référence à la scène précédente

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
