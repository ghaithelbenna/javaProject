package tn.esprit.services;

import tn.esprit.utils.MyDataBase;
import tn.esprit.models.Commentaire;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceCommentaire {
    private Connection cnx;

    public ServiceCommentaire() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    public void ajouter(Commentaire commentaire) {
        try {
            String query = "INSERT INTO commentaire (post_id, commentaire, date_c) VALUES (?, ?, ?)";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, commentaire.getPost_id());
            pst.setString(2, commentaire.getCommentaire());

            if (commentaire.getDate_c() != null) {
                pst.setDate(3, new java.sql.Date(commentaire.getDate_c().getTime()));
            } else {
                pst.setDate(3, null); // Si la date est nulle, insérer null dans la base de données
            }
            pst.executeUpdate();
            System.out.println("Commentaire ajouté avec succès !");
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCommentaire.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void modifier(Commentaire commentaire) {
        try {
            String query = "UPDATE commentaire SET post_id=?, commentaire=?, date_c=? WHERE idcommentaire=?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, commentaire.getPost_id());
            pst.setString(2, commentaire.getCommentaire());
            pst.setDate(3, new java.sql.Date(commentaire.getDate_c().getTime()));
            pst.setInt(4, commentaire.getIdcommentaire());
            pst.executeUpdate();
            System.out.println("Commentaire modifié avec succès !");
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCommentaire.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void supprimer(int idcommentaire) {
        try {
            String query = "DELETE FROM commentaire WHERE idcommentaire=?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, idcommentaire);
            pst.executeUpdate();
            System.out.println("Commentaire supprimé avec succès !");
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCommentaire.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Commentaire> listerCommentairesParPost(int post_id) {
        List<Commentaire> commentaires = new ArrayList<>();
        try {
            String query = "SELECT * FROM commentaire WHERE post_id=?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, post_id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int idcommentaire = rs.getInt("idcommentaire");
                String commentaireTexte = rs.getString("commentaire");
                Date date_c = rs.getDate("date_c");
                Commentaire commentaire = new Commentaire(idcommentaire, post_id, commentaireTexte, date_c);
                commentaires.add(commentaire);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCommentaire.class.getName()).log(Level.SEVERE, null, ex);
        }
        return commentaires;
    }

}
