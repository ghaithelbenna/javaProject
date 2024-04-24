package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.models.Pack;

import java.io.File;

public class CarteItemController {

    @FXML
    private ImageView imageView;

    @FXML
    private Label nomPack;

    @FXML
    private Label descriptionPack;

    @FXML
    private Label prix;

    @FXML
    private Label date;

    @FXML
    private Label disponible;

    private Pack pack;

    public void initData(Pack pack) {
        this.pack = pack;

        // Afficher les informations du pack dans les labels correspondants
        nomPack.setText(pack.getNomPack());
        descriptionPack.setText(pack.getDescriptionPack());
        prix.setText(String.valueOf(pack.getprix()));
        date.setText(String.valueOf(pack.getDate()));
        disponible.setText(pack.isDisponible() ? "Disponible" : "Non disponible");

        // Charger l'image du pack s'il y en a une
        if (pack.getImage() != null && !pack.getImage().isEmpty()) {
            File file = new File(pack.getImage());
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);
            }
        }
    }
}
