package tn.esprit.models;

import java.time.LocalDateTime;
public class programme {
    int id_prog;
    int id_categorie;
    String nomCategorie,description_programme; // Nouveau champ pour stocker le nom de la catégorie
    String image;
    private LocalDateTime duree;
    double prix;
    boolean disponible;

    public programme(int id_prog, int id_categorie, String image, LocalDateTime duree, double prix, String description_programme, boolean disponible) {
        this.id_prog = id_prog;
        this.id_categorie = id_categorie;
        this.image = image;
        this.duree = duree;
        this.prix = prix;
        this.description_programme = description_programme;
        this.disponible = disponible;
    }

    // Autres méthodes existantes...

    public String getNomCategorie() {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    public programme() {
    }

    public int getId_prog() {
        return id_prog;
    }

    public void setId_prog(int id_prog) {
        this.id_prog = id_prog;
    }

    public int getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(int id_categorie) {
        this.id_categorie = id_categorie;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription_programme() {
        return description_programme;
    }

    public void setDescription_programme(String description_programme) {
        this.description_programme = description_programme;
    }

    public LocalDateTime getDuree() {
        return duree;
    }

    public void setDuree(LocalDateTime duree) {
        this.duree = duree;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "programme{" +
                "id_prog=" + id_prog +
                ", id_categorie=" + id_categorie +
                ", image='" + image + '\'' +
                ", description_programme='" + description_programme + '\'' +
                ", duree=" + duree +
                ", prix=" + prix +
                ", disponible=" + disponible +
                '}';
    }
}
