package tn.esprit.services;

import tn.esprit.models.typePack;
import tn.esprit.utils.MyDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceTypePack {

    private Connection cnx;

    public ServiceTypePack() {
        cnx = MyDataBase.getInstance().getCnx();
    }



    // Méthode pour ajouter un nouveau type de pack
    public void ajouterTypePack(typePack type) throws SQLException {
        String query = "INSERT INTO typepack (nomTypePack) VALUES (?)";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setString(1, type.getNomTypePack());
            statement.executeUpdate();
        }
    }

    // Méthode pour récupérer tous les types de packs depuis la base de données
    public List<typePack> afficherListe() throws SQLException {
        List<typePack> types = new ArrayList<>();
        String query = "SELECT * FROM typepack";
        try (PreparedStatement statement = cnx.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                typePack type = new typePack();

                type.setNomTypePack(resultSet.getString("nomTypePack"));
                types.add(type);
            }
        }
        return types;
    }

    public boolean delete(typePack typePack) {
        String query = "DELETE FROM typepack WHERE id_type_pack = ?";
        try (PreparedStatement stm = cnx.prepareStatement(query)) {
            stm.setInt(1, typePack.getId_typepack());
            int rowsDeleted = stm.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Type de pack supprimé avec succès !");
                return true;
            } else {
                System.out.println("Aucun type de pack supprimé !");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du type de pack : " + e.getMessage());
            // Gérer l'exception de manière appropriée
            return false;
        }
    }


    public List<typePack> getAll() throws SQLException {
        List<typePack> types = new ArrayList<>();
        String query = "SELECT * FROM typepack";
        try (PreparedStatement statement = cnx.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                typePack type = new typePack();
                type.setNomTypePack(resultSet.getString("nomTypePack"));
                types.add(type);
            }
        }
        return types;
    }

    public void update(typePack typePack) {
        String query = "UPDATE type_pack SET nom_type_pack = ? WHERE id_type_pack = ?";
        try (PreparedStatement stm = cnx.prepareStatement(query)) {
            stm.setString(1, typePack.getNomTypePack());
            stm.setInt(2, typePack.getId_typepack());
            int rowsUpdated = stm.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Type de pack mis à jour avec succès !");
            } else {
                System.out.println("Aucun type de pack mis à jour !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du type de pack : " + e.getMessage());
            // Gérer l'exception de manière appropriée
        }
    }


}
