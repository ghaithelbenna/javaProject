package util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

    private static final String URL = "jdbc:mysql://localhost:3306/salah";
    private static final String USER = "root";
    private static final String PASSWORD = "";


    private static DataSource instance;

    private Connection cnx;

    //First Step: Rendre le constructeur privé
    private DataSource() {
        try {
            cnx = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected To DATABASE !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Thrid Step: Creer une methode static pour recuperer l'instance

    public static DataSource getInstance(){
        if (instance == null)
            instance = new DataSource();
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }
}

