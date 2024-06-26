package tn.esprit.services;

import tn.esprit.models.User;

import java.util.List;

public interface IService<T> {

    void add(T t);
    void update (T t, int id);

    void delete(int id);

    List<T> getAll();

    T getById();

}
