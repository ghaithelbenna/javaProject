package tn.esprit.interfaces;

import java.util.ArrayList;

public interface IService<T> {

    void add (T pack);
    ArrayList<T> getAll();
    void update(T pack);
    boolean delete(T pack);
    // Autres méthodes spécifiques à Pack

}