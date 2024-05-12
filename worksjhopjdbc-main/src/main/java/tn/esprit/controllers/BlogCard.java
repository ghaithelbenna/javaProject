package tn.esprit.controllers;

import tn.esprit.models.Post;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BlogCard {

    @FXML
    private Label body;
    @FXML
    private Text likes;


    @FXML
    private Pane pane;
    @FXML
    private Button likebtn;

    @FXML
    private AnchorPane main;

    @FXML
    private Label nbcmnts;

    @FXML
    private Label nblikes;

    @FXML
    private Text title;

    @FXML
    private Label username;

    @FXML
    private VBox vbox;

    @FXML
    private Button vp;
    @FXML
    private ImageView image;
    private Post post;


    public void setData(Post post){
        Image postImage = new Image("file:///C:/Users/Mrrae/Desktop/Pijava1/gestion_Raed/src/main/resources/blogImages/" + post.getImage_p());
        image.setImage(postImage);
        title.setText(post.getHashtag());
        body.setText(post.getDescription());
        this.post=post;
        likes.setText(String.valueOf(post.getLikes()));
    }

    public void setPane(Pane pane){
        this.pane=pane;
    }

    public void showDetails(ActionEvent actionEvent){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/blogDetails.fxml"));
            Parent fxml = loader.load();
            BlogDetails controller = loader.getController();
            controller.setData(post);
            controller.setPane(pane);
            pane.getChildren().clear();
            pane.getChildren().setAll(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
