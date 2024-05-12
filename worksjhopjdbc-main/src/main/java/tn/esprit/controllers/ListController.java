package tn.esprit.controllers;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import tn.esprit.models.hebergement;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import tn.esprit.services.HebergementService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.gluonhq.maps.MapView;

public class ListController implements Initializable {

    @FXML
    private ScrollPane front_scroll_pane;

    @FXML
    private ImageView qr_image;

    @FXML
    private VBox adresse;

    @FXML
    private GridPane front_grid_pane;

    @FXML
    private AnchorPane ajouter_form;

    @FXML
    private Button affichage_front_btn;

    @FXML
    private AnchorPane front_form;

    @FXML
    private Button ajouter_hebergement_btn;

    @FXML
    private BarChart<?, ?> chart;

    @FXML
    private TextField description_field_mod;

    @FXML
    private TextField description_filed;

    @FXML
    private TextField emplacement_field;

    @FXML
    private TextField emplacement_field_mod;

    @FXML
    private Button home_btn;

    @FXML
    private AnchorPane home_form;

    @FXML
    private ListView<hebergement> listviewajout;

    @FXML
    private ListView<hebergement> listviewmod;

    @FXML
    private AnchorPane modifier_form;

    @FXML
    private Button modifier_hebergement_btn;

    @FXML
    private Button modify_btn;

    @FXML
    private TextField name_field;

    @FXML
    private TextField name_field_mod;

    @FXML
    private Label nbr_airbnb;

    @FXML
    private TextField nbr_etoiles_field;

    @FXML
    private TextField nbr_etoiles_field_mod;

    @FXML
    private Label nbr_hotels;

    @FXML
    private Label nbr_maison_hautes;

    @FXML
    private TextField prix_field;

    @FXML
    private TextField prix_field_mod;

    @FXML
    private TextField search;

    @FXML
    private ChoiceBox<String> type_hebergement_choice;

    @FXML
    private ChoiceBox<String> type_hebergement_field_mod;

    private HebergementService service = new HebergementService();
    private hebergement selectedhebergement;
    private HebergementService hebergementService;



    private MapView mapView;
    private final java.util.Map<String, MapPoint> destinationCoordinates = new HashMap<>();
    private final MapPoint espritGhazelaPoint = new MapPoint(36.896953, 10.189527);
   // Map map = new Map();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize your ChoiceBox and other components here if needed

        // Load data into the ListView
        loadHebergementData();
        type_hebergement_choice.getItems().addAll("hotel", "maison d'haute", "airbnb");
        type_hebergement_field_mod.getItems().addAll("hotel", "maison d'haute", "airbnb");

