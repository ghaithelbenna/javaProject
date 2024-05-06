package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import models.agencedelocation;
import models.voiture;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import services.agenceServices;

public class afficherAgencesController {

    @FXML
    private Button pdfButton;

    private final agenceServices as = new agenceServices();

    @FXML
    private ListView<agencedelocation> listView;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize() {
        afficherAgences();
        pdfButton.setOnAction(this::exportToPDF);
    }

    public void afficherAgences() {
        ObservableList<agencedelocation> agences = as.getAllAgences();
        listView.setItems(agences);

        listView.setCellFactory(param -> new ListCell<agencedelocation>() {
            @Override
            protected void updateItem(agencedelocation item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    String agenceInfo = "Nom: " + item.getNom_agence() + "\n" +
                            "Adresse: " + item.getAdresse_agence() + "\n" +
                            "Nombre de voitures disponibles: " + item.getNbrvoitures_dispo();
                    setText(agenceInfo);
                }
            }
        });

        listView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                agencedelocation agence = listView.getSelectionModel().getSelectedItem();
                if (agence != null) {
                    openModifierAgenceWindow(event, agence);
                }
            }
        });
    }

    public void openModifierAgenceWindow(MouseEvent event, agencedelocation agence) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateAgences.fxml"));
            Parent root = loader.load();

            updateAgencesController controller = loader.getController();
            controller.setAgencedelocation(agence);
            controller.setAfficherAgencesController(this);

            Stage stage = new Stage();
            stage.setTitle("Modifier Agence");
            stage.setScene(new Scene(root));
            stage.show();

            //Stage currentStage = (Stage) listView.getScene().getWindow();
            //currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
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
            String title = "Liste des Agences";
            float titleWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(title) / 1000 * 12; // Largeur du titre en points
            float titleX = (PDRectangle.A4.getWidth() - titleWidth) / 2; // Centre du document
            contentStream.beginText();
            contentStream.newLineAtOffset(titleX, 700);
            contentStream.showText(title);
            contentStream.endText();

            // Obtenir la liste des agences
            List<agencedelocation> agences = as.getAllAgences();

            // Définir la police et la taille du texte pour les informations des agences
            contentStream.setFont(PDType1Font.HELVETICA, 10);

            // Position verticale initiale pour les informations des agences
            float yPosition = 680;

            for (agencedelocation as : agences) {
                String agencedelocationInfo = "Nom de l'agence: " + as.getNom_agence() + "\n" +
                        "Adresse : " + as.getAdresse_agence() + "\n" +
                        "Nombre de voitures disponibles: " + as.getNbrvoitures_dispo() + "\n\n";

                String[] lines = agencedelocationInfo.split("\n");

                for (String line : lines) {
                    // Écrire chaque ligne d'information à la position verticale actuelle
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, yPosition);
                    contentStream.showText(line);
                    contentStream.endText();

                    // Déplacer la position verticale vers le bas
                    yPosition -= 12;
                }

                // Ajouter un espace entre chaque agence
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
            String fileName = "liste_agences_" + now.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".pdf";
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
        List<agencedelocation> agences = as.getAllAgences();

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Agences");

        // Création de l'en-tête
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Nom de l'agence");
        headerRow.createCell(1).setCellValue("Adresse");
        headerRow.createCell(2).setCellValue("Nombre de voitures disponibles");


        // Remplissage des données
        int rowNum = 1;
        for (agencedelocation agencedelocation : agences) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(agencedelocation.getNom_agence());
            row.createCell(1).setCellValue(agencedelocation.getAdresse_agence());
            row.createCell(2).setCellValue(agencedelocation.getNbrvoitures_dispo());

        }

        // Ajuster la largeur des colonnes
        for (int i = 0; i < 6; i++) {
            sheet.autoSizeColumn(i);
        }

        // Enregistrer le fichier Excel
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime now = LocalDateTime.now();
        String fileName = "Agences_" + dtf.format(now) + ".xlsx";

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
    void ajoutAgenceBTN(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterAgences.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Ajouter Agence");
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}