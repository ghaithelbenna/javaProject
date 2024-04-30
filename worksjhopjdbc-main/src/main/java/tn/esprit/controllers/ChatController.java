package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tn.esprit.services.ChatService;

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
}
