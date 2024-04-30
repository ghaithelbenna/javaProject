package tn.esprit.models;

import java.util.Date;

public class Pack {

    private int id;
    private String nomPack;
    private String descriptionPack;
    private double prix;
    private Date date;
    private String image;
    private boolean disponible;
    private typePack typePack;

    public Pack(int id, typePack typePack, String nomPack, String descriptionPack, double prix, Date date, String image, boolean disponible) {
        this.id = id;
        this.typePack = typePack;
        this.nomPack = nomPack;
        this.descriptionPack = descriptionPack;
        this.prix = prix;
        this.date = date;
        this.image = image;
        this.disponible = disponible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public typePack getTypePack() {
        return typePack;
    }

    public void setTypePack(typePack typePack) {
        this.typePack = typePack;
    }

    public String getNomPack() {
        return nomPack;
    }

    public void setNomPack(String nomPack) {
        this.nomPack = nomPack;
    }

    public String getDescriptionPack() {
        return descriptionPack;
    }

    public void setDescriptionPack(String descriptionPack) {
        this.descriptionPack = descriptionPack;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Pack{" +
                "id=" + id +
                ", nomPack='" + nomPack + '\'' +
                ", descriptionPack='" + descriptionPack + '\'' +
                ", prix=" + prix +
                ", date=" + date +
                ", image='" + image + '\'' +
                ", disponible=" + disponible +
                ", typePack=" + typePack +
                '}';
    }
}