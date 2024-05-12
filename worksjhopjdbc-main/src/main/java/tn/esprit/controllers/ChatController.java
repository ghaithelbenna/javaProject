package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.services.ChatService;

import java.io.IOException;

public class ChatController {
    @FXML
    private TextArea chatArea;
    @FXML
    private TextField messageField;

    private ChatService chatService;

    public ChatController() {
        this.chatService = new ChatService();
    }

    @FXML
    private void initialize() {
        chatArea.appendText("ChatBot: Bonjour !\n");
    }

    @FXML
    private void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            chatArea.appendText("Vous: " + message + "\n");
            String response = chatService.getResponse(message);
            chatArea.appendText("ChatBot: " + response + "\n");
            messageField.clear();
        }
    }

    private Scene previousScene;


    public void retour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichageFront.fxml"));
            Parent root = loader.load();
            previousScene = ((Node) event.getSource()).getScene(); // Conserve la référence à la scène précédente

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
