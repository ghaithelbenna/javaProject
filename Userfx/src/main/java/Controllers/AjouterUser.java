package Controllers;

import entite.User;
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
import service.UserService;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;

import java.io.IOException;

public class AjouterUser {

    @FXML
    private Button ajouter;







    @FXML
    private TextField Uemail;


    @FXML
    private TextField Upassword;





    @FXML
    private TextField Uprenom;







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



        Uemail.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(EMAIL_REGEX) && !newValue.isEmpty()) {
                Uemail.setStyle("-fx-text-fill: red;");  // Change text color to red if invalid
            } else {
                Uemail.setStyle("-fx-text-fill: black;"); // Revert back to black when corrected
            }
        });




    }




    public void ajouterUser(ActionEvent actionEvent) {
        if (validateEmail(Uemail.getText())) {
            User newUser = new User(0, Unom.getText(), Uprenom.getText(), Uemail.getText(), Upassword.getText(), "[\"ROLE_USER\"]");
            userService.add(newUser); // Add the user and update the User object with the generated ID

            int userId = newUser.getId(); // Retrieve the generated user ID from the User object

            clearFields();
            loadScene("/Profile.fxml", actionEvent, userId); // Pass the generated user ID to the next scene
        } else {
            showAlert(Alert.AlertType.ERROR, "Input invalid", "Échec de la validation de l'e-mail", "S'il vous plaît, mettez une adresse email valide.");
        }
    }
    private boolean validateEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }











    private void clearFields() {

            Unom.clear();
            Uprenom.clear();
            Uemail.clear();
            Upassword.clear();

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
    private void loadScene(String fxmlPath, ActionEvent event, int userId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Get the controller instance
            ProfileController profileController = loader.getController();
            // Pass the user ID to the controller
            profileController.initData(userId);

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

}