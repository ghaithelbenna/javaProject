package tn.esprit.services;

import java.util.HashMap;
import java.util.Map;

public class ChatService {
    private Map<String, String> responses;

    public ChatService() {
        responses = new HashMap<>();
        // Ajoutez ici vos paires de questions et réponses
        String[] greetings1 = {"Bonjour", "salut", "Salut", "coucou"};
        for (String greeting : greetings1) {
            responses.put(greeting, "Bonjour !");
        }
        String[] greetings2 = {"Bonjour", "salut", "hi", "coucou"};
        for (String greeting : greetings2) {
            responses.put(greeting, "Bonjour !");
        }
        String[] greetings3 = {"Comment ça va ","cv","how are you"};
        for (String greeting : greetings3) {
            responses.put(greeting, "bien, merci , comment je peut vous aidez");
        }
        String[] greetings4 = {"presente toi ?","presente toi","Quel est ton nom ?"};
        for (String greeting : greetings4) {
            responses.put(greeting, "je suis un bot de 2rism , je suis la pour vous aidez");
        }
        String[] greetings5 = {"je veux reservé"};
        for (String greeting : greetings5) {
            responses.put(greeting, "consultez nos pack pour personnaliser vos voyage");
        }

        // Ajoutez autant de paires que nécessaire
    }

    public String getResponse(String message) {
        // Vérifiez si la question a une réponse spécifique
        String response = responses.get(message);
        if (response != null) {
            return response;
        } else {
            // Si la question n'a pas de réponse spécifique, renvoyez une réponse aléatoire
            String[] defaultResponses = {
                    "Je ne comprends pas la question.",
                    "Je ne sais pas quoi répondre.",
                    "Posez-moi une autre question."
            };
            int randomIndex = (int) (Math.random() * defaultResponses.length);
            return defaultResponses[randomIndex];
        }
    }
}
