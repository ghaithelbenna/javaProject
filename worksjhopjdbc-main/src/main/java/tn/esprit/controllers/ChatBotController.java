package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatBotController {

    @FXML
    private TextArea chatArea;

    @FXML
    private TextField messageField;

    @FXML
    public void sendMessage(ActionEvent event) {
        // Récupérer le message du champ de texte
        String message = messageField.getText().trim();

        // Afficher le message de l'utilisateur dans la zone de chat
        appendToChat("User: " + message);

        // Effacer le champ de texte après l'envoi du message
        messageField.clear();

        // Traiter le message et générer la réponse du chatbot
        String botResponse = generateBotResponse(message);

        // Afficher la réponse du chatbot dans la zone de chat
        appendToChat("Bot: " + botResponse);
    }

    // Méthode pour ajouter du texte à la zone de chat
    private void appendToChat(String text) {
        chatArea.appendText(text + "\n");
    }

    // Méthode pour générer la réponse du chatbot
    private String generateBotResponse(String message) {
        // Nettoyer le message de l'utilisateur pour éviter les erreurs de casse
        message = message.toLowerCase();

        // Implémenter la logique de réponse en fonction du message de l'utilisateur
        if (message.contains("bonjour") || message.contains("salut")) {
            return "Bonjour! Comment puis-je vous aider ?";
        } else if (message.contains("bonsoir")) {
            return "Bonsoir! En quoi puis-je vous assister ?";
        }else if (message.contains("2rism")) {
            return "C'est une agence de voyage international!!.";
        } else if (message.contains("information") ){
            return "Pour voir plusiseurs details sur notre agence veuillez choisir guide ou evenement .";
        } else if (message.contains("event") || message.contains("details")) {
            return "Notre agence propose beacoup des evenements.";

        } else if (message.contains("guide") || message.contains("informations")) {
            return "Nous pouvons vous fournir de nombreux guides lors des événements ";
        } else {
            return "Désolé, je ne comprends pas. Pouvez-vous reformuler votre question ?";
        }
    }
}
