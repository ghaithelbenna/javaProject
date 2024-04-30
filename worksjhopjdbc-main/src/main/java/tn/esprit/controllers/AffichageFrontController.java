package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import tn.esprit.models.Pack;
import tn.esprit.services.ServicePack;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AffichageFrontController implements Initializable {

    @FXML
    private TilePane biensContainer;

    @FXML
    private Pagination pagination;

    private final ServicePack servicePack = new ServicePack();
    private List<Pack> packs;
    private static final int ITEMS_PER_PAGE = 8;
    private static final int PACKS_PER_ROW = 4;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            packs = getPacks();
            if (!packs.isEmpty()) {
                System.out.println("Nombre total de packs : " + packs.size());
                configurePagination();
                updateDisplayedPacks(0); // Afficher les packs de la première page au démarrage
            } else {
                System.out.println("La liste de packs est vide.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private void updateDisplayedPacks(int pageIndex) {
        System.out.println("Mise à jour des packs affichés pour la page " + pageIndex);
        biensContainer.getChildren().clear();
        int startIndex = pageIndex * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, packs.size());
        List<Pack> packsToDisplay = packs.subList(startIndex, endIndex);
        addPacksToRow(packsToDisplay);
    }


    private void configurePagination() {
        int pageCount = (int) Math.ceil((double) packs.size() / ITEMS_PER_PAGE);
        pagination.setPageCount(pageCount);
        System.out.println("Pagination configurée avec " + pageCount + " pages.");
        pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            int newIndexValue = newIndex.intValue();
            System.out.println("Changement de page vers la page " + newIndexValue);
            updateDisplayedPacks(newIndexValue);
        });
    }



    private void addPacksToRow(List<Pack> rowPacks) {
        for (Pack pack : rowPacks) {
            StackPane bienCard = new StackPane();
            bienCard.getStyleClass().add("bien-card");

            VBox bienBox = new VBox();
            bienBox.setSpacing(5);

            ImageView imageView = new ImageView();
            imageView.setFitWidth(100);
            imageView.setPreserveRatio(true);

            try {
                String imagePath = pack.getImage();
                if (imagePath != null && !imagePath.isEmpty()) {
                    Image image = new Image("file:" + imagePath);
                    imageView.setImage(image);
                }
            } catch (Exception e) {
                System.out.println("Erreur lors du chargement de l'image : " + e.getMessage());
            }

            Label nomPackLabel = new Label("Nom : " + pack.getNomPack());
            Label prixLabel = new Label("Prix : " + pack.getPrix());
            Label typeLabel = new Label("Type : " + pack.getTypePack());
            Label disponibiliteLabel = new Label("Disponible : " + (pack.isDisponible() ? "Oui" : "Non"));

            bienBox.getChildren().addAll(imageView, nomPackLabel, prixLabel, typeLabel, disponibiliteLabel);
            bienBox.getStyleClass().add("bien-details");
            StackPane.setMargin(bienBox, new Insets(10));

            bienCard.getChildren().add(bienBox);
            biensContainer.getChildren().add(bienCard);
            System.out.println("Ajout du pack : " + pack.getNomPack());
        }
    }


    private List<Pack> getPacks() throws SQLException {
        List<Pack> packs = servicePack.afficherListe();
        if (packs != null && !packs.isEmpty()) {
            System.out.println("Liste des packs récupérée avec succès.");
        } else {
            System.out.println("La liste des packs est vide ou n'a pas pu être récupérée.");
        }
        return packs;
    }
}
