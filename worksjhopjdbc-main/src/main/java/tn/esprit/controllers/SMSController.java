package tn.esprit.controllers;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.twilio.exception.ApiException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SMSController {

    public static final String ACCOUNT_SID = "ACc7dca76d585525acbc86e706c4b67c91";
    public static final String AUTH_TOKEN = "cb7d611da08bbb8f534a68b4ab3d6566";
    public static final String TWILIO_PHONE_NUMBER = "+14088404213";

    // Obtention de la date actuelle
    LocalDate date = LocalDate.now();
    // Formattage de la date selon le format souhaité
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String formattedDate = date.format(formatter);


    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField messageField;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void sendSMS() {
        String phoneNumber = phoneNumberField.getText();
        String message = "La réservation  de  "+ formattedDate +"  est achevée avec succès ! Il vous - manque que la determination de procédure de paiement ";

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Send SMS
        Message twilioMessage = Message.creator(
                        new PhoneNumber(phoneNumber),
                        new PhoneNumber(TWILIO_PHONE_NUMBER),
                        message).create();

        System.out.println("Message sent successfully. SID: " + twilioMessage.getSid());
    }




}


