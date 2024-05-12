package tn.esprit.controllers;

import tn.esprit.models.Commentaire;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class CommentCard {

    @FXML
    private Text comment;

    @FXML
    private Text date;

    public void setData(Commentaire commentaire){
        comment.setText(commentaire.getCommentaire());
        date.setText(String.valueOf(commentaire.getDate_c()));
    }
}
