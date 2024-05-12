package tn.esprit.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import tn.esprit.models.voiture;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import tn.esprit.services.voitureServices;



import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;




public class afficherVoituresController {

    private final voitureServices vs = new voitureServices();

    @FXML
    private Button pdfButton;

    @FXML
    private Button exportToExcel;


    @FXML
    private ListView<voiture> listView;

    @FXML
    private Button versAjout;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize() {
        afficherVoitures();
        pdfButton.setOnAction(this::exportToPDF);
    }

    public void afficherVoitures() {
        ObservableList<voiture> voitures = vs.getAll();

        listView.setItems(voitures);

        // Définir une cellule personnalisée pour afficher les voitures
        listView.setCellFactory(param -> new ListCell<voiture>() {
            @Override
            protected void updateItem(voiture item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    String voitureInfo = "immatriculation: " + item.getImmatriculation() + "\n" +
                            "Modèle: " + item.getModele() + "\n" +
                            "nbr places: " + item.getNbr_places() + "\n" +
                            "Couleur: " + item.getCouleur() + "\n" +
                            "prix de location: " + item.getPrixdelocation() + "\n" +
                            "imagePath: " + item.getImagePath() + "\n" +
                            "Nom agence: " + item.getNom_agence(); // Ajout du nom de l'agence
                    setText(voitureInfo);
                }
            }
        });