        // Retrieve accommodations from the database
        List<hebergement> hebergements = null;
        try {
            hebergements = service.afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Add accommodations to the ListView components
        listviewajout.getItems().addAll(hebergements);
        listviewmod.getItems().addAll(hebergements);

        // Initialize destination coordinates
        initializeDestinationCoordinates();

        // Initialize hebergementService
        hebergementService = new HebergementService();

        try {
            int count = hebergementService.affichercountmaisonhaute();
            nbr_maison_hautes.setText(String.valueOf(count));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            int count = hebergementService.affichercounthotel();
            nbr_hotels.setText(String.valueOf(count));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            int count = hebergementService.affichercountairbnb();
            nbr_airbnb.setText(String.valueOf(count));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Add selection listener to listviewajout
        listviewajout.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateMap(newValue);
            }
        });

        // Add selection listener to listviewmod
        listviewmod.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                initData(newValue); // Update modification fields immediately upon selection
            }
        });
    }

    private void updateMap(hebergement selectedHebergement) {
        System.out.println("Updating map for selected hebergement: " + selectedHebergement);
        // Clear the adresse VBox
        adresse.getChildren().clear();

        // Create a new map view for the selected hebergement
        mapView = createMapView(selectedHebergement);
        adresse.getChildren().add(mapView);

        System.out.println("Map view added to the adresse VBox.");

        VBox.setVgrow(mapView, Priority.ALWAYS);
    }




    private MapView createMapView(hebergement selectedHebergement) {
        MapView mapView = new MapView();
        mapView.setPrefSize(500, 400);
        mapView.addLayer(new CustomMapLayer());
        mapView.setZoom(15);
        mapView.flyTo(0, espritGhazelaPoint, 0.1);
        return mapView;
    }



    private void initializeDestinationCoordinates() {

        destinationCoordinates.put("Paris", new MapPoint(48.8588443, 2.2943506));
        destinationCoordinates.put("New York", new MapPoint(40.712776, -74.005974));
        destinationCoordinates.put("London", new MapPoint(51.5074, -0.1278));
        destinationCoordinates.put("Tokyo", new MapPoint(35.6895, 139.6917));
        destinationCoordinates.put("Sydney", new MapPoint(-33.865143, 151.209900));
        destinationCoordinates.put("Rome", new MapPoint(41.9028, 12.4964));
        destinationCoordinates.put("Berlin", new MapPoint(52.5200, 13.4050));
        destinationCoordinates.put("Moscow", new MapPoint(55.7558, 37.6176));
        destinationCoordinates.put("Beijing", new MapPoint(39.9042, 116.4074));
        destinationCoordinates.put("Cairo", new MapPoint(30.0330, 31.2336));
        destinationCoordinates.put("Rio de Janeiro", new MapPoint(-22.9068, -43.1729));
        destinationCoordinates.put("Los Angeles", new MapPoint(34.0522, -118.2437));
        destinationCoordinates.put("Toronto", new MapPoint(43.65107, -79.347015));
        destinationCoordinates.put("Dubai", new MapPoint(25.276987, 55.296249));
        destinationCoordinates.put("Hong Kong", new MapPoint(22.3193, 114.1694));
        destinationCoordinates.put("Bangkok", new MapPoint(13.7563, 100.5018));
        destinationCoordinates.put("Mexico City", new MapPoint(19.4326, -99.1332));
        destinationCoordinates.put("Madrid", new MapPoint(40.4168, -3.7038));
        destinationCoordinates.put("Amsterdam", new MapPoint(52.3676, 4.9041));
        destinationCoordinates.put("Seoul", new MapPoint(37.5665, 126.9780));
        destinationCoordinates.put("Singapore", new MapPoint(1.3521, 103.8198));
        destinationCoordinates.put("Istanbul", new MapPoint(41.0082, 28.9784));
        destinationCoordinates.put("Mumbai", new MapPoint(19.0760, 72.8777));
        destinationCoordinates.put("Lisbon", new MapPoint(38.7223, -9.1393));
        destinationCoordinates.put("Athens", new MapPoint(37.9838, 23.7275));
        destinationCoordinates.put("Stockholm", new MapPoint(59.3293, 18.0686));
        destinationCoordinates.put("Vienna", new MapPoint(48.2082, 16.3738));
        destinationCoordinates.put("Prague", new MapPoint(50.0755, 14.4378));
        destinationCoordinates.put("Copenhagen", new MapPoint(55.6761, 12.5683));
        destinationCoordinates.put("Dublin", new MapPoint(53.3498, -6.2603));
        destinationCoordinates.put("Helsinki", new MapPoint(60.1695, 24.9354));
        destinationCoordinates.put("Tunis", new MapPoint(36.8065, 10.1815));



    }



    private class CustomMapLayer extends    MapLayer {
        private final Node marker;

        public CustomMapLayer() {
            marker = new Circle(5, Color.RED);
            getChildren().add(marker);
        }

        @Override
        protected void layoutLayer() {
            Point2D point = getMapPoint(espritGhazelaPoint.getLatitude(), espritGhazelaPoint.getLongitude());
            marker.setTranslateX(point.getX());
            marker.setTranslateY(point.getY());
        }
    }

    private void loadHebergementData() {
        List<hebergement> hebergements = null;
        try {
            hebergements = service.afficher(); // Assuming you have a method in service to fetch all hebergements
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Clear the existing grid pane children
        front_grid_pane.getChildren().clear();

        int columnIndex = 0;
        int rowIndex = 0;

        for (hebergement hebergementData : hebergements) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/card.fxml"));
                Node cardNode = loader.load();
                cardController controller = loader.getController();
                controller.SetData(hebergementData);

                // Add the card to the grid pane
                front_grid_pane.add(cardNode, columnIndex, rowIndex);

                // Increment the column index and row index
                columnIndex++;
                if (columnIndex == 3) { // Limit 3 cards per row
                    columnIndex = 0;
                    rowIndex++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    @FXML
    void add_btn(ActionEvent  actionEvent) {
        // Implement logic to add a new accommodation
        String typeHebergement = type_hebergement_choice.getValue(); // Use getValue() to get the selected item
        String emplacementText = emplacement_field.getText(); // Use getText() to get text from the TextField
        String nbrEtoilesText =nbr_etoiles_field.getText(); // Get text from the TextField for number of stars
        String descriptionText = description_filed.getText(); // Use getText() to get text from the TextField
        String prixText = prix_field.getText(); // Get text from the TextField for price
        String nameText = name_field.getText(); // Use getText() to get text from the TextField

        try {

            // Validate input fields
            if (typeHebergement == null || emplacementText.isEmpty() || nbrEtoilesText.isEmpty() || descriptionText.isEmpty() || prixText.isEmpty() || nameText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez remplir tous les champs.");
                return; // Exit the method if any field is empty
            }

            int nbrEtoile ;
            float prixValue ;

            try {
                prixValue = Float.parseFloat(prixText);
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le prix doit être un nombre.");
                return; // Exit the method if the price is not a number
            }

            try {
                nbrEtoile = Integer.parseInt(nbrEtoilesText);
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le nombre d'etoile  doit être un nombre.");
                return; // Exit the method if the price is not a number
            }

            // Check if the name input contains only alphabetic characters
            if (!nameText.matches("[a-zA-Z]+")) {
                showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le nom doit contenir uniquement des lettres.");
                return; // Exit the method if the name contains non-alphabetical characters
            }

            // Check if the description exceeds 100 characters
            if (descriptionText.length() > 100) {
                showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "La description ne doit pas dépasser 100 caractères.");
                return; // Exit the method if the description exceeds 100 characters
            }

            if (nbrEtoile < 1 || nbrEtoile > 5) {
                showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le nombre d'étoiles doit etre compris entre 1 et 5.");
                return; // Exit the method if the number of stars is negative
            }

            if (prixValue < 0) {
                showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le prix ne peut pas être négatif.");
                return; // Exit the method if the price is negative
            }

            // Create a new hebergement object
            hebergement newHebergement = new hebergement(typeHebergement, emplacementText, nbrEtoile, descriptionText, prixValue, nameText);

            // Add the new hebergement to the database using HebergementService
            HebergementService hebergementService = new HebergementService();
            hebergementService.ajouter(newHebergement);
            System.out.println("Hebergement ajouté avec succès !");

            try {
                // Load the notification FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/notification.fxml"));
                Parent root = loader.load();

                // Create a new stage for the notification
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(new Scene(root));

                // Show the notification stage
                stage.show();

                // Close the notification after 5 seconds
                PauseTransition delay = new PauseTransition(Duration.seconds(5));
                delay.setOnFinished(event -> stage.close());
                delay.play();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Redirect to another interface
            // Update the ListView
            ObservableList<hebergement> items = listviewajout.getItems();
            items.add(newHebergement);

            // Clear the input fields
            emplacement_field.clear();
            nbr_etoiles_field.clear();
            description_filed.clear();
            prix_field.clear();
            name_field.clear();
        } catch (NumberFormatException e) {
            System.err.println("Erreur de format pour le nombre d'étoiles ou le prix. Assurez-vous d'entrer des valeurs numériques valides.");
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout de l'hébergement: " + e.getMessage());
        }

        try {
            int count = hebergementService.affichercountmaisonhaute();
            nbr_maison_hautes.setText(String.valueOf(count));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            int count = hebergementService.affichercounthotel();
            nbr_hotels.setText(String.valueOf(count));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            int count = hebergementService.affichercountairbnb();
            nbr_airbnb.setText(String.valueOf(count));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void initData(hebergement selectedhebergement) {
        this.selectedhebergement = selectedhebergement;


        description_field_mod.setText(selectedhebergement.getDescription());
        emplacement_field_mod.setText(selectedhebergement.getEmplacement());
        name_field_mod.setText(selectedhebergement.getName());
        nbr_etoiles_field_mod.setText(String.valueOf(selectedhebergement.getNbr_etoile()));
        prix_field_mod.setText(String.valueOf(selectedhebergement.getPrix()));

    }


    @FXML
    void modify_btn(ActionEvent event) {
        String typeHebergement = type_hebergement_field_mod.getValue(); // Use getValue() to get the selected item
        String emplacementText = emplacement_field_mod.getText(); // Use getText() to get text from the TextField
        String nbrEtoilesText = nbr_etoiles_field_mod.getText(); // Get text from the TextField for number of stars
        String descriptionText = description_field_mod.getText(); // Use getText() to get text from the TextField
        String prixText = prix_field_mod.getText(); // Get text from the TextField for price
        String nameText = name_field_mod.getText(); // Use getText() to get text from the TextField

        // Validate input fields
        try {
            int nbrEtoile = Integer.parseInt(nbrEtoilesText);
            float prixValue = Float.parseFloat(prixText);

            if (nbrEtoile < 1 || nbrEtoile > 5) {
                showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le nombre d'étoiles doit être compris entre 1 et 5.");
                return; // Exit the method if the number of stars is not within the range
            }

            if (prixValue < 0) {
                showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le prix ne peut pas être négatif.");
                return; // Exit the method if the price is negative
            }

            // Update the selected hebergement object
            selectedhebergement.setDescription(descriptionText);
            selectedhebergement.setEmplacement(emplacementText);
            selectedhebergement.setName(nameText);
            selectedhebergement.setNbr_etoile(nbrEtoile);
            selectedhebergement.setPrix(prixValue);
            selectedhebergement.setType_hebergement(typeHebergement);

            // Call the modifier method from HebergementService
            hebergementService.modifier(selectedhebergement);
            System.out.println("Hebergement modifié avec succès !");

            // Refresh the ListView items
            listviewmod.refresh();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de format", "Assurez-vous d'entrer des valeurs numériques valides pour le nombre d'étoiles et le prix.");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur lors de la modification", "Une erreur s'est produite lors de la modification de l'hébergement.");
        }
    }



    public void switchForm (ActionEvent event) {
        if (event.getSource() == home_btn) {
            home_form.setVisible(true);
            ajouter_form.setVisible(false);
            modifier_form.setVisible(false);
            front_form.setVisible(false);
        } else if (event.getSource() == ajouter_hebergement_btn) {
            home_form.setVisible(false);
            ajouter_form.setVisible(true);
            modifier_form.setVisible(false);
            front_form.setVisible(false);
        } else if (event.getSource() == modifier_hebergement_btn) {
            home_form.setVisible(false);
            ajouter_form.setVisible(false);
            modifier_form.setVisible(true);
            front_form.setVisible(false);
        }else if (event.getSource() == affichage_front_btn){
            home_form.setVisible(false);
            ajouter_form.setVisible(false);
            modifier_form.setVisible(false);
            front_form.setVisible(true);
        }
    }

    // You can add more methods as needed for your application

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    @FXML
    void generate(ActionEvent event) {
        // Get the selected item from the listviewmod
        hebergement selectedHebergement = listviewmod.getSelectionModel().getSelectedItem();
        if (selectedHebergement == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner un élément dans la liste.");
            return;
        }



        // Generate the text for the QR code using the attributes of the selected hebergement
        String qrText = "Type: " + selectedHebergement.getType_hebergement() + "\n"
                + "Emplacement: " + selectedHebergement.getEmplacement() + "\n"
                + "Nombre d'étoiles: " + selectedHebergement.getNbr_etoile() + "\n"
                + "Description: " + selectedHebergement.getDescription() + "\n"
                + "Prix: " + selectedHebergement.getPrix() + "\n"
                + "Nom: " + selectedHebergement.getName();

        try {
            // Generate the QR code image
            ByteArrayOutputStream out = QRCode.from(qrText).to(ImageType.PNG).stream();
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
            javafx.scene.image.Image qrImage = new javafx.scene.image.Image(in);

            // Display the QR code image in the qr_image ImageView
            qr_image.setImage(qrImage);
            System.out.println("QR Code Content: " + qrText);



        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la génération du code QR.");
        }
    }




    private void updateMapPosition(String destination) {
        MapPoint destinationPoint = getDestinationCoordinates(destination);
        if (mapView != null && destinationPoint != null) {
            mapView.flyTo(0, destinationPoint, 0.1);
        }
    }

    private MapPoint getDestinationCoordinates(String destination) {
        return destinationCoordinates.get(destination);
    }


    private Scene previousScene;

    public void RETOUR(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
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