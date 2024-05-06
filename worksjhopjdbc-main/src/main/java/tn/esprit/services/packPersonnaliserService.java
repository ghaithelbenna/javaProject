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
        try {
            // Préparer la requête SQL pour l'insertion
            String query = "INSERT INTO pack_personnaliser (pack_id, programme_id) VALUES (?, ?)";
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            // Définir les valeurs des paramètres dans la requête
            preparedStatement.setInt(1, packPerso.getPackId());
            preparedStatement.setInt(2, packPerso.getProgrammeId());
            // Exécuter la requête d'insertion
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Le pack personnalisé a été ajouté avec succès !");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du pack personnalisé : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
