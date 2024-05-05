package tn.esprit.services;

import tn.esprit.models.typePack;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TypePackService {

    private Connection cnx;

    public TypePackService() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    public void add(typePack typePack) {
        String query = "INSERT INTO typepack (nomTypePack) VALUES (?)";
        try (PreparedStatement stm = cnx.prepareStatement(query)) {
            stm.setString(1, typePack.getNomTypePack());
            stm.executeUpdate();
            System.out.println("TypePack ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du TypePack : " + e.getMessage());
        }
    }

    public List<typePack> getAll() {
        String query = "SELECT * FROM typepack";
        List<typePack> typePacks = new ArrayList<>();
        try (Statement stm = cnx.createStatement(); ResultSet rs = stm.executeQuery(query)) {
            while (rs.next()) {
                typePack typePack = new typePack();
                typePack.setId_typepack(rs.getInt("id_typepack"));
                typePack.setNomTypePack(rs.getString("nomTypePack"));
                typePacks.add(typePack);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la liste des TypePacks : " + e.getMessage());
            e.printStackTrace(); // Affichez la stack trace pour obtenir des informations détaillées sur l'erreur
        }
        return typePacks;
    }

    public void update(typePack typePack) {
        String query = "UPDATE typepack SET nomTypePack=? WHERE id_typepack=?";
        try (PreparedStatement stm = cnx.prepareStatement(query)) {
            stm.setString(1, typePack.getNomTypePack());
            stm.setInt(2, typePack.getId_typepack());
            int rowsUpdated = stm.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("TypePack mis à jour avec succès !");
            } else {
                System.out.println("Aucun TypePack mis à jour !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du TypePack : " + e.getMessage());
        }
    }

    public boolean delete(typePack typePack) {
        // Vérifiez d'abord si le type de pack existe
        if (isTypePackExists(typePack.getId_typepack())) {
            // S'il existe, vérifiez s'il y a des enregistrements de pack liés à ce type de pack
            if (packExistsForTypePack(typePack)) {
                System.out.println("Des packs sont associés à ce type de pack. Veuillez d'abord supprimer les packs associés.");
                return false; // Ne supprimez pas le type de pack s'il est lié à des packs
            } else {
                // S'il n'y a pas de packs associés, supprimez le type de pack
                String query = "DELETE FROM typepack WHERE id_typepack = ?";
                try (PreparedStatement stm = cnx.prepareStatement(query)) {
                    stm.setInt(1, typePack.getId_typepack());
                    int rowsDeleted = stm.executeUpdate();
                    if (rowsDeleted > 0) {
                        System.out.println("TypePack supprimé avec succès !");
                        return true;
                    } else {
                        System.out.println("Aucun TypePack supprimé !");
                        return false;
                    }
                } catch (SQLException e) {
                    System.out.println("Erreur lors de la suppression du TypePack : " + e.getMessage());
                    return false;
                }
            }
        } else {
            // Si le type de pack n'existe pas, affichez un message approprié
            System.out.println("Le type de pack avec l'ID spécifié n'existe pas !");
            return false;
        }
    }

    public boolean packExistsForTypePack(typePack typePack) {
        String query = "SELECT COUNT(*) FROM pack WHERE id_typepack = ?";
        try (PreparedStatement stm = cnx.prepareStatement(query)) {
            stm.setInt(1, typePack.getId_typepack());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la vérification de l'existence de packs pour le type de pack : " + e.getMessage());
        }
        return false;
    }
    public boolean isTypePackExists(int typePackId) {
        String query = "SELECT COUNT(*) FROM typepack WHERE id_typepack = ?";
        try (PreparedStatement stm = cnx.prepareStatement(query)) {
            stm.setInt(1, typePackId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Retourne true si au moins un type de pack existe avec l'ID spécifié
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
