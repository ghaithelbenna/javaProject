package tn.esprit.services;

import tn.esprit.utils.MyDataBase;
import tn.esprit.models.Post;
import tn.esprit.utils.MyDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicePost {
    private Connection cnx;

    public ServicePost() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    public void ajouter(Post post) {
        try {
            String query = "INSERT INTO post (description, image_p, hashtag, date_p, likes) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setString(1, post.getDescription());
            pst.setString(2, post.getImage_p());
            pst.setString(3, post.getHashtag());
            pst.setInt(5, post.getLikes());

            // Vérifier si la date de la publication est nulle
            if (post.getDate_p() != null) {
                pst.setDate(4, new java.sql.Date(post.getDate_p().getTime()));
            } else {
                pst.setDate(4, null); // Si la date est nulle, insérer null dans la base de données
            }

            pst.executeUpdate();
            System.out.println("Post ajouté avec succès !");
        } catch (SQLException ex) {
            Logger.getLogger(ServicePost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void modifier(Post post) {
        try {
            String query = "UPDATE post SET description=?, image_p=?, hashtag=?, date_p=? WHERE idPost=?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setString(1, post.getDescription());
            pst.setString(2, post.getImage_p());
            pst.setString(3, post.getHashtag());
            pst.setDate(4, new java.sql.Date(post.getDate_p().getTime()));
            pst.setInt(5, post.getIdPost());
            pst.executeUpdate();
            System.out.println("Post modifié avec succès !");
        } catch (SQLException ex) {
            Logger.getLogger(ServicePost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void supprimer(int idPost) {
        try {
            String query = "DELETE FROM post WHERE idPost=?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, idPost);
            pst.executeUpdate();
            System.out.println("Post supprimé avec succès !");
        } catch (SQLException ex) {
            Logger.getLogger(ServicePost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Post> listerPosts() {
        List<Post> posts = new ArrayList<>();
        try {
            String query = "SELECT * FROM post";

            PreparedStatement pst = cnx.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int idPost = rs.getInt("idPost");
                String description = rs.getString("description");
                String image_p = rs.getString("image_p");
                String hashtag = rs.getString("hashtag");
                Date date_p = rs.getDate("date_p");
                int likes = rs.getInt("likes");
                Post post = new Post(idPost, description, image_p, hashtag, date_p, likes);
                posts.add(post);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicePost.class.getName()).log(Level.SEVERE, null, ex);
        }
        return posts;
    }


        public void like(Post post) {
            try {
                String query = "UPDATE post SET likes = likes + 1 WHERE idPost = ?";
                PreparedStatement pst = cnx.prepareStatement(query);
                pst.setInt(1, post.getIdPost());
                pst.executeUpdate();
                System.out.println("Like added to post with ID: " + post.getIdPost());
            } catch (SQLException ex) {
                Logger.getLogger(ServicePost.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


}
