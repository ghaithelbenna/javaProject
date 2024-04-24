package tn.esprit.models;

public class categorie {
    int id_categorie 	;
	String	nomcategorie;

    public categorie() {
    }

    public categorie(int id_categorie, String nomcategorie) {
        this.id_categorie = id_categorie;
        this.nomcategorie = nomcategorie;
    }

    public int getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(int id_categorie) {
        this.id_categorie = id_categorie;
    }

    public String getNomcategorie() {
        return nomcategorie;
    }

    public void setNomcategorie(String nomcategorie) {
        this.nomcategorie = nomcategorie;
    }

    @Override
    public String toString() {
        return "categorie{" +
                "id_categorie=" + id_categorie +
                ", nomcategorie='" + nomcategorie + '\'' +
                '}';
    }
}
