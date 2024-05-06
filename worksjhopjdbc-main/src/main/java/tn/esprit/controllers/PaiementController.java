package tn.esprit.controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Map;

public class PaiementController {

    @FXML
    private TextField cardNumberField;
    @FXML
    private TextField expiryDateField;
    @FXML
    private TextField cvvField;
    @FXML
    private Label statusLabel;
    @FXML
    private Label montantLabel; // Label pour afficher le montant total

    private float montant;

    public void setMontant(float montant) {
        this.montant = montant;
        // Mettre à jour le texte du label pour afficher le montant total
        montantLabel.setText(Float.toString(montant)); // Convertir le montant en String
    }

    // Création d'un token de carte à partir des informations saisies par l'utilisateur
    private String createToken(String cardNumber, String expiryDate, String cvv) {
        // Votre logique de création de token Stripe ici
        return cardNumber;
    }

    @FXML
    private void processPayment() {
        // Utilisez votre clé API secrète Stripe
        Stripe.apiKey = "sk_test_51OqsOPG9SwQBi6fzq65LI6vmDHW0TwbueDnbR59Fia9zQ8OFljXiHiL1lJjnAIz8o5IwWqN5vXAXHAdX9LaMxmYa00FMnBBrQ5";

        // Récupération des informations de la carte saisies par l'utilisateur
        String cardNumber = cardNumberField.getText();
        String expiryDate = expiryDateField.getText();
        String cvv = cvvField.getText();

        // Préparation des paramètres de la charge
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", montant); // Montant en cents
        chargeParams.put("currency", "usd");
        chargeParams.put("source", createToken(cardNumber, expiryDate, cvv)); // Utilisation d'un token de carte créé à partir des informations saisies par l'utilisateur
        chargeParams.put("description", "Paiement professionnel");

        try {
            // Création de la charge
            Charge charge = Charge.create(chargeParams);
            statusLabel.setText("Paiement réussi : " + charge);
            // Enregistrer les données dans la base de données ici...
        } catch (StripeException e) {
            // Affichage de l'erreur dans la console
            e.printStackTrace();

            // Affichage de l'erreur dans l'étiquette de statut
            statusLabel.setText("Erreur lors du traitement du paiement : " + e.getMessage());

            // Gérer l'échec du paiement...
        }
    }

}
