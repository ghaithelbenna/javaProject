package tn.esprit.controllers;

import tn.esprit.models.Commentaire;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import tn.esprit.models.Post;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import tn.esprit.services.ServiceCommentaire;
import tn.esprit.services.ServicePost;

public class CommentaireController {

    @FXML
    private TableView<Commentaire> tableViewCommentaires;

    @FXML
    private TableColumn<Commentaire, Integer> colId;
    @FXML
    private TableColumn<Commentaire, Integer> colPostId;
    @FXML
    private TableColumn<Commentaire, String> colCommentaire;
    @FXML
    private TableColumn<Commentaire, String> colDate;
    @FXML
    private TableColumn<?, ?> colActions;
    @FXML
    private Text name;

    @FXML
    private AnchorPane pane;

    @FXML
    private TextArea taCommentaire;
    private int selectedPostId;

    public void setData(int postId,String postName){
        this.selectedPostId = postId;
        name.setText(postName);
        colId.setCellValueFactory(new PropertyValueFactory<>("idcommentaire"));
        colPostId.setCellValueFactory(new PropertyValueFactory<>("post_id"));
        colCommentaire.setCellValueFactory(new PropertyValueFactory<>("commentaire"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date_c"));

        chargerCommentaires();
    }




    private void chargerCommentaires() {


            System.out.println(selectedPostId);
            ServiceCommentaire serviceCommentaire = new ServiceCommentaire();
            List<Commentaire> commentaires = serviceCommentaire.listerCommentairesParPost(selectedPostId);
            System.out.println(commentaires);
            tableViewCommentaires.setItems(FXCollections.observableArrayList(commentaires));

    }

    @FXML
    private void btnDel(ActionEvent event){
        ServiceCommentaire serviceCommentaire = new ServiceCommentaire();
        Commentaire selectedCommentaire = tableViewCommentaires.getSelectionModel().getSelectedItem();
        if (selectedCommentaire != null) {
            serviceCommentaire.supprimer(selectedCommentaire.getIdcommentaire());
            chargerCommentaires();
        }
    }

    public void navigateBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLPost.fxml"));
        Parent root = loader.load();
        pane.getChildren().clear();
        pane.getChildren().setAll(root);
    }
    /*@FXML
    private void btnModif(ActionEvent event) {
        ServiceCommentaire serviceCommentaire = new ServiceCommentaire();

        Commentaire selectedCommentaire = tableViewCommentaires.getSelectionModel().getSelectedItem();
        if (selectedCommentaire != null) {
            // Mettre à jour les attributs de la publication avec les valeurs saisies
            selectedCommentaire.setCommentaire(taCommentaire.getText());
            selectedCommentaire.setDate_c(java.sql.Date.valueOf(tfDate.getValue()));

            selectedCommentaire.getCommentaire();
            // Appeler la méthode de modification du service
            serviceCommentaire.modifier(selectedCommentaire);

            // Rafraîchir le TableView
            chargerCommentaires();

            // Effacer les champs de saisie
            taCommentaire.clear();
            tfPostId.clear();

        }

    }*/
    public void setPane(AnchorPane pane){
        this.pane=pane;
    }
}

