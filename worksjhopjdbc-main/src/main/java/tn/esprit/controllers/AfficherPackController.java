package tn.esprit.controllers;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;

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
import tn.esprit.utils.TriCritere;


import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

public class AfficherPackController implements Initializable {

    @FXML
    private VBox pnItems;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    private final ServicePack sp = new ServicePack();
    private ToggleGroup toggleGroup;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toggleGroup = new ToggleGroup();
        prixRadioButton.setToggleGroup(toggleGroup);
        dateRadioButton.setToggleGroup(toggleGroup);
        disponibiliteRadioButton.setToggleGroup(toggleGroup);
        // Appeler la méthode pour ajuster les prix des packs selon la saison
        ajusterPrixPacksSaison(new Date());
        affichage();

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
    void Statistique(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page des statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Statistiques.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Remplacer la scène actuelle par la nouvelle scène des statistiques
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void genererPDF(ActionEvent event) {
        // Récupérer les données de la table "Pack"
        ServicePack servicePack = new ServicePack();
        List<Pack> packs = servicePack.afficherListe();

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Charger le logo
                PDImageXObject logo = PDImageXObject.createFromFile("C:\\Users\\user\\javaProject\\worksjhopjdbc-main\\src\\main\\resources\\logo.jpg", document);

                // Dessiner le logo
                contentStream.drawImage(logo, 50, 750, logo.getWidth() / 2, logo.getHeight() / 2);

                // Définir le titre
                String title = "Liste des packs";
                contentStream.setNonStrokingColor(Color.BLUE);
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
                float titleWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(title) / 1000f * 20;
                float titleX = (page.getMediaBox().getWidth() - titleWidth) / 2;
                float titleY = 750;
                contentStream.beginText();
                contentStream.newLineAtOffset(titleX, titleY);
                contentStream.showText(title);
                contentStream.endText();

                // Dessiner l'en-tête du tableau
                float tableWidth = page.getMediaBox().getWidth() - 100;
                float tableY = titleY - 50;
                drawTableHeader(contentStream, tableWidth, tableY);

                // Dessiner les données des packs dans le tableau
                float tableContentY = tableY - 20;
                drawTableContent(contentStream, packs, tableWidth, tableContentY);
            }

            document.save("C:\\Users\\user\\IdeaProjects\\2rism\\packs.pdf");
            System.out.println("Le PDF a été généré avec succès.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la génération du PDF : " + e.getMessage());
        }
    }

    // Dessiner l'en-tête du tableau
    private static void drawTableHeader(PDPageContentStream contentStream, float tableWidth, float tableY) throws IOException {
        contentStream.setNonStrokingColor(Color.LIGHT_GRAY);
        contentStream.fillRect(50, tableY, tableWidth, 20);

        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(80, tableY + 5);
        contentStream.showText("Type Pack");
        contentStream.newLineAtOffset(170, 0);
        contentStream.showText("Nom");
        contentStream.newLineAtOffset(200, 0);
        contentStream.showText("Description");

        contentStream.endText();
    }

    // Dessiner les données des packs dans le tableau
    private static void drawTableContent(PDPageContentStream contentStream, List<Pack> packs, float tableWidth, float tableContentY) throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        float lineHeight = 15;
        for (Pack pack : packs) {
            contentStream.beginText();
            contentStream.newLineAtOffset(80, tableContentY);
            contentStream.showText(pack.getTypePack().getNomTypePack());
            contentStream.newLineAtOffset(170, 0);
            contentStream.showText(pack.getNomPack());
            contentStream.newLineAtOffset(200, 0);
            contentStream.showText(pack.getDescriptionPack());
            contentStream.endText();
            tableContentY -= lineHeight;
        }
    }




    @FXML
    void search(KeyEvent event) {
        String keyword = searchField.getText().toLowerCase();
        List<Pack> allPacks = sp.afficherListe();
        pnItems.getChildren().clear();

        for (Pack pack : allPacks) {
            // Vérifier si le pack correspond au critère de recherche
            if (pack.getNomPack().toLowerCase().contains(keyword) ||
                    String.valueOf(pack.getPrix()).contains(keyword) ||
                    (pack.getTypePack() != null && pack.getTypePack().getNomTypePack().toLowerCase().contains(keyword)) ||
                    (pack.getDate() != null && pack.getDate().toString().toLowerCase().contains(keyword))) {

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ItemP.fxml"));
                    HBox node = loader.load();

                    ItemPController itemController = loader.getController();
                    itemController.initData(pack, this);

                    pnItems.getChildren().add(node); // Ajouter le pack à la liste affichée
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @FXML
    private RadioButton prixRadioButton;

    @FXML
    private RadioButton dateRadioButton;

    @FXML
    private RadioButton disponibiliteRadioButton;

    @FXML
    void triParPrix(ActionEvent event) {
        trierPacks(TriCritere.PRIX, true); // Tri croissant par prix
    }

    @FXML
    void triParDate(ActionEvent event) {
        trierPacks(TriCritere.DATE, true); // Tri croissant par date
    }

    @FXML
    void triParDisponibilite(ActionEvent event) {
        trierPacks(TriCritere.DISPO, true); // Tri croissant par disponibilité
    }

    private void trierPacks(TriCritere triCritere, boolean croissant) {
        // Récupérer la liste des packs à partir de votre service ou de toute autre source de données
        List<Pack> packs = sp.afficherListe();

        // Définir le comparateur en fonction du critère de tri sélectionné
        Comparator<Pack> comparator = null;
        switch (triCritere) {
            case PRIX:
                comparator = Comparator.comparing(Pack::getPrix);
                break;
            case DATE:
                comparator = Comparator.comparing(Pack::getDate);
                break;
            case DISPO:
                comparator = Comparator.comparing(Pack::isDisponible);
                break;
            default:
                // Gérer les cas non pris en charge, si nécessaire
                break;
        }

        // Inverser l'ordre du tri si nécessaire
        if (!croissant) {
            comparator = comparator.reversed();
        }

        // Trier la liste des packs en utilisant le comparateur
        packs.sort(comparator);

        // Effacer les éléments actuels de la liste
        pnItems.getChildren().clear();

        // Afficher les packs triés
        for (Pack pack : packs) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ItemP.fxml"));
                HBox node = loader.load();

                ItemPController itemController = loader.getController();
                itemController.initData(pack, this);

                pnItems.getChildren().add(node); // Ajouter le pack à la liste affichée
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public void ajusterPrixPacksSaison(Date dateActuelle) {
        String saisonActuelle = determinerSaison(dateActuelle);

        // Récupérer tous les packs
        List<Pack> packs = sp.afficherListe();

        // Parcourir les packs pour ajuster les prix
        for (Pack pack : packs) {
            // Vérifiez si le pack est disponible
            if (pack.isDisponible()) {
                // Vérifiez si le type de pack appartient à la saison actuelle
                if (pack.getTypePack().getNomTypePack().equalsIgnoreCase(saisonActuelle)) { // Si c'est le cas, le prix reste le même
                    continue;
                } else {
                    // Sinon, vérifiez si le type de pack appartient à la saison précédente
                    if (typePackAppartientSaisonPrecedente(pack.getTypePack().getNomTypePack(), saisonActuelle)) {
                        // Si c'est le cas, appliquez une réduction de 60%
                        pack.setPrix(pack.getPrix() * 0.4); // 60% de réduction
                    }
                }
            }
        }
    }


    // Méthode pour déterminer la saison en fonction de la date
    public String determinerSaison(Date dateActuelle) {
        int mois = dateActuelle.getMonth() + 1; // Les mois en Java commencent à 0, donc on ajoute 1
        String saison;

        // Déterminez la saison en fonction du mois
        if (mois >= 3 && mois <= 5) {
            saison = "PRINTEMPS";
        } else if (mois >= 6 && mois <= 8) {
            saison = "ÉTÉ";
        } else if (mois >= 9 && mois <= 11) {
            saison = "AUTOMNE";
        } else {
            saison = "HIVER";
        }

        return saison;
    }

    // Méthode pour vérifier si le type de pack appartient à la saison précédente
    public boolean typePackAppartientSaisonPrecedente(String typePack, String saisonActuelle) {
        if (typePack.equalsIgnoreCase("HIVER") && saisonActuelle.equalsIgnoreCase("PRINTEMPS")) {
            return true;
        } else if (typePack.equalsIgnoreCase("PRINTEMPS") && saisonActuelle.equalsIgnoreCase("ÉTÉ")) {
            return true;
        } else if (typePack.equalsIgnoreCase("ÉTÉ") && saisonActuelle.equalsIgnoreCase("AUTOMNE")) {
            return true;
        } else if (typePack.equalsIgnoreCase("AUTOMNE") && saisonActuelle.equalsIgnoreCase("HIVER")) {
            return true;
        }
        return false;
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

    public void Ajoutpack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Pack.fxml"));
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