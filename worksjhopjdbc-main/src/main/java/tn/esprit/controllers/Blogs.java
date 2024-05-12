package tn.esprit.controllers;

import tn.esprit.models.Post;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import tn.esprit.services.ServicePost;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Blogs implements Initializable {
    @FXML
    private GridPane postsGrid;
    @FXML
    private Pane mainPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int columns = 0;
        int row = 1;
        List<Post> posts=new ArrayList<>();

        ServicePost postService =new ServicePost();
        posts=postService.listerPosts();
        System.out.println(posts);

        try {
            for (int i = 0; i < posts.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/blogCard.fxml"));
                Parent fxml = fxmlLoader.load();
                BlogCard controller = fxmlLoader.getController();
                controller.setPane(mainPane);
                controller.setData(posts.get(i));
                if (columns == 2) {
                    columns = 0;
                    ++row;
                }
                postsGrid.add(fxml, columns++, row);
                GridPane.setMargin(fxml, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDetails(){

    }


}
