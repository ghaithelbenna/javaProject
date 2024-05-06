package models;
import java.util.Objects;
public class voiture {
    private int id_vehicule;
    private String immatriculation;
    private String modele;
    private int nbr_places;
    private String couleur;
    private int prixdelocation;
    private String imagePath;

    private int id_agence;

    public voiture() {

    }

    public int getId_vehicule() {
        return id_vehicule;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public String getModele() {
        return modele;
    }

    public int getNbr_places() {
        return nbr_places;
    }

    public String getCouleur() {
        return couleur;
    }

    public int getPrixdelocation() {
        return prixdelocation;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getId_agence() {
        return id_agence;
    }

    public void setId_vehicule(int id_vehicule) {
        this.id_vehicule = id_vehicule;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public void setNbr_places(int nbr_places) {
        this.nbr_places = nbr_places;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public void setPrixdelocation(int prixdelocation) {
        this.prixdelocation = prixdelocation;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setId_agence(int id_agence) {
        this.id_agence = id_agence;
    }

    public voiture(int id_vehicule, String immatriculation, String modele, int nbr_places, String couleur , int prixdelocation , String imagePath) {
        this.id_vehicule = id_vehicule;
        this.immatriculation = immatriculation;
        this.modele = modele;
        this.nbr_places = nbr_places;
        this.couleur = couleur;
        this.prixdelocation = prixdelocation;
        this.imagePath = imagePath;
    }

    public voiture(String immatriculation, String modele, int nbr_places, String couleur , int prixdelocation , String imagePath) {
        this.immatriculation = immatriculation;
        this.modele = modele;
        this.nbr_places = nbr_places;
        this.couleur = couleur;
        this.prixdelocation = prixdelocation;
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "voiture{" +
                "id_vehicule=" + id_vehicule +
                ", immatriculation='" + immatriculation + '\'' +
                ", modele='" + modele + '\'' +
                ", nbr_places=" + nbr_places +
                ", couleur='" + couleur + '\'' +
                ", prixdelocation=" + prixdelocation +
                ", imagepath='" + imagePath + '\'' +
                '}';
    }
}