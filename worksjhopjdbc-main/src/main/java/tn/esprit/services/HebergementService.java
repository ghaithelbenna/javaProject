package tn.esprit.services;

import tn.esprit.models.hebergement;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HebergementService  {
    private Connection cnx;

    public HebergementService() {
        cnx = MyDataBase.getInstance().getCnx();
    }


    public void ajouter(hebergement hebergement) {
        try {


            // Proceed with database insertion
            String req = "INSERT INTO hebergement (type_hebergement, emplacement, Nbr_etoile, description, prix, name) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ste = cnx.prepareStatement(req);
            ste.setString(1, hebergement.getType_hebergement());
            ste.setString(2, hebergement.getEmplacement());
            ste.setInt(3, hebergement.getNbr_etoile());
            ste.setString(4, hebergement.getDescription());
            ste.setFloat(5, hebergement.getPrix());
            ste.setString(6, hebergement.getName());
            ste.executeUpdate();
            System.out.println("Hébergement ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'hébergement : " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Prix invalide. Le prix doit être un nombre.");
        }
    }

    // Method to check if a string contains only alphabetical characters
    private boolean isAlphabetical(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return str.chars().allMatch(Character::isLetter);
    }


    // Method to check if a string can be parsed into a floating-point number
    private boolean isNumericFloat(float value) {
        return value > 0;
    }



    public void modifier(hebergement hebergement) throws SQLException {
        String req = "UPDATE hebergement SET type_hebergement=?, emplacement=?, Nbr_etoile=?, description=?, prix=?, name=? WHERE id=?";
        PreparedStatement pre = cnx.prepareStatement(req);
        pre.setString(1, hebergement.getType_hebergement());
        pre.setString(2, hebergement.getEmplacement());
        pre.setInt(3, hebergement.getNbr_etoile());
        pre.setString(4, hebergement.getDescription());
        pre.setFloat(5, hebergement.getPrix());
        pre.setString(6, hebergement.getName());
        pre.setInt(7, hebergement.getId());
        pre.executeUpdate();
        System.out.println("Hébergement modifié avec succès !");
    }


    public void supprimer(hebergement hebergement) throws SQLException {
        String req = "DELETE FROM hebergement WHERE id=?";
        PreparedStatement pre = cnx.prepareStatement(req);
        pre.setInt(1, hebergement.getId());
        pre.executeUpdate();
        System.out.println("Hébergement supprimé avec succès !");
    }


    public List<hebergement> afficher() throws SQLException {
        String req = "SELECT * FROM hebergement";
        int hotelcount=0;
        Statement ste = cnx.createStatement();
        ResultSet res = ste.executeQuery(req);
        List<hebergement> list = new ArrayList<>();
        while (res.next()) {
            hebergement h = new hebergement();
            h.setId(res.getInt("id"));
            h.setType_hebergement(res.getString("type_hebergement"));
            h.setEmplacement(res.getString("emplacement"));
            h.setNbr_etoile(res.getInt("Nbr_etoile"));
            h.setDescription(res.getString("description"));
            h.setPrix(res.getFloat("prix"));
            h.setName(res.getString("name"));
            list.add(h);
            if(res.getString("type_hebergement")=="hotel")
            { hotelcount=hotelcount+1;}

        }
        return list;
    }





    public int affichercounthotel() throws SQLException {
        String req = "SELECT * FROM hebergement";
        int hotelcount=0;

        Statement ste = cnx.createStatement();
        ResultSet res = ste.executeQuery(req);

        while (res.next()) {
            if(res.getString("type_hebergement").equals("hotel"))
            { hotelcount=hotelcount+1;}
        }
        return hotelcount;
    }


    public int affichercountairbnb() throws SQLException {
        String req = "SELECT * FROM hebergement";
        int airbnbcount=0;

        Statement ste = cnx.createStatement();
        ResultSet res = ste.executeQuery(req);

        while (res.next()) {
            if(res.getString("type_hebergement").equals("airbnb"))
            { airbnbcount=airbnbcount+1;}
        }
        return airbnbcount;
    }


    public int affichercountmaisonhaute() throws SQLException {
        String req = "SELECT * FROM hebergement";
        int maisoncount = 0;

        Statement ste = cnx.createStatement();
        ResultSet res = ste.executeQuery(req);

        while (res.next()) {
            if (res.getString("type_hebergement").equals("maison d'haute")) {
                maisoncount++;
            }
        }
        return maisoncount;
    }









}
