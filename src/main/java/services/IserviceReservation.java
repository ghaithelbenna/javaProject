package services;

import java.sql.SQLException;
import java.util.List;

public interface IserviceReservation <R>{

    void ajouterReservation (R r) throws SQLException;


}
