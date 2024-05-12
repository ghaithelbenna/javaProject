package tn.esprit.controllers;


import tn.esprit.services.UserService;
import tn.esprit.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;

import java.io.IOException;

public class BackController {

    @FXML
    private Button ajouter;

    @FXML
    private TableColumn<User, String> Temail;

    @FXML
    private TextField iid;

    @FXML
    private Button modifier;



    @FXML
    private TableColumn<User, Integer> Tid;



    @FXML
    private TableColumn<User, String> Tprenom;

    @FXML
    private TableColumn<User, String> Tpassword;

    @FXML
    private Button supprimer;

    @FXML
    private TextField Uprenom;

    @FXML
    private TableColumn<User, String> Trole;

    @FXML
    private TableView<User> table;

    @FXML
    private TableColumn<User, String> Tnom;

    @FXML
    private TextField Unom;
    @FXML
    private Button loginButton;

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }



    private UserService userService;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";


    @FXML
    public void initialize() {
        userService = new UserService();

        // Set up the columns in the table
        Tid.setCellValueFactory(new PropertyValueFactory<>("id"));
        Tnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        Tprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        Temail.setCellValueFactory(new PropertyValueFactory<>("email"));
        Tpassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        Trole.setCellValueFactory(new PropertyValueFactory<>("roles"));




        populateTable();


    }

    private void populateTable() {
        ObservableList<User> userList = FXCollections.observableArrayList(userService.getAll());
        table.setItems(userList);
    }




    private boolean validateEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    public void modifierUser(ActionEvent actionEvent) {
        try {
            int userId = Integer.parseInt(iid.getText());
            User existingUser = userService.getUserById(userId);
            if (existingUser != null) {
                // Fetch the existing email and password
                String email = existingUser.getEmail();
                String password = existingUser.getPassword();

                // Create a new User object with the existing email and password
                User modifiedUser = new User(
                        Unom.getText(),
                        Uprenom.getText(),
                        email, // Use the existing email
                        password, // Use the existing password
                        "[\"ROLE_USER\"]"
                );

                userService.update(modifiedUser, userId);
                populateTable(); // Refresh the table to show the updated data
                showAlert(Alert.AlertType.INFORMATION, "Modification d'utilisateur", "Succès!", "Utilisateur modifié");
            } else {
                showAlert(Alert.AlertType.ERROR, "Modification Error", "Utilisateur non trouvé", "Aucun utilisateur trouvé avec cet identifiant.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Invalid", "ID Invalid", "Veuillez saisir un identifiant d'utilisateur valide.");
        } catch (RuntimeException e) {
            showAlert(Alert.AlertType.ERROR, "Modification Error", "Modification échouée", "Une erreur s'est produite lors de la mise à jour de l'utilisateur :" + e.getMessage());
        }
    }

    public void supprimerUser(ActionEvent actionEvent) {
        try {
            int userId = Integer.parseInt(iid.getText());
            userService.delete(userId);
            populateTable(); // Refresh table after deletion
            showAlert(Alert.AlertType.INFORMATION, "supprimer d'utilisateur", "Succès!", "L'utilisateur a été détruit avec succès !");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Invalid", "ID Invalid", "Veuillez saisir un identifiant d'utilisateur valide.");
        } catch (RuntimeException e) {
            // Handle other runtime exceptions
            showAlert(Alert.AlertType.ERROR, "supprission Error", "supprimer a échoué", "Une erreur s'est produite lors de la sipprission de l'utilisateur : " + e.getMessage());
        }
    }




    public void initData(int userId) {
        try {
            User user = userService.getUserById(userId);
            if (user != null) {
                Unom.setText(user.getNom());
                Uprenom.setText(user.getPrenom());
            } else {
                showAlert(Alert.AlertType.ERROR, "Utilisateur non trouvé", "Erreur", "Aucun utilisateur trouvé avec cet identifiant.");
            }
        } catch (RuntimeException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Database Error", "An error occurred while fetching user details: " + e.getMessage());
        }
    }




    private void clearFields() {

        Unom.clear();
        Uprenom.clear();


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


    public void adminUser(ActionEvent event) {
        try {
            int userId = Integer.parseInt(iid.getText());
            User user = userService.getUserById(userId);

            if (user != null) {
                user.setRoles("[\"ROLE_ADMIN\"]");
                userService.update(user, userId);
                populateTable();
                showAlert(Alert.AlertType.INFORMATION, "Admin User", "Success!", "User role updated to ADMIN");
            } else {
                showAlert(Alert.AlertType.ERROR, "User not found", "Error", "User with the provided ID not found");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "ID Invalid ", "Please enter a valid user ID.");
        } catch (RuntimeException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update user role", "An error occurred while updating the user role: " + e.getMessage());
        }
    }
}
