package tn.esprit.controllers;
import javafx.collections.FXCollections;
import tn.esprit.services.UserService;
import tn.esprit.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import tn.esprit.controllers.HomeController;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorLabel;

    // Inject the UserService
    private UserService userService;

    // Initialize method to set up the controller
    @FXML
    public void initialize() {
        userService = new UserService();
        // Retrieve the last logged-in email and password
        String lastEmail = LoginManager.getLastLoggedInEmail();
        String lastPassword = LoginManager.getLastLoggedInPassword();

        // If there are last logged-in credentials, populate the fields
        if (lastEmail != null && lastPassword != null) {
            emailField.setText(lastEmail);
            passwordField.setText(lastPassword);
        }
    }

    @FXML
    public void loginClicked(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        // Validate username and password
        if (email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter both email and password.");
        } else {
            // Here you would typically perform authentication against your database or other authentication service
            // For demonstration purposes, let's assume you have a method in UserService for authentication
            boolean authenticated = userService.authenticate(email, password);

            if (authenticated) {
                // Login successful
                // You can navigate to the main application or perform any other actions here
                errorLabel.setText("Connected!");
                System.out.println("Login successful");
                LoginManager.setLastLoggedInEmail(email);
                LoginManager.setLastLoggedInPassword(password);
                User user = userService.getUserByEmail(email);
                int userId = user.getId(); // Retrieve the user ID
                if ("[\"ROLE_ADMIN\"]".equals(user.getRoles())) {
                    try {
                        // Load the registration screen
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
                        Parent root = loader.load();

                        // Create a new stage for the registration screen
                        Stage registrationStage = new Stage();
                        registrationStage.setTitle("Registration");
                        registrationStage.setScene(new Scene(root));
                        registrationStage.show();

                        // Close the current login window
                        ((Node) (event.getSource())).getScene().getWindow().hide();
                    } catch (IOException e) {
                        e.printStackTrace();
                        errorLabel.setText("Error loading the registration screen.");
                    }
                } else {
                    try {
                        // Load the registration screen
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/front.fxml"));
                        Parent root = loader.load();

                        // Create a new stage for the registration screen
                        Stage registrationStage = new Stage();
                        registrationStage.setTitle("Registration");
                        registrationStage.setScene(new Scene(root));
                        registrationStage.show();

                        // Close the current login window
                        ((Node) (event.getSource())).getScene().getWindow().hide();
                    } catch (IOException e) {
                        e.printStackTrace();
                        errorLabel.setText("Error loading the registration screen.");
                    }
                }
            }
        }
    }

    public void registerClicked(ActionEvent event) {
        try {
            // Load the registration screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterU.fxml"));
            Parent root = loader.load();

            // Create a new stage for the registration screen
            Stage registrationStage = new Stage();
            registrationStage.setTitle("Registration");
            registrationStage.setScene(new Scene(root));
            registrationStage.show();

            // Close the current login window
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Error loading the registration screen.");
        }
    }
    private void loadScene(String fxmlPath, ActionEvent event, int userId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Access the controller instance
            Object controller = loader.getController();

            // Check the type of the controller and initialize data accordingly
            if (controller instanceof ProfileController) {
                // If it's a ProfileController, initialize its data
                ProfileController profileController = (ProfileController) controller;
                profileController.initData(userId);
            } else if (controller instanceof BackController) {
                // If it's a BackController, initialize its data
                HomeController homeController = (HomeController) controller;
                homeController.initData(userId);
            } else {
                // Handle the case where the controller type is unknown
                throw new IllegalStateException("Unknown controller type: " + controller.getClass().getName());
            }

            // Set the scene
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Error loading the screen: " + fxmlPath);
        }
    }



    public class LoginManager {
        private static String lastLoggedInEmail;
        private static String lastLoggedInPassword;

        // Method to set the last logged-in email
        public static void setLastLoggedInEmail(String email) {
            lastLoggedInEmail = email;
        }

        // Method to get the last logged-in email
        public static String getLastLoggedInEmail() {
            return lastLoggedInEmail;
        }

        // Method to set the last logged-in password
        public static void setLastLoggedInPassword(String password) {
            lastLoggedInPassword = password;
        }

        // Method to get the last logged-in password
        public static String getLastLoggedInPassword() {
            return lastLoggedInPassword;
        }

        // Method to clear the last logged-in email and password
        public static void clearLastLoggedInInfo() {
            lastLoggedInEmail = null;
            lastLoggedInPassword = null;
        }
    }
}
