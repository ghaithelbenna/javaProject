package service;

import entite.User;
import util.DataSource;

import java.net.ConnectException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;


public class UserService implements IService<User> {

    private Connection cnx;
    private Statement ste;

    public UserService(){
        cnx= DataSource.getInstance().getConnection();
    }

    public void add(User u) {
        if (u.getNom().isEmpty() || u.getPrenom().isEmpty()) {
            throw new IllegalArgumentException("Nom ou/et prenom ?");
        }
        String sql = "INSERT INTO user (nom, prenom, email, password, roles) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, u.getNom());
            pstmt.setString(2, u.getPrenom());
            pstmt.setString(3, u.getEmail());
            String hashedPassword = hashPassword(u.getPassword());
            pstmt.setString(4, hashedPassword);
            pstmt.setString(5, u.getRoles());

            pstmt.executeUpdate();

            // Get the generated user ID and update the User object
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    u.setId(generatedId); // Update the User object with the generated ID
                } else {
                    throw new SQLException("Adding user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user, int id) {
        if (user.getNom().isEmpty() || user.getPrenom().isEmpty() || user.getPassword().isEmpty() || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("fill in the blanks");
        }
        String sql = "UPDATE user SET nom = ?, prenom = ?, email = ?, password = ?, roles = ? WHERE id = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setString(1, user.getNom());
            pstmt.setString(2, user.getPrenom());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPassword());
            pstmt.setString(5, user.getRoles());
            pstmt.setInt(6, id);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating user failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM user WHERE id = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting user failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<User> getAll() {
        List<User>list=new ArrayList<>();
        String sql="select * from user";

        try {
            ste=cnx.createStatement();
            ResultSet rs=ste.executeQuery(sql);
            while(rs.next()){
                list.add(new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public User getById() {
        return null;
    }




    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("roles")
                );
            } else {
                return null; // User not found
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean authenticate(String email, String password) {

        // Logic to authenticate user against database or other authentication service
        // For demonstration purposes, we'll assume a simple check against a hardcoded email and password
        // Modify this logic to query your database for the user with the given email and check the password
        User user = getUserByEmail(email);
        if (user != null && verifyPassword(password, user.getPassword())) {
            return true; // Authentication successful
        } else {
            return false; // Authentication failed
        }
    }
    private boolean verifyPassword(String enteredPassword, String hashedPassword) {
        String enteredHash = hashPassword(enteredPassword); // Hash the entered password
        return enteredHash.equals(hashedPassword); // Compare the hashed passwords
    }
    public User getUserById(int id) {
        User user = null;
        String sql = "SELECT * FROM user WHERE id = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String roles = rs.getString("roles");

                user = new User(nom, prenom, email, password, roles);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching user with ID " + id, e);
        }

        return user;
    }



}
