package tn.esprit.services;
import tn.esprit.utils.MyDataBase;

import entities.evenement;


import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class ServiceEvenement  {
    private Connection cnx;

    public ServiceEvenement(){
        cnx = MyDataBase.getInstance().getCnx();
    }


    private boolean isAlphabetical(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return str.chars().allMatch(Character::isLetter);
    }

    public void ajouter(evenement evenement) throws SQLException {
        if (!isAlphabetical(evenement.getNom_event()) ) {
            System.out.println("le nom de l'evenement doit etre de type alphabetic ");
            return;
        }
        if (!isAlphabetical(evenement.getType_event())) {
            System.out.println("le type de l'evenement doit etre de type alphabetic ");
            return;
        }
        if (evenement.getDuree_event() == null || evenement.getDuree_event().isEmpty()) {
            System.out.println("la duree de l'evenement ne doit pas etre vide  ");
            return;
        }
        if (evenement.getDate_event() == null ) {
            System.out.println("la date de l'evenement ne doit pas etre vide  ");
            return;
        }
        String req ="INSERT INTO evenement (nom_event, type_event, duree_event, date_event) VALUES (?, ?, ?, ?)";
        PreparedStatement ste = cnx.prepareStatement(req);
        ste.setString(1, evenement.getNom_event());
        ste.setString(2, evenement.getType_event());
        ste.setString(3, evenement.getDuree_event());
        ste.setString(4, String.valueOf(evenement.getDate_event()));
        ste.executeUpdate();
    }


    public void modifier(evenement evenement) throws SQLException {

        if (evenement.getNom_event() == null || evenement.getNom_event().isEmpty()) {
            // Handle invalid nom_event input
            return;
        }
        if (evenement.getType_event() == null || evenement.getType_event().isEmpty()) {
            // Handle invalid type_event input
            return;
        }
        if (evenement.getDuree_event() == null || evenement.getDuree_event().isEmpty()) {
            // Handle invalid duree_event input
            return;
        }
        if (evenement.getDate_event() == null ) {
            // Handle invalid date_event input
            return;
        }
        if (evenement.getId_Event() <= 0) {
            // Handle invalid id_Event input
            return;
        }
        String req = "UPDATE evenement SET nom_event=?, type_event=?, duree_event=?, date_event=? WHERE id_event=?";
        PreparedStatement pre = cnx.prepareStatement(req);
        pre.setString(1, evenement.getNom_event());
        pre.setString(2, evenement.getType_event());
        pre.setString(3, evenement.getDuree_event());
        pre.setString(4, String.valueOf(evenement.getDate_event()));
        pre.setInt(5, evenement.getId_Event());
        pre.executeUpdate();
    }


    public void supprimer(evenement evenement) throws SQLException {
        String req = "DELETE FROM evenement WHERE id_event=?";
        PreparedStatement pre = cnx.prepareStatement(req);
        pre.setInt(1, evenement.getId_Event());
        pre.executeUpdate();
    }


    public List<evenement> afficher() throws SQLException {
        String req = "SELECT * FROM evenement";
        Statement ste = cnx.createStatement();
        ResultSet res = ste.executeQuery(req);
        List<evenement> list = new ArrayList<>();
        while (res.next()) {
            evenement e = new evenement();
            e.setId_Event(res.getInt(1));
            e.setNom_event(res.getString("nom_event"));
            e.setType_event(res.getString("type_event"));
            e.setDuree_event(res.getString("duree_event"));
           // e.setDate_event(res.getString("date_event"));
            e.setDate_event(res.getDate("date_event").toLocalDate());
            list.add(e);
        }
        return list;
    }

    public List<evenement> triNom() throws SQLException {
        List<evenement> list1 = new ArrayList<>();
        List<evenement> list2 = afficher();
        list1 = list2.stream().sorted(Comparator.comparing(evenement::getNom_event)).collect(Collectors.toList());
        return list1;
    }

    public List<evenement> triDate() throws SQLException {
        List<evenement> list1 = new ArrayList<>();
        List<evenement> list2 = afficher();
        list1 = list2.stream().sorted(Comparator.comparing(evenement::getDate_event)).collect(Collectors.toList());
        return list1;
    }
}
