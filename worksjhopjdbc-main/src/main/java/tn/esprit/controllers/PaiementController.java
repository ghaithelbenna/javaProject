package tn.esprit.controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Pack;
import tn.esprit.models.packPersonnaliser;
import tn.esprit.services.packPersonnaliserService;

import java.io.IOException;
import java.util.List;

    public class PaiementController {

        private packPersonnaliserService packPersoService = new packPersonnaliserService();

        @FXML
        private TextField cardNumberField;
        @FXML
        private TextField expiryDateField;
        @FXML
        private TextField cvvField;
        @FXML
        private Label statusLabel;
        @FXML
        private Label montantLabel;

        private float montant;
        private List<Pack> selectedPacks;

        public void setMontant(float montant) {
            this.montant = montant;
            montantLabel.setText(Float.toString(montant));
        }

        public void setPacksSelectionnes(List<Pack> packs) {
            this.selectedPacks = packs;
        }

        private static final String STRIPE_SECRET_KEY = "sk_test_51OqsOPG9SwQBi6fzq65LI6vmDHW0TwbueDnbR59Fia9zQ8OFljXiHiL1lJjnAIz8o5IwWqN5vXAXHAdX9LaMxmYa00FMnBBrQ5";

        @FXML
        private void processPayment() {
            // Vérifiez si les champs de saisie sont vides
            if (cardNumberField.getText().isEmpty() || expiryDateField.getText().isEmpty() || cvvField.getText().isEmpty()) {
                // Affichez une alerte indiquant que tous les champs doivent être remplis
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Champs vides");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs de saisie.");
                alert.showAndWait();
            } else {
                try {
                    // Traitement du paiement
                    Stripe.apiKey = STRIPE_SECRET_KEY;

                    PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                            .setAmount((long) (montant * 100))
                            .setCurrency("usd")
                            .build();

                    PaymentIntent intent = PaymentIntent.create(params);

                    // Affichage d'une alerte de succès
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Paiement réussi");
                    alert.setHeaderText(null);
                    alert.setContentText("Paiement réussi. Identifiant du PaymentIntent : " + intent.getId());
                    alert.showAndWait();

                    savePacksToDatabase(selectedPacks);
                    saveCustomPacksToDatabase(selectedPacks);

                } catch (StripeException e) {
                    // Affichage d'une alerte d'échec
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de paiement");
                    alert.setHeaderText(null);
                    alert.setContentText("Le paiement a échoué. Erreur : " + e.getMessage());
                    alert.showAndWait();
                }
            }
        }


        private void savePacksToDatabase(List<Pack> packs) {
            // Logique pour enregistrer les packs dans la base de données
        }

        private void saveCustomPacksToDatabase(List<Pack> packs) {
            try {
                for (Pack pack : packs) {
                    packPersonnaliser packPerso = new packPersonnaliser(0, pack.getId(), 1);
                    packPerso.setPackId(pack.getId());
                    packPerso.setProgrammeId(1);
                    packPersoService.addPackPersonnaliser(packPerso);
                }
            } catch (Exception e) {
                // Gérez les erreurs...
            }
        }

        public void setMontantEtNombre(float montant) {
            this.montant = montant;

            montantLabel.setText(Float.toString(montant));

        }

        private Scene previousScene;

        public void RETOUR(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/panier.fxml"));
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
    }

