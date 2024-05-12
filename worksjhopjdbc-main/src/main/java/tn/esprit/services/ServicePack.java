package tn.esprit.services;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Pack;
import tn.esprit.models.typePack;
import tn.esprit.utils.MyDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date; // Importation de java.sql.Date
import java.time.Instant;
import java.time.LocalDate; // Importation de java.time.LocalDate
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ServicePack implements IService<Pack> {
    private Connection cnx;

    public ServicePack() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    public void add(Pack pack) {
        String query = "INSERT INTO `pack`(`id_typepack`, `nom_pack`, `description_pack`, `prix`, `date`, `image`, `disponible`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stm = cnx.prepareStatement(query)) {
            stm.setInt(1, pack.getTypePack().getId_typepack());
            stm.setString(2, pack.getNomPack());
            stm.setString(3, pack.getDescriptionPack());
            stm.setDouble(4, (float)pack.getPrix());
            stm.setDate(5, new java.sql.Date(pack.getDate().getTime()));
            stm.setString(6, pack.getImage());
            stm.setBoolean(7, pack.isDisponible());

            stm.executeUpdate();
            System.out.println("Pack ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du pack : " + e.getMessage());
        }
    }

    public List<Pack> afficherListe() {
        String req = "SELECT p.*, tp.* FROM pack p JOIN typepack tp ON p.id_typepack = tp.id_typepack";
        List<Pack> lv = new ArrayList<>();
        try (Statement stm = cnx.createStatement(); ResultSet rs = stm.executeQuery(req)) {
            while (rs.next()) {
                typePack packType = new typePack();
                packType.setId_typepack(rs.getInt("tp.id_typepack"));
                packType.setNomTypePack(rs.getString("tp.nomTypePack"));

                Pack p = new Pack(
                        rs.getInt("p.id_pack"),
                        packType,
                        rs.getString("p.nom_pack"),
                        rs.getString("p.description_pack"),
                        rs.getDouble("p.prix"),
                        rs.getDate("p.date"),
                        rs.getString("p.image"),
                        rs.getBoolean("p.disponible")
                );

                lv.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la liste des packs : " + e.getMessage());
        }
        return lv;
    }

    @Override
    public ArrayList<Pack> getAll() {
        String query = "SELECT * FROM pack";
        ArrayList<Pack> packs = new ArrayList<>();
        try (Statement stm = cnx.createStatement(); ResultSet rs = stm.executeQuery(query)) {
            while (rs.next()) {
                Pack pack = new Pack(
                        rs.getInt("id_pack"),
                        null, // Remplacez null par le TypePack correspondant à l'id_typepack du pack dans la base de données
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
            stm.setObject(1, pack.getTypePack().getId_typepack());
            stm.setString(2, pack.getNomPack());
            stm.setString(3, pack.getDescriptionPack());
            stm.setDouble(4, pack.getPrix());
            stm.setDate(5, (java.sql.Date) new Date(pack.getDate().getTime()));
            stm.setString(6, pack.getImage());
            stm.setBoolean(7, pack.isDisponible());
            stm.setInt(8, pack.getId());
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
                System.out.println("Aucun packsupprimé !");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du pack : " + e.getMessage());
            return false;
        }
    }

    // Méthode pour mettre à jour les prix des packs et afficher les icônes en fonction de la date
    public Map<Integer, ImageView> mettreAJourPrixEtIcônes(List<Pack> packs) {
        LocalDate dateActuelle = LocalDate.now(); // Date actuelle
        Map<Integer, ImageView> iconesMap = new HashMap<>(); // Map pour stocker les icônes
        for (Pack pack : packs) {
            // Convertir la date du pack en LocalDate
            Date datePack = (Date) pack.getDate();
            LocalDate localDatePack = Instant.ofEpochMilli(datePack.getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            ImageView icon;

            if (localDatePack.isAfter(dateActuelle) || localDatePack.isEqual(dateActuelle)) {
                // Afficher l'icône verte si la date du pack est supérieure ou égale à la date actuelle
                icon = new ImageView(new Image(getClass().getResourceAsStream("/icons/vert_icon.png")));
            } else {
                // Afficher l'icône rouge si la date du pack est inférieure à la date actuelle
                icon = new ImageView(new Image(getClass().getResourceAsStream("/icons/rouge_icon.png")));
                // Réduire le prix du pack de 30%
                double nouveauPrix = pack.getPrix() * 0.7; // Réduction de 30%
                pack.setPrix(nouveauPrix);
            }

            // Stocker l'icône dans la map avec l'identifiant du pack comme clé
            iconesMap.put(pack.getId(), icon);
        }

        // Retourner la map contenant les icônes correspondant à chaque pack
        return iconesMap;
    }


}
