package tn.esprit.controllers;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import entities.evenement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.esprit.services.ServiceEvenement;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class ListEvenementController {
    private MapView mapView;
    @FXML
    private VBox adresse;
    @FXML
    private ListView<evenement> eventListView;
    @FXML
    private TextField searchField;

    private final MapPoint espritGhazelaPoint = new MapPoint(36.896953, 10.189527);
    private ServiceEvenement serviceEvenement;
    private final HashMap<String, MapPoint> destinationCoordinates = new HashMap<>();

    public ListEvenementController() {
        serviceEvenement = new ServiceEvenement();
    }

    @FXML
    public void initialize() {
        eventListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        loadEvents();
        mapView = createMapView();
        adresse.getChildren().add(mapView);
        VBox.setVgrow(mapView, Priority.ALWAYS);

        // Add listener for selection changes in the ListView
        eventListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String typeEvent = newValue.getType_event();
                updateMapPosition(typeEvent);
            }
        });
    }

    public void updateMapPosition(String typeEvent) {
        MapPoint destinationPoint = destinationCoordinates.get(typeEvent);
        if (mapView != null && destinationPoint != null) {
            mapView.flyTo(0, destinationPoint, 0.1);
        }
    }

    private MapView createMapView() {
        MapView mapView = new MapView();
        mapView.setPrefSize(500, 400);
        mapView.addLayer(new CustomMapLayer());
        mapView.setZoom(15);
        mapView.flyTo(0, espritGhazelaPoint, 0.1);
        return mapView;
    }

    public void loadEvents() {
        try {
            List<evenement> events = serviceEvenement.afficher();
            ObservableList<evenement> observableEvents = FXCollections.observableArrayList(events);
            eventListView.setItems(observableEvents);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void AddEvent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutEvenement.fxml"));
            Parent root = loader.load();
            Scene currentScene = eventListView.getScene();
            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ModifyEvent() {
        evenement selectedEvenement = eventListView.getSelectionModel().getSelectedItem();
        if (selectedEvenement == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun événement sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un événement à modifier.");
            alert.showAndWait();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Modify.fxml"));
            Parent root = loader.load();
            tn.esprit.controllers.ModifyController modifyController = loader.getController();
            modifyController.initData(selectedEvenement, serviceEvenement);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier un événement");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            loadEvents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteSelectedEvents() {
        ObservableList<evenement> selectedEvents = eventListView.getSelectionModel().getSelectedItems();
        if (selectedEvents.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun événement sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner au moins un événement à supprimer.");
            alert.showAndWait();
            return;
        }
        try {
            for (evenement event : selectedEvents) {
                serviceEvenement.supprimer(event);
            }
            loadEvents();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void generatePDF(ActionEvent event) {
        try {
            List<evenement> events = serviceEvenement.afficher();
            PDFGenerator.generatePDF(events);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PDF Generated");
            alert.setHeaderText(null);
            alert.setContentText("PDF report generated successfully.");
            alert.showAndWait();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void search(ActionEvent event) {
        String query = searchField.getText().toLowerCase();
        try {
            List<evenement> events = serviceEvenement.afficher();
            ObservableList<evenement> filteredEvents = FXCollections.observableArrayList();
            for (evenement evt : events) {
                if (evt.getNom_event().toLowerCase().contains(query) || evt.getType_event().toLowerCase().contains(query)) {
                    filteredEvents.add(evt);
                }
            }
            eventListView.setItems(filteredEvents);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private Scene previousScene;

    public void naviguer(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListGuide.fxml"));
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



    private class CustomMapLayer extends MapLayer {
        private final Node marker;

        public CustomMapLayer() {
            marker = new Circle(5, Color.RED);
            getChildren().add(marker);
            initializeDestinationCoordinates();
        }

        @Override
        protected void layoutLayer() {
            Point2D point = getMapPoint(espritGhazelaPoint.getLatitude(), espritGhazelaPoint.getLongitude());
            marker.setTranslateX(point.getX());
            marker.setTranslateY(point.getY());
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
        }
    }
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
