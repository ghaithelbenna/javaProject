package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import tn.esprit.models.Pack;
import tn.esprit.services.ServicePack;

import java.net.URL;
import java.util.*;

public class AffichageFrontController implements Initializable {
    @FXML
    private Label selectedPacksLabel;
    @FXML
    private TilePane biensContainer;
    @FXML
    private Pagination pagination;
    private List<Pack> selectedPacks = new ArrayList<>();

    private final ServicePack servicePack = new ServicePack();
    private List<Pack> packs;
    private static final int ITEMS_PER_PAGE = 8;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            packs = servicePack.afficherListe();
            if (!packs.isEmpty()) {
                configurePagination();
                updateDisplayedPacks(0);
            } else {
                System.out.println("La liste de packs est vide.");
            }
        } catch (Exception e) {
            System.err.println("Une erreur s'est produite lors de l'initialisation : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateDisplayedPacks(int pageIndex) {
        Map<Integer, ImageView> iconesMap = servicePack.mettreAJourPrixEtIcônes(packs);
        biensContainer.getChildren().clear();
        int startIndex = pageIndex * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, packs.size());
        List<Pack> packsToDisplay = packs.subList(startIndex, endIndex);
        addPacksToRow(packsToDisplay, iconesMap);
    }

    private void addPacksToRow(List<Pack> rowPacks, Map<Integer, ImageView> iconesMap) {
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
                    // Charger l'image depuis le chemin
                    imageView.setImage(new javafx.scene.image.Image("file:" + imagePath));
                }
            } catch (Exception e) {
                System.out.println("Erreur lors du chargement de l'image : " + e.getMessage());
            }

            Label nomPackLabel = new Label("Nom : " + pack.getNomPack());
            Label prixLabel = new Label("Prix : " + pack.getPrix());
            Label typeLabel = new Label("Type : " + pack.getTypePack().getNomTypePack());
            Label disponibiliteLabel = new Label("Disponible : " + (pack.isDisponible() ? "Oui" : "Non"));

            // Récupérer l'icône correspondant à ce pack depuis la Map
            ImageView icon = iconesMap.get(pack.getId());
            icon.setFitHeight(20);
            icon.setFitWidth(20);

            CheckBox checkBox = new CheckBox("Sélectionner");
            if (selectedPacks.contains(pack)) {
                checkBox.setSelected(true);
            }

            checkBox.setOnAction(event -> {
                if (checkBox.isSelected()) {
                    selectedPacks.add(pack);
                } else {
                    selectedPacks.remove(pack);
                }
                updateSelectedPacksLabel();
            });

            bienBox.getChildren().addAll(imageView, nomPackLabel, prixLabel, typeLabel, disponibiliteLabel, checkBox);
            bienBox.getStyleClass().add("bien-details");
            StackPane.setMargin(bienBox, new Insets(10));

            bienCard.getChildren().addAll(bienBox, icon);
            biensContainer.getChildren().add(bienCard);
        }
    }

    private void configurePagination() {
        int pageCount = (int) Math.ceil((double) packs.size() / ITEMS_PER_PAGE);
        pagination.setPageCount(pageCount);
        pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndexValue) -> {
            int newIndex = newIndexValue.intValue();
            updateDisplayedPacks(newIndex);
            updateSelectedPacksLabel();
        });
    }

    private void updateSelectedPacksLabel() {
        selectedPacksLabel.setText("Packs sélectionnés : " + selectedPacks.size());
    }
}
