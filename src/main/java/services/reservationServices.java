package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.reservation;
import util.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class reservationServices implements IserviceReservation<reservation> {

    private Connection cnx;

    public reservationServices() {
        cnx = DataSource.getInstance().getCnx();
    }

    @Override
    public void ajouterReservation(reservation reservation) throws SQLException {
        PreparedStatement pst = cnx.prepareStatement("INSERT INTO reservation (`datedebut`, `datefin`) VALUES (?, ?)");
        pst.setObject(1, reservation.getDateDebut());
        pst.setObject(2, reservation.getDateFin());
        pst.executeUpdate();
        System.out.println("Réservation ajoutée avec succès");
    }

    public boolean existeReservationDateDebut(LocalDate dateDebut) throws SQLException {
        PreparedStatement pst = cnx.prepareStatement("SELECT COUNT(*) FROM reservation WHERE datedebut = ?");
        pst.setObject(1, dateDebut);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            int count = rs.getInt(1);
            return count > 0;
        }
        return false;
    }
}