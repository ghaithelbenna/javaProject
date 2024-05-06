package services;

import java.util.List;
import java.sql.SQLException;

public interface Iservices<T> {

    void ajouterVoiture (T t) throws SQLException;
    void update (T t ) ;
    void delete (T t) ;
    List<T> getAll() ;
    T getById(int id_voiture);




}
