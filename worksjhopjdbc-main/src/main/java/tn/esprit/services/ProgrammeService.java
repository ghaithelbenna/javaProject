package tn.esprit.services;

import tn.esprit.models.programme;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProgrammeService {
    private Connection cnx;

    public ProgrammeService() {
        this.cnx = MyDataBase.getInstance().getCnx();
    }

    public void ajouterProgramme(programme nouveauProgramme) {
        String query = "INSERT INTO programme (id_categorie, image, duree, prix, description_programme, disponible) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, nouveauProgramme.getId_categorie());
            preparedStatement.setString(2, nouveauProgramme.getImage());
            preparedStatement.setObject(3, nouveauProgramme.getDuree());
            preparedStatement.setDouble(4, nouveauProgramme.getPrix());
            preparedStatement.setString(5, nouveauProgramme.getDescription_programme());
            preparedStatement.setBoolean(6, nouveauProgramme.isDisponible());
            preparedStatement.executeUpdate();
            System.out.println("Programme ajouté avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du programme : " + e.getMessage());
        }
    }

    public List<programme> getAll() {
        List<programme> programmes = new ArrayList<>();
        String query = "SELECT * FROM programme";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                LocalDateTime duree = resultSet.getTimestamp("duree").toLocalDateTime();
                programme prog = new programme(
                        resultSet.getInt("id_prog"),
                        resultSet.getInt("id_categorie"),
                        resultSet.getString("image"),
                        duree,
                        resultSet.getDouble("prix"),
                        resultSet.getString("description_programme"),
                        resultSet.getBoolean("disponible")
                );
                programmes.add(prog);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des programmes : " + e.getMessage());
        }
        return programmes;
    }

}
