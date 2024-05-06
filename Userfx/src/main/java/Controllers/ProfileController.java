package Controllers;

import entite.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import service.UserService;

import java.io.IOException;
import java.util.UUID;

public class ProfileController {

    @FXML
    private Label userNameLabel;

    @FXML
    private Button changePasswordButton;

    private UserService userService;

    // User ID of the current user (you need to set it before loading this controller)
    private int userId;

    // Initialize method to set up the controller
    @FXML
    public void initialize() {
        userService = new UserService();
        populateUserProfile();
    }

    public void initData(int userId) {
        this.userId = userId;
        populateUserProfile();
    }

    // Method to populate the user profile with information
    private void populateUserProfile() {
        User user = userService.getUserById(userId);
        if (user != null) {
            String userName = user.getNom() + " " + user.getPrenom();
            userNameLabel.setText(userName);
        }
    }

    // Method to handle changing the password when the button is clicked
    @FXML
    public void changePasswordClicked() {
        String resetCode = generateResetCode();

        // Temporarily store the reset code (you can store it in a field or a temporary storage)
        //PasswordResetManager.setResetCode(resetCode);

        // Open the password reset page
        openPasswordResetPage(resetCode);
    }

    private String generateResetCode() {
        // Generate a random code/token
        // You can use UUID or any other method to generate a unique code
        return UUID.randomUUID().toString();
    }

    private void sendResetCodeEmail(String email, String resetCode) {
        // Send an email to the user's email address containing the reset code
        // You can use JavaMail API, Apache Commons Email, or any other email service
        // Implement this method according to the email service you're using
    }

    private void openPasswordResetPage(String resetCode) {
        try {
            // Load the password reset page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PassReset.fxml"));
            Parent root = loader.load();

            // Pass the reset code to the password reset controller
            PassReset passwordResetController = loader.getController();
            passwordResetController.initData(resetCode);

            // Show the password reset page in a new window
            Stage stage = new Stage();
            stage.setTitle("Password Reset");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }

    // Method to set the user ID
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void LoginClicked(ActionEvent actionEvent) {
        try {
            // Load the login screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();

            // Get the current stage from the action event source
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
