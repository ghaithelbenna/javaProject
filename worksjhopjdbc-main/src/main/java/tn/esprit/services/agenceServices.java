package tn.esprit.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.utils.MyDataBase;
import tn.esprit.models.agencedelocation;
import java.sql.*;
public class agenceServices{

    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;

    public agenceServices() {
        cnx = MyDataBase.getInstance().getCnx();
    }



    public void ajouterAgence(agencedelocation agencedelocation) throws SQLException {
        PreparedStatement pst = cnx.prepareStatement("INSERT INTO locationvoitures (`nom_agence`, `adresse_agence`, `nbrvoitures_dispo`) VALUES (?, ?, ?)");
        pst.setString(1, agencedelocation.getNom_agence());
        pst.setString(2, agencedelocation.getAdresse_agence());
        pst.setInt(3, agencedelocation.getNbrvoitures_dispo());
        pst.executeUpdate();
        System.out.println("Voiture ajoutée avec succès");
    }


    public void updateAgence(agencedelocation agencedelocation) {
        try {
            PreparedStatement pst = cnx.prepareStatement("UPDATE locationvoitures SET nom_agence=?, adresse_agence=?, nbrvoitures_dispo=? WHERE id_agence=?");
            pst.setString(1, agencedelocation.getNom_agence());
            pst.setString(2, agencedelocation.getAdresse_agence());
            pst.setInt(3, agencedelocation.getNbrvoitures_dispo());
            pst.setInt(4, agencedelocation.getId_agence());
            pst.executeUpdate();
            System.out.println("Agence mise à jour avec succès");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteAgence(agencedelocation agencedelocation) {
        try {
            PreparedStatement pst = cnx.prepareStatement("DELETE FROM locationvoitures WHERE id_agence=?");
            pst.setInt(1, agencedelocation.getId_agence());
            pst.executeUpdate();
            System.out.println("Agence supprimée avec succès");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<agencedelocation> getAllAgences() {
        ObservableList<agencedelocation> agencedelocations = FXCollections.observableArrayList();

        try {
            String req = "SELECT * FROM locationvoitures";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                agencedelocation agencedelocation = new agencedelocation();
                agencedelocation.setId_agence(rs.getInt("id_agence"));
                agencedelocation.setNom_agence(rs.getString("nom_agence"));
                agencedelocation.setAdresse_agence(rs.getString("adresse_agence"));
                agencedelocation.setNbrvoitures_dispo(rs.getInt("nbrvoitures_dispo"));
                agencedelocations.add(agencedelocation);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return agencedelocations;
    }



    public agencedelocation getById(int id_agence) {
        try {
            PreparedStatement pst = cnx.prepareStatement("SELECT * FROM locationvoitures WHERE id_agence=?");
            pst.setInt(1, id_agence);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                agencedelocation agencedelocation = new agencedelocation();
                agencedelocation.setId_agence(rs.getInt("id_agence"));
                agencedelocation.setNom_agence(rs.getString("nom_agence"));
                agencedelocation.setAdresse_agence(rs.getString("adresse_agence"));
                agencedelocation.setNbrvoitures_dispo(rs.getInt("nbrvoitures_dispo"));
                return agencedelocation;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return null;

    }
}
