package tn.esprit.services;


import tn.esprit.models.categorie;
import tn.esprit.utils.MyDataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategorieService {
    private Connection cnx;
    public CategorieService() {

        cnx = MyDataBase.getInstance().getCnx();
    }

    public boolean addCategorie(categorie categorie) {
        String query = "INSERT INTO categorie (nomcategorie) VALUES (?)";
        try {
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setString(1, categorie.getNomcategorie());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Catégorie ajoutée avec succès !");
                return true; // Retourne true si l'insertion a réussi
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retourne false en cas d'échec de l'insertion
    }


    public List<categorie> getAllCategories() {
        List<categorie> categories = new ArrayList<>();
        String query = "SELECT * FROM categorie";
        try {
            PreparedStatement statement = cnx.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id_categorie");
                String nomCategorie = resultSet.getString("nomcategorie");
                categorie categorie = new categorie(id, nomCategorie);
                categories.add(categorie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public boolean supprimerCategorie(categorie categorie) {
        String query = "DELETE FROM categorie WHERE id_categorie = ?";
        try (PreparedStatement stm = cnx.prepareStatement(query)) {
            stm.setInt(1, categorie.getId_categorie());
            int rowsDeleted = stm.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Catégorie supprimée avec succès !");
                return true;
            } else {
                System.out.println("Aucune catégorie supprimée !");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la catégorie : " + e.getMessage());
            return false;
        }
    }

        public boolean updateCategorie(categorie categorie) {
            String query = "UPDATE categorie SET nomcategorie = ? WHERE id_categorie = ?";
            try (PreparedStatement statement = cnx.prepareStatement(query)) {
                statement.setString(1, categorie.getNomcategorie());
                statement.setInt(2, categorie.getId_categorie());

                int rowsUpdated = statement.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Catégorie mise à jour avec succès !");
                    return true;
                } else {
                    System.out.println("Aucune catégorie mise à jour !");
                    return false;
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la mise à jour de la catégorie : " + e.getMessage());
                return false;
            }
        }
    }

