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


    public Integer getIdAgenceByNom(String nomAgence) {
        Integer idAgence = null;

        try {
            String query = "SELECT id_agence FROM locationvoitures WHERE nom_agence = ?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setString(1, nomAgence);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                idAgence = rs.getInt("id_agence");
                if (rs.wasNull()) {
                    idAgence = null; // Si la valeur est NULL dans la base de données, on la considère comme null en Java
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idAgence;
    }


    @Override
    public void ajouterVoiture(voiture voiture) throws SQLException {
        Integer idAgence = getIdAgenceByNom(voiture.getNom_agence()); // Obtenez l'ID de l'agence en utilisant le nom de l'agence

        PreparedStatement pst = cnx.prepareStatement("INSERT INTO vehicule (`immatriculation`, `modele`, `nbr_places`, `couleur`, `prixdelocation`, `imagePath`, `id_agence`) VALUES (?, ?, ?, ?, ?, ?, ?)");
        pst.setString(1, voiture.getImmatriculation());
        pst.setString(2, voiture.getModele());
        pst.setInt(3, voiture.getNbr_places());
        pst.setString(4, voiture.getCouleur());
        pst.setInt(5, voiture.getPrixdelocation());
        pst.setString(6, voiture.getImagePath());  // Ajoutez le chemin d'accès à l'image dans la requête
        pst.setObject(7, idAgence); // Utilisez setObject pour gérer la valeur null

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
            String req = "SELECT v.*, l.nom_agence FROM vehicule v INNER JOIN locationvoitures l ON v.id_agence = l.id_agence";
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
                voiture.setNom_agence(rs.getString("nom_agence")); // Ajout du nom de l'agence
                voitures.add(voiture);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return voitures;
    }



    public boolean immatriculationExists(String immatriculation) throws SQLException {
        // Effectuez la requête appropriée pour vérifier si l'immatriculation existe déjà
        // Retournez true si l'immatriculation existe, sinon retournez false
        // Par exemple, vous pouvez utiliser une requête SQL SELECT pour rechercher l'immatriculation dans la table des voitures
        // Assurez-vous d'utiliser des paramètres de requête ou des requêtes préparées pour éviter les injections SQL
        // Voici un exemple de code :

        String query = "SELECT COUNT(*) FROM vehicule WHERE immatriculation = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setString(1, immatriculation);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }

        return false;
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

