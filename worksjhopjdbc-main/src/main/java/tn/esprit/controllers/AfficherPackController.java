package tn.esprit.controllers;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import tn.esprit.models.Pack;
import tn.esprit.services.ServicePack;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherPackController implements Initializable {

    @FXML
    private VBox pnItems;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    private final ServicePack sp = new ServicePack();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        affichage();
        searchButton.setOnAction(this::search);
    }

    public void affichage() {
        pnItems.getChildren().clear();

        Button genererPDFButton = new Button("Générer PDF");
        genererPDFButton.setOnAction(this::genererPDF);

        List<Pack> lv = sp.afficherListe();

        for (Pack pack : lv) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ItemP.fxml"));
                HBox node = loader.load();

                ItemPController itemController = loader.getController();
                itemController.initData(pack, this);

                pnItems.getChildren().add(node);
            } catch (IOException ex) {
                ex.printStackTrace();
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

    public void afficherCategorie(ActionEvent actionEvent) throws IOException {
        navigate("/affichageCategorie.fxml", actionEvent);
    }

    public void ajouterCategorie(ActionEvent actionEvent) throws IOException {
        navigate("/AjoutCategorie.fxml", actionEvent);
    }

    public void afficherTypePack(ActionEvent actionEvent) throws IOException {
        navigate("/AffichageTypePack.fxml", actionEvent);
    }

    public void ajouterTypePack(ActionEvent actionEvent) throws IOException {
        navigate("/ajoutTypePack.fxml", actionEvent);
    }

    @FXML
    void genererPDF(ActionEvent event) {
        // Récupérer les données de la table "Pack"
        ServicePack servicePack = new ServicePack();
        List<Pack> packs = servicePack.afficherListe();

        // Créer un nouveau document PDF
        PDDocument document = new PDDocument();

        // Ajouter une nouvelle page au document
        PDPage page = new PDPage();
        document.addPage(page);

        try {
            // Initialiser le contenu du PDF
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Charger le logo depuis un fichier image
            PDImageXObject logoImage = PDImageXObject.createFromFile("C:\\Users\\user\\IdeaProjects\\2rism\\worksjhopjdbc-main\\src\\main\\resources\\logo.jpg", document);

            // Insérer le logo dans le document
            float logoX = 50; // Coordonnée X du logo
            float logoY = 750; // Coordonnée Y du logo
            float logoWidth = 100; // Largeur du logo
            float logoHeight = 100; // Hauteur du logo
            contentStream.drawImage(logoImage, logoX, logoY, logoWidth, logoHeight);

            // Définir la police et la taille du texte du titre de l'application
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);

            // Écrire le nom de l'application
            contentStream.beginText();
            float titleX = 180; // Coordonnée X du titre de l'application
            float titleY = 780; // Coordonnée Y du titre de l'applicationcontentStream.newLineAtOffset(titleX, titleY);
            contentStream.showText("Liste des packs");
            contentStream.endText();

            // Définir la police et la taille du texte du contenu
            contentStream.setFont(PDType1Font.HELVETICA, 12);

            // Écrire les données des packs dans le document
            float contentX = 50; // Coordonnée X du contenu
            float contentY = 700; // Coordonnée Y du contenu
            float lineHeight = 20; // Hauteur de ligne
            for (Pack pack : packs) {
                contentStream.beginText();
                contentStream.newLineAtOffset(contentX, contentY);
                contentStream.showText("ID: " + pack.getId());
                contentStream.newLineAtOffset(0, -lineHeight);
                contentStream.showText("Nom: " + pack.getNomPack());
                contentStream.newLineAtOffset(0, -lineHeight);
                contentStream.showText("Description: " + pack.getDescriptionPack());
                contentStream.newLineAtOffset(0, -lineHeight);
                contentStream.showText("Prix: " + pack.getPrix());
                contentStream.newLineAtOffset(0, -lineHeight);
                contentStream.endText();
                contentY -= 4 * lineHeight;
            }

            // Fermer le contenu du PDF
            contentStream.close();

            // Sauvegarder le document PDF
            document.save("C:\\Users\\user\\IdeaProjects\\2rism\\worksjhopjdbc-main\\src\\main\\resources\\packs.pdf");
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void search(ActionEvent event) {
        String keyword = searchField.getText();
        List<Pack> searchResults = sp.search(keyword);
        pnItems.getChildren().clear();
        for (Pack pack : searchResults) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ItemP.fxml"));
                HBox node = loader.load();

                ItemPController itemController = loader.getController();
                itemController.initData(pack, this);

                pnItems.getChildren().add(node);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}