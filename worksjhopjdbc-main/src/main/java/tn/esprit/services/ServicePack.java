package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Pack;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicePack implements IService<Pack> {
    private Connection cnx;

    public ServicePack() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    public void add(Pack pack) {
        String query = "INSERT INTO `pack`(`id_typepack`, `nom_pack`, `description_pack`, `prix`, `date`, `image`, `disponible`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stm = cnx.prepareStatement(query)) {
            stm.setInt(1,pack.getId_typepack());
            stm.setString(2, pack.getNomPack());
            stm.setString(3, pack.getDescriptionPack());
            stm.setDouble(4, pack.getprix());
            stm.setDate(5, new java.sql.Date(pack.getDate().getTime()));
            stm.setString(6, pack.getImage());
            stm.setBoolean(7, pack.isDisponible());
            // Ajoutez l'ID du type de pack à la requête

            stm.executeUpdate();
            System.out.println("Pack ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du pack : " + e.getMessage());
            // Vous pouvez remonter l'exception à l'appelant ou gérer de manière appropriée ici
        }
    }

    public List<Pack> afficherListe() {
        String req = "SELECT * FROM pack";
        List<Pack> lv = new ArrayList<>();
        try (Statement stm = cnx.createStatement(); ResultSet rs = stm.executeQuery(req)) {
            while (rs.next()) {
                Pack p = new Pack(
                        rs.getInt("id_pack"),
                        rs.getInt("id_typepack"),
                        rs.getString("nom_pack"),
                        rs.getString("description_pack"),
                        rs.getDouble("prix"),
                        rs.getDate("date"),
                        rs.getString("image"),
                        rs.getBoolean("disponible")
                );
                lv.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la liste des packs : " + e.getMessage());
        }
        return lv;
    }

    // Méthode pour récupérer tous les packs (non utilisée dans le code fourni, mais ajoutée pour la complétude)
    @Override
    public ArrayList<Pack> getAll() {
        String query = "SELECT * FROM pack";
        ArrayList<Pack> packs = new ArrayList<>();
        try (Statement stm = cnx.createStatement(); ResultSet rs = stm.executeQuery(query)) {
            while (rs.next()) {
                Pack pack = new Pack(
                        rs.getInt("id_pack"),
                        rs.getInt("id_typepack"),
                        rs.getString("nom_pack"),
                        rs.getString("description_pack"),
                        rs.getDouble("prix"),
                        rs.getDate("date"),
                        rs.getString("image"),
                        rs.getBoolean("disponible")
                );
                packs.add(pack);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la liste des packs : " + e.getMessage());
        }
        return packs;
    }

    @Override
    public void update(Pack pack) {
        String query = "UPDATE pack SET id_typepack=?, nom_pack=?, description_pack=?, prix=?, date=?, image=?, disponible=? WHERE id_pack=?";
        try (PreparedStatement stm = cnx.prepareStatement(query)) {
            stm.setInt(1, pack.getId_typepack());
            stm.setString(2, pack.getNomPack());
            stm.setString(3, pack.getDescriptionPack());
            stm.setDouble(4, pack.getPrix());
            stm.setDate(5, new java.sql.Date(pack.getDate().getTime()));
            stm.setString(6, pack.getImage());
            stm.setBoolean(7, pack.isDisponible());
            stm.setInt(8, pack.getId()); // Assurez-vous que cette ligne est ajoutée pour spécifier la valeur du huitième paramètre
            int rowsUpdated = stm.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Pack mis à jour avec succès !");
            } else {
                System.out.println("Aucun pack mis à jour !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du pack : " + e.getMessage());
        }
    }




    @Override
    public boolean delete(Pack pack) {
        String query = "DELETE FROM pack WHERE id_pack = ?";
        try (PreparedStatement stm = cnx.prepareStatement(query)) {
            stm.setInt(1, pack.getId());
            int rowsDeleted = stm.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Pack supprimé avec succès !");
                return true;
            } else {
                System.out.println("Aucun pack supprimé !");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du pack : " + e.getMessage());
            return false;
        }
    }

}
