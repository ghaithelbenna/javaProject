package tn.esprit.models;

import java.util.Date;

public class guide {
    private int id_guide;
    private String nom_guide;
    private String prenom_guide;
    private String age_guide;
    private String sexe_guide;

    public guide() {

    }
    public String getNom_guide() {
        return nom_guide;
    }

    public void setNom_guide(String nom_guide) {
        this.nom_guide = nom_guide;
    }

    public String getPrenom_guide() {
        return prenom_guide;
    }

    public void setPrenom_guide(String prenom_guide) {
        this.prenom_guide = prenom_guide;
    }

    public String getAge_guide() {
        return age_guide;
    }

    public void setAge_guide(String age_guide) {
        this.age_guide = age_guide;
    }

    public String getSexe_guide() {
        return sexe_guide;
    }

    public void setSexe_guide(String sexe_guide) {
        this.sexe_guide = sexe_guide;
    }

    public guide(int id_guide, String nom_guide, String prenom_guide, String age_guide, String sexe_guide) {
        this.id_guide = id_guide;
        this.nom_guide = nom_guide;
        this.prenom_guide = prenom_guide;
        this.age_guide = age_guide;
        this.sexe_guide = sexe_guide;
    }

    public guide(String nom_guide, String prenom_guide, String age_guide, String sexe_guide) {
        this.nom_guide = nom_guide;
        this.prenom_guide = prenom_guide;
        this.age_guide = age_guide;
        this.sexe_guide = sexe_guide;
    }

    @Override
    public String toString() {
        return "guide{" +
                "id_guide=" + id_guide +
                ", nom_guide='" + nom_guide + '\'' +
                ", prenom_guide='" + prenom_guide + '\'' +
                ", age_guide='" + age_guide + '\'' +
                ", sexe_guide=" + sexe_guide +
                '}';
    }

    public int getId_guide() {
        return id_guide;
    }

    public void setId_guide(int id_guide) {
        this.id_guide = id_guide;
    }
}
