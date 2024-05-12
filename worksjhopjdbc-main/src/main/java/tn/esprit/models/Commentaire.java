package tn.esprit.models;


import java.util.Date;
public class Commentaire {
    private int idcommentaire;
    private int post_id;
    private String commentaire;
    private Date date_c;

    public Commentaire() {
    }

    public Commentaire(int idcommentaire, int post_id, String commentaire, Date date_c) {
        this.idcommentaire = idcommentaire;
        this.post_id = post_id;
        this.commentaire = commentaire;
        this.date_c = date_c;
    }


    public Commentaire(int post_id, String commentaire, Date date_c) {
        this.post_id = post_id;
        this.commentaire = commentaire;
        this.date_c = date_c;
    }

    public int getIdcommentaire() {
        return idcommentaire;
    }

    public void setIdcommentaire(int idcommentaire) {
        this.idcommentaire = idcommentaire;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int postid) {
        this.post_id = post_id;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Date getDate_c() {
        return date_c;
    }

    public void setDate_c(Date date_c) {
        this.date_c = date_c;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "idcommentaire=" + idcommentaire +
                ", post_id=" + post_id +
                ", commentaire='" + commentaire + '\'' +
                ", date_c=" + date_c +
                '}';
    }
}
