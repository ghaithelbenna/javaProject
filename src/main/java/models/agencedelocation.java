package models;

public class agencedelocation {

    private int id_agence;
    private String nom_agence;
    private String adresse_agence;
    private int nbrvoitures_dispo;

    public agencedelocation() {

    }

    public agencedelocation(int id_agence, String nom_agence, String adresse_agence, int nbrvoitures_dispo) {
        this.id_agence = id_agence;
        this.nom_agence = nom_agence;
        this.adresse_agence = adresse_agence;
        this.nbrvoitures_dispo = nbrvoitures_dispo;
    }

    public agencedelocation(String nom_agence, String adresse_agence, int nbrvoitures_dispo) {
        this.nom_agence = nom_agence;
        this.adresse_agence = adresse_agence;
        this.nbrvoitures_dispo = nbrvoitures_dispo;
    }

    public int getId_agence() {
        return id_agence;
    }

    public void setId_agence(int id_agence) {
        this.id_agence = id_agence;
    }

    public String getNom_agence() {
        return nom_agence;
    }

    public void setNom_agence(String nom_agence) {
        this.nom_agence = nom_agence;
    }

    public String getAdresse_agence() {
        return adresse_agence;
    }

    public void setAdresse_agence(String adresse_agence) {
        this.adresse_agence = adresse_agence;
    }

    public int getNbrvoitures_dispo() {
        return nbrvoitures_dispo;
    }

    public void setNbrvoitures_dispo(int nbrvoitures_dispo) {
        this.nbrvoitures_dispo = nbrvoitures_dispo;
    }

    @Override
    public String toString() {
        return "AgenceDeLocation{" +
                "id_agence=" + id_agence +
                ", nom_agence='" + nom_agence + '\'' +
                ", adresse_agence='" + adresse_agence + '\'' +
                ", nbrvoitures_dispo=" + nbrvoitures_dispo +
                '}';
    }
}
