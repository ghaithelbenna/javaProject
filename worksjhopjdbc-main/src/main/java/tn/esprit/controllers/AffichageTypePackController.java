package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tn.esprit.controllers.ItempkController;
import tn.esprit.models.typePack;
import tn.esprit.services.TypePackService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AffichageTypePackController implements Initializable {

    @FXML
    private VBox pkItems;

    @FXML
    private TextField searchField;

    private final TypePackService typePackService = new TypePackService();
    private ObservableList<typePack> typePacks = FXCollections.observableArrayList();

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Chargez les types de packs initialement
        loadTypePacks();

        // Ajoutez un écouteur au champ de recherche
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterTypePacks(newValue));
    }

    private void loadTypePacks() {
        typePacks.setAll(typePackService.getAll());
        affichage();
    }

    public void affichage() {
        pkItems.getChildren().clear();
        for (typePack typePack : typePacks) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Itempk.fxml"));
                HBox node = loader.load();

                ItempkController itemController = loader.getController();
                itemController.initData(typePack);

                // Passer la référence à AffichageTypePackController à ItempkController
                itemController.setAffichageTypePackController(this);

                pkItems.getChildren().add(node);
            } catch (IOException ex) {
                ex.printStackTrace(); // Affiche l'erreur dans la console
            }
        }
    }

    private void filterTypePacks(String keyword) {
        ObservableList<typePack> filteredTypePacks = FXCollections.observableArrayList();
        if (keyword == null || keyword.trim().isEmpty()) {
            filteredTypePacks.addAll(typePacks);
        } else {
            filteredTypePacks.addAll(typePacks.stream()
                    .filter(tp -> tp.getNomTypePack().toLowerCase().contains(keyword.toLowerCase()))
                    .collect(Collectors.toList()));
        }
        pkItems.getChildren().clear();
        for (typePack typePack : filteredTypePacks) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Itempk.fxml"));
                HBox node = loader.load();

                ItempkController itemController = loader.getController();
                itemController.initData(typePack);

                // Passer la référence à AffichageTypePackController à ItempkController
                itemController.setAffichageTypePackController(this);

                pkItems.getChildren().add(node);
            } catch (IOException ex) {
                ex.printStackTrace(); // Affiche l'erreur dans la console
            }
        }
    }


    private void navigate(String fxmlFile, EventObject event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void afficherPack(ActionEvent actionEvent) throws IOException {
        navigate("/AfficherPack.fxml", actionEvent);
    }

    public void ajouterPack(ActionEvent actionEvent) throws IOException {
        navigate("/Pack.fxml", actionEvent);
    }

    public void affichercategorie(ActionEvent actionEvent) throws IOException {
        navigate("/affichageCategorie.fxml", actionEvent);
    }

    public void ajoutercategorie(ActionEvent actionEvent) throws IOException {
        navigate("/AjoutCategorie.fxml", actionEvent);
    }

    public void affichertypePack(ActionEvent actionEvent) throws IOException {
        navigate("/AffichageTypePack.fxml", actionEvent);
    }

    public void ajoutertypePack(ActionEvent actionEvent) throws IOException {
        navigate("/ajoutTypePack.fxml", actionEvent);
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
    @FXML
    void exportToExcel(ActionEvent event) {
        String filePath = "type_packs.xlsx";

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Type Packs");

            // Style pour les en-têtes
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.ORANGE.getIndex()); // Set font color to orange
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER); // Center-align the text

            // Créer l'en-tête
            Row headerRow = sheet.createRow(0);
            Cell headerCell = headerRow.createCell(0);
            headerCell.setCellValue("Type Pack");
            headerCell.setCellStyle(headerStyle);

            // Remplir les données
            List<typePack> typePacks = typePackService.getAll();
            int rowNum = 1;
            CellStyle dataStyle = workbook.createCellStyle();
            for (typePack typePack : typePacks) {
                Row row = sheet.createRow(rowNum++);
                Cell cell = row.createCell(0);
                cell.setCellValue(typePack.getNomTypePack());
                cell.setCellStyle(dataStyle);
            }

            // Ajuster la largeur de la colonne
            sheet.autoSizeColumn(0);

            // Sauvegarder le classeur dans un fichier
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                System.out.println("Le fichier Excel a été créé avec succès !");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
