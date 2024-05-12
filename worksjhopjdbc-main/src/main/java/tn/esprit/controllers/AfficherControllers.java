package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AfficherControllers {


    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtPrenom;

    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPassword;

    public void setTxtNom(String txtNom) {
        this.txtNom.setText(txtNom);
    }

    public void setTxtPrenom(String txtPrenom) {
        this.txtPrenom.setText(txtPrenom);
    }

    public void setTxtEmail(TextField txtEmail) {
        this.txtEmail = txtEmail;
    }

    public void setTxtPassword(TextField txtPassword) {
        this.txtPassword = txtPassword;
    }
}