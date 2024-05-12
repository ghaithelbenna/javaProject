package tn.esprit.models;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class reservation {

    private int id;
    private LocalDateTime datedebut;
    private LocalDateTime datefin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateDebut() {
        return datedebut;
    }

    public void setDateDebut(LocalDateTime datedebut) {
        this.datedebut = datedebut;
    }

    public LocalDateTime getDateFin() {
        return datefin;
    }

    public void setDateFin(LocalDateTime datefin) {
        this.datefin = datefin;
    }

    public reservation() {

    }

    public reservation(int id, LocalDateTime datedebut, LocalDateTime datefin) {
        this.id = id;
        this.datedebut = datedebut;
        this.datefin = datefin;

    }

    public reservation(LocalDateTime datedebut, LocalDateTime datefin) {

        this.datedebut = datedebut;
        this.datefin = datefin;

    }

    @Override
    public String toString() {
        return "reservation{" +
                "id=" + id +
                ", datedebut=" + datedebut +
                ", datefin=" + datefin +
                '}';
    }
}
