package tn.esprit.models;
import java.util.Date;

public class Post {
    private int idPost;
    private String description;
    private String image_p;
    private String hashtag;
    private Date date_p;
    private int likes;

    public Post() {
    }

    public Post(int idPost, String description, String image_p, String hashtag, Date date_p,int likes) {
        this.idPost = idPost;
        this.description = description;
        this.image_p = image_p;
        this.hashtag = hashtag;
        this.date_p = date_p;
        this.likes = likes;
    }
    public Post(String description, String image_p, String hashtag, Date date_p, int likes) {
        this.description = description;
        this.image_p = image_p;
        this.hashtag = hashtag;
        this.date_p = date_p;
        this.likes = likes;
    }

   /* public Post(String description, String image, String hashtag, Date date) {

    }
*/

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_p() {
        return image_p;
    }

    public void setImage_p(String image_p) {
        this.image_p = image_p;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public Date getDate_p() {
        return date_p;
    }

    public void setDate_p(Date date_p) {
        this.date_p = date_p;
    }

    @Override
    public String toString() {
        return "Post{" +
                "idPost=" + idPost +
                ", description='" + description + '\'' +
                ", image_p='" + image_p + '\'' +
                ", hashtag='" + hashtag + '\'' +
                ", date_p=" + date_p +'\''+
                ", likes=" + likes +
                '}';
    }
}
