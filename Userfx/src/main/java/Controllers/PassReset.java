package Controllers;

import entite.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import service.UserService;

public class PassReset {

    @FXML
    private TextField codeField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    private String resetCode;

    public void initData(String resetCode) {
        this.resetCode = resetCode;
    }

    @FXML
    void resetPassword(ActionEvent event) {
        String code = codeField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (!code.equals(resetCode)) {
            // Code doesn't match
            // Display an error message
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            // New passwords don't match
            // Display an error message
            return;
        }

        // Password reset successful
        // Close the password reset window
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
}
