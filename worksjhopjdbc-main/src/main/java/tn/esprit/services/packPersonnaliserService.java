package tn.esprit.services;

import tn.esprit.models.packPersonnaliser;
import tn.esprit.utils.MyDataBase;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class packPersonnaliserService {
    private Connection cnx;

    public packPersonnaliserService() {
        this.cnx = MyDataBase.getInstance().getCnx();
    }

    public void addPackPersonnaliser(packPersonnaliser packPerso) {
        PreparedStatement preparedStatement = null;
        try {
            String query = "INSERT INTO pack_personnaliser (pack_id, programme_id) VALUES (?, ?)";
            preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setInt(1, packPerso.getPackId());
            preparedStatement.setInt(2, packPerso.getProgrammeId());
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Le pack personnalisé a été ajouté avec succès !");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du pack personnalisé : " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la fermeture de l'objet PreparedStatement : " + e.getMessage());
                }
            }
        }
    }
}
