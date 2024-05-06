package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.DataSource;
import models.voiture;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class voitureServices implements Iservices<voiture> {
    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;

    public voitureServices() {
        cnx = DataSource.getInstance().getCnx();
    }



    public List<String> getNomsAgences() {
        List<String> nomsAgences = new ArrayList<>();

        try {
            String query = "SELECT nom_agence FROM locationvoitures";
            PreparedStatement pst = cnx.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String nomAgence = rs.getString("nom_agence");
                nomsAgences.add(nomAgence);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nomsAgences;
    }


    @Override
    public void ajouterVoiture(voiture voiture) throws SQLException {
        PreparedStatement pst = cnx.prepareStatement("INSERT INTO vehicule (`immatriculation`, `modele`, `nbr_places`, `couleur`, `prixdelocation`, `imagePath`) VALUES (?, ?, ?, ?, ?, ?)");
        pst.setString(1, voiture.getImmatriculation());
        pst.setString(2, voiture.getModele());
        pst.setInt(3, voiture.getNbr_places());
        pst.setString(4, voiture.getCouleur());
        pst.setInt(5, voiture.getPrixdelocation());
        pst.setString(6, voiture.getImagePath());  // Ajoutez le chemin d'accès à l'image dans la requête

        pst.executeUpdate();
        System.out.println("Voiture ajoutée avec succès");
    }

    @Override
    public void update(voiture voiture) {
        try {
            PreparedStatement pst = cnx.prepareStatement("UPDATE vehicule SET immatriculation=?, modele=?, nbr_places=?, couleur=?, prixdelocation=?, imagePath=? WHERE id_vehicule=?");
            pst.setString(1, voiture.getImmatriculation());
            pst.setString(2, voiture.getModele());
            pst.setInt(3, voiture.getNbr_places());
            pst.setString(4, voiture.getCouleur());
            pst.setInt(5, voiture.getPrixdelocation());
            pst.setString(6, voiture.getImagePath()); // Ajout de la nouvelle colonne imagePath
            pst.setInt(7, voiture.getId_vehicule());
            pst.executeUpdate();
            System.out.println("Voiture mise à jour avec succès");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(voiture voiture) {
        try {
            PreparedStatement pst = cnx.prepareStatement("DELETE FROM vehicule WHERE id_vehicule=?");
            pst.setInt(1, voiture.getId_vehicule());
            pst.executeUpdate();
            System.out.println("Voiture supprimée avec succès");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<voiture> getAll() {
        ObservableList<voiture> voitures = FXCollections.observableArrayList();

        try {
            String req = "SELECT * FROM vehicule";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                voiture voiture = new voiture();
                voiture.setId_vehicule(rs.getInt("id_vehicule"));
                voiture.setImmatriculation(rs.getString("immatriculation"));
                voiture.setModele(rs.getString("modele"));
                voiture.setNbr_places(rs.getInt("nbr_places"));
                voiture.setCouleur(rs.getString("couleur"));
                voiture.setPrixdelocation(rs.getInt("prixdelocation"));
                voiture.setImagePath(rs.getString("imagePath"));
                voitures.add(voiture);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return voitures;
    }

    @Override
    public voiture getById(int id_vehicule) {
        try {
            PreparedStatement pst = cnx.prepareStatement("SELECT * FROM vehicule WHERE id_vehicule=?");
            pst.setInt(1, id_vehicule);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                voiture voiture = new voiture();
                voiture.setId_vehicule(rs.getInt("id_vehicule"));
                voiture.setImmatriculation(rs.getString("immatriculation"));
                voiture.setModele(rs.getString("modele"));
                voiture.setNbr_places(rs.getInt("nbr_places"));
                voiture.setCouleur(rs.getString("couleur"));
                voiture.setPrixdelocation(rs.getInt("prixdelocation"));
                return voiture;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }
}

