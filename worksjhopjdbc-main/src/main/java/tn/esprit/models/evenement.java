package entities;


import java.time.LocalDate;

public class evenement {
    private int id_event;
    private String nom_event;
    private String type_event;
    private String duree_event;
    private LocalDate date_event;

    public evenement() {

    }

    public String getNom_event() {
        return nom_event;
    }

    public void setNom_event(String nom_event) {
        this.nom_event = nom_event;
    }

    public String getType_event() {
        return type_event;
    }

    public void setType_event(String type_event) {
        this.type_event = type_event;
    }

    public String getDuree_event() {
        return duree_event;
    }

    public void setDuree_event(String duree_event) {
        this.duree_event = duree_event;
    }

    public LocalDate getDate_event() {
        return date_event;
    }

    public void setDate_event(LocalDate date_event) {
        this.date_event = date_event;
    }
    public int getId_Event() {
        return id_event;
    }

    public void setId_Event(int id) {
        this.id_event = id;
    }


    public evenement(int id_event, String nom_event, String type_event, String duree_event, LocalDate date_event) {
        this.id_event = id_event;
        this.nom_event = nom_event;
        this.type_event = type_event;
        this.duree_event = duree_event;
        this.date_event = date_event;
    }

    public evenement(String nom_event, String type_event, String duree_event, LocalDate date_event) {
        this.nom_event = nom_event;
        this.type_event = type_event;
        this.duree_event = duree_event;
        this.date_event = date_event;
    }

    @Override
    public String toString() {
        return "evenement{" +

                ", nom_event='" + nom_event + '\'' +
                ", destination='" + type_event + '\'' +
                ", duree_event='" + duree_event + '\'' +
                ", date_event=" + date_event +
                '}';
    }

    //public void setDate_event(String dateEvent) {}
}