        // Ajouter un gestionnaire d'événements pour écouter le clic sur une voiture dans la liste
        listView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) { // Double-clic
                voiture selectedVoiture = listView.getSelectionModel().getSelectedItem();
                if (selectedVoiture != null) {
                    openModifierVoitureWindow(event, selectedVoiture);
                }
            }
        });
    }

    public void openModifierVoitureWindow(MouseEvent event, voiture selectedVoiture) {
        if (selectedVoiture != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateVoitures.fxml"));
                Parent root = loader.load();

                // Obtenir la référence du contrôleur de la fenêtre de mise à jour
                updateVoituresController controller = loader.getController();
                controller.setVoiture(selectedVoiture);
                controller.setAfficherController(this); // Passer la référence de la fenêtre d'affichage

                // Charger l'image stockée dans la voiture et la passer au contrôleur de mise à jour
                String imagePath = selectedVoiture.getImagePath();
                if (imagePath != null && !imagePath.isEmpty()) {
                    File imageFile = new File(imagePath);
                    Image image = new Image(imageFile.toURI().toString());
                    controller.setImage(image); // Afficher l'image dans l'imageView
                } else {
                    // Afficher une image par défaut ou laisser l'imageView vide
                    // Par exemple:
                    Image defaultImage = new Image(getClass().getResourceAsStream("/default_image.png"));
                    controller.setImage(defaultImage); // Afficher l'image par défaut
                }

                // Récupérer la scène actuelle à partir de l'événement
                Scene currentScene = ((Node) event.getSource()).getScene();

                // Remplacer le contenu de la scène actuelle par le nouveau Parent
                currentScene.setRoot(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    public void exportToPDF(ActionEvent event) {
        try {
            // Créer un nouveau document PDF
            PDDocument document = new PDDocument();

            // Créer une nouvelle page
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Initialiser le contenu de la page
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Ajouter le logo
            InputStream inputStream = getClass().getResourceAsStream("/images/logo2rism.png");
            PDImageXObject logo = PDImageXObject.createFromByteArray(document, IOUtils.toByteArray(inputStream), "png");

            contentStream.drawImage(logo, 50, 750, logo.getWidth() / 2, logo.getHeight() / 2); // Modifier les coordonnées et la taille selon vos besoins

            // Définir la police et la taille du texte
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

            // Écrire le titre du document
            String title = "Liste des voitures";
            float titleWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(title) / 1000 * 12; // Largeur du titre en points
            float titleX = (PDRectangle.A4.getWidth() - titleWidth) / 2; // Centre du document
            contentStream.beginText();
            contentStream.newLineAtOffset(titleX, 700);
            contentStream.showText(title);
            contentStream.endText();

            // Obtenir la liste des voitures
            List<voiture> voitures = vs.getAll();

            // Définir la police et la taille du texte pour les informations des voitures
            contentStream.setFont(PDType1Font.HELVETICA, 10);

            // Position verticale initiale pour les informations des voitures
            float yPosition = 680;

            for (voiture v : voitures) {
                String voitureInfo = "Immatriculation: " + v.getImmatriculation() + "\n" +
                        "Modèle: " + v.getModele() + "\n" +
                        "Nombre de places: " + v.getNbr_places() + "\n" +
                        "Couleur: " + v.getCouleur() + "\n" +
                        "Prix de location: " + v.getPrixdelocation() + "\n" +
                        "Image Path: " + v.getImagePath() + "\n" +
                        "Nom agence: " + v.getNom_agence() + "\n\n";

                String[] lines = voitureInfo.split("\n");

                for (String line : lines) {
                    // Écrire chaque ligne d'information à la position verticale actuelle
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, yPosition);
                    contentStream.showText(line);
                    contentStream.endText();

                    // Déplacer la position verticale vers le bas
                    yPosition -= 12;
                }

                // Ajouter un espace entre chaque voiture
                yPosition -= 12;
            }

            // Ajouter la date et l'heure de téléchargement du fichier
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String downloadTime = "Téléchargé le : " + formatter.format(now);
            float downloadTimeWidth = PDType1Font.HELVETICA.getStringWidth(downloadTime) / 1000 * 10; // Largeur du texte en points
            float downloadTimeX = (PDRectangle.A4.getWidth() - downloadTimeWidth) / 2; // Centre du document
            contentStream.beginText();
            contentStream.newLineAtOffset(downloadTimeX, 50);
            contentStream.showText(downloadTime);
            contentStream.endText();

            // Fermer le contenu du flux
            contentStream.close();

            // Sauvegarder le document PDF
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            String timestamp = LocalDateTime.now().format(dtf);
            String fileName = "liste_voitures_" + timestamp + ".pdf";
            document.save(fileName);
            document.close();

            // Afficher une alerte pour indiquer que le PDF a été téléchargé avec succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Téléchargement réussi");
            alert.setHeaderText(null);
            alert.setContentText("Le fichier PDF a été téléchargé avec succès !");
            alert.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void exportToExcel(ActionEvent event) {
        List<voiture> voitures = vs.getAll();

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Voitures");

        // Création de l'en-tête
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Immatriculation");
        headerRow.createCell(1).setCellValue("Modèle");
        headerRow.createCell(2).setCellValue("Nombre de places");
        headerRow.createCell(3).setCellValue("Couleur");
        headerRow.createCell(4).setCellValue("Prix de location");
        headerRow.createCell(5).setCellValue("Nom agence");

        // Remplissage des données
        int rowNum = 1;
        for (voiture voiture : voitures) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(voiture.getImmatriculation());
            row.createCell(1).setCellValue(voiture.getModele());
            row.createCell(2).setCellValue(voiture.getNbr_places());
            row.createCell(3).setCellValue(voiture.getCouleur());
            row.createCell(4).setCellValue(voiture.getPrixdelocation());
            row.createCell(5).setCellValue(voiture.getNom_agence());
        }

        // Ajuster la largeur des colonnes
        for (int i = 0; i < 6; i++) {
            sheet.autoSizeColumn(i);
        }

        // Enregistrer le fichier Excel
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime now = LocalDateTime.now();
        String fileName = "voitures_" + dtf.format(now) + ".xlsx";

        try (OutputStream outputStream = new FileOutputStream(fileName)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Export vers Excel");
        alert.setHeaderText(null);
        alert.setContentText("Les données ont été exportées vers le fichier " + fileName);
        alert.showAndWait();
    }

    @FXML
    public void versAjout(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle scène
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterVoitures.fxml"));
            Parent root = loader.load();

            // Récupérer la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacer le contenu de la scène actuelle par le nouveau Parent
            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    public void versAjoutAgences(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle scène
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterAgences.fxml"));
            Parent root = loader.load();

            // Récupérer la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacer le contenu de la scène actuelle par le nouveau Parent
            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }
