package entities;

import java.time.LocalDate;


public class facture
{
    private int id;
    private String type;
    private LocalDate  date_debut;
    private LocalDate date_fin;
    private float prix;
    private String nom;
    private String prenom;
    private String addresse_mail;

    public facture(String type, LocalDate date_debut, LocalDate date_fin, float prix, String nom, String prenom, String addresse_mail) {
        this.type = type;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.prix = prix;
        this.nom = nom;
        this.prenom = prenom;
        this.addresse_mail = addresse_mail;
    }



    public facture(int id, String type, LocalDate date_debut, LocalDate date_fin, float prix, String nom, String prenom, String addresse_mail) {
        this.id = id;
        this.type = type;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.prix = prix;
        this.nom = nom;
        this.prenom = prenom;
        this.addresse_mail = addresse_mail;
    }

    public facture() {

    }


    public int getId(){return id;}

    public void setId(int id){this.id=id;}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(LocalDate date_debut) {
        this.date_debut = date_debut;
    }

    public LocalDate getDate_fin() { return date_fin; }

    public void setDate_fin(LocalDate date_fin) {
        this.date_fin = date_fin;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAddresse_mail() {
        return addresse_mail;
    }

    public void setAddresse_mail(String addresse_mail) {
        this.addresse_mail = addresse_mail;
    }



    @Override
    public String toString() {
        return "facture{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                ", prix=" + prix +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", addresse_mail='" + addresse_mail + '\'' +
                '}';
    }
}
