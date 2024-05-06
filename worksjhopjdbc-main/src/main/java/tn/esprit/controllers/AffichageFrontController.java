// AffichageFrontController.java
package tn.esprit.controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import tn.esprit.models.Pack;
import tn.esprit.models.packPersonnaliser;
import tn.esprit.services.ServicePack;
import tn.esprit.services.packPersonnaliserService;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.List;

public class AffichageFrontController implements Initializable {

    @FXML
    private Label selectedPacksLabel;
    @FXML
    private TilePane biensContainer;
    @FXML
    private Pagination pagination;
    private List<Pack> selectedPacks = new ArrayList<>();
    PaiementController paiementController = new PaiementController();

    private final ServicePack servicePack = new ServicePack();
    private List<Pack> packs;
    private static final int ITEMS_PER_PAGE = 8;
    private double cardWidth = 180; // Largeur fixe pour chaque carte
    private double cardHeight = 300; // Hauteur fixe pour chaque carte

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
            bienCard.setPrefSize(cardWidth, cardHeight);

            VBox bienBox = new VBox();
            bienBox.setSpacing(10);
            bienBox.setPadding(new Insets(10));

            ImageView imageView = new ImageView();
            imageView.setFitWidth(cardWidth - 20); // Largeur ajustée
            imageView.setFitHeight(cardHeight - 20); // Hauteur ajustée
            imageView.setPreserveRatio(true);
            try {
                String imagePath = pack.getImage();
                if (imagePath != null && !imagePath.isEmpty()) {
                    // Charger l'image depuis le chemin
                    javafx.scene.image.Image image = new javafx.scene.image.Image("file:" + imagePath);
                    imageView.setImage(image);
                } else {
                    // Charger l'image blanche depuis les ressources
                    InputStream blankImageStream = getClass().getResourceAsStream("/icons/blanc.png");
                    javafx.scene.image.Image blankImage = new javafx.scene.image.Image(blankImageStream);
                    imageView.setImage(blankImage);
                }
            } catch (Exception e) {
                System.out.println("Erreur lors du chargement de l'image : " + e.getMessage());
            }

            Label nomPackLabel = new Label("Nom : " + pack.getNomPack());
            Label prixLabel = new Label("Prix : " + pack.getPrix());
            Label typeLabel = new Label("Type : " + pack.getTypePack().getNomTypePack());

            HBox disponibiliteBox = new HBox();
            disponibiliteBox.setSpacing(5);
            Label disponibiliteLabel = new Label(pack.isDisponible() ? "Disponible" : "Non Disponible");

            ImageView icon = iconesMap.get(pack.getId());
            icon.setFitHeight(20);
            icon.setFitWidth(20);

            disponibiliteBox.getChildren().addAll(icon, disponibiliteLabel);

            CheckBox checkBox = new CheckBox("Sélectionner");
            checkBox.setSelected(selectedPacks.contains(pack));

            checkBox.setOnAction(event -> {
                if (checkBox.isSelected()) {
                    selectedPacks.add(pack);
                } else {
                    selectedPacks.remove(pack);
                }
                updateSelectedPacksLabel();
            });

            bienBox.getChildren().addAll(imageView, nomPackLabel, prixLabel, typeLabel, disponibiliteBox, checkBox);
            bienCard.getChildren().addAll(bienBox);
            biensContainer.getChildren().add(bienCard);
        }
    }

    @FXML
    private void reservePack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/paiement.fxml"));
            Parent paiementView = loader.load();
            PaiementController paiementController = loader.getController();
            paiementController.setMontant(calculerMontantTotal()); // Assigner le montant total

            // Récupérer la scène actuelle
            Scene scene = selectedPacksLabel.getScene();

            // Changer la racine de la scène pour afficher la page de paiement
            scene.setRoot(paiementView);
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les erreurs de chargement de la vue de paiement...
        }
    }


    private float calculerMontantTotal() {
        float montantTotal = 0.0F;
        for (Pack pack : selectedPacks) {
            montantTotal += pack.getPrix();
        }
        return (float) montantTotal;
    }

    private void uncheckAllCheckBoxes() {
        for (Node node : biensContainer.getChildren()) {
            if (node instanceof StackPane) {
                StackPane bienCard = (StackPane) node;
                for (Node innerNode : bienCard.getChildren()) {
                    if (innerNode instanceof VBox) {
                        VBox bienBox = (VBox) innerNode;
                        for (Node boxNode : bienBox.getChildren()) {
                            if (boxNode instanceof CheckBox) {
                                CheckBox checkBox = (CheckBox) boxNode;
                                checkBox.setSelected(false);
                            }
                        }
                    }
                }
            }
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
