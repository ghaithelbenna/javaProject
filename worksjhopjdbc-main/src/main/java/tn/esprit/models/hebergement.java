package tn.esprit.models;

public class hebergement {
    private int id;
    private String type_hebergement;
    private String emplacement;
    private int Nbr_etoile;
    private String description;
    private float prix;
    private String name;

    public hebergement() {
    }

    public hebergement(String type_hebergement, String emplacement, int nbr_etoile, String description, float prix, String name) {
        this.type_hebergement = type_hebergement;
        this.emplacement = emplacement;
        Nbr_etoile = nbr_etoile;
        this.description = description;
        this.prix = prix;
        this.name = name;
    }

    public hebergement(int id, String type_hebergement, String emplacement, int nbr_etoile, String description, float prix, String name) {
        this.id = id;
        this.type_hebergement = type_hebergement;
        this.emplacement = emplacement;
        Nbr_etoile = nbr_etoile;
        this.description = description;
        this.prix = prix;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType_hebergement() {
        return type_hebergement;
    }

    public void setType_hebergement(String type_hebergement) {
        this.type_hebergement = type_hebergement;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }

    public int getNbr_etoile() {
        return Nbr_etoile;
    }

    public void setNbr_etoile(int nbr_etoile) {
        Nbr_etoile = nbr_etoile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "hebergement{" +
                "id=" + id +
                ", type_hebergement='" + type_hebergement + '\'' +
                ", emplacement='" + emplacement + '\'' +
                ", Nbr_etoile=" + Nbr_etoile +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", name='" + name + '\'' +
                '}';
    }
}
