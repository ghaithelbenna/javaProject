package tn.esprit.services;

import java.sql.SQLException;
import java.util.List;

public interface IservicesAgence <A>{

    void ajouterAgence (A a) throws SQLException;
    void updateAgence (A a ) ;
    void deleteAgence (A a) ;
    List<A> getAllAgences() ;
    A getById(int id_voiture);
}
