package Controllers;

import entite.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;

public class ModifierUser {
    @FXML
    private Button ajouter;

    @FXML
    private TableColumn<?, ?> Vemail;

    @FXML
    private TextField iid;

    @FXML
    private Button modifier;

    @FXML
    private TextField Uemail;

    @FXML
    private TableColumn<?, ?> Vid;

    @FXML
    private TextField Upassword;

    @FXML
    private TableColumn<?, ?> Vprenom;

    @FXML
    private TableColumn<?, ?> Vpassword;

    @FXML
    private Button supprimer;

    @FXML
    private TextField Uprenom;

    @FXML
    private TableColumn<?, ?> Vrole;

    @FXML
    private TableView<User> table;

    @FXML
    private TableColumn<?, ?> Vnom;

    @FXML
    private TextField Unom;

    @FXML
    void ajouterUser(ActionEvent event) {

    }

    @FXML
    void modifierUser(ActionEvent event) {

    }

    @FXML
    void supprimerUser(ActionEvent event) {

    }
}
