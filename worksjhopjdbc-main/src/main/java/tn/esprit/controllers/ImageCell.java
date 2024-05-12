import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.models.Pack;

import java.io.ByteArrayInputStream;
import java.util.Base64;

public class ImageCell extends TableCell<Pack, String> {
    private final ImageView imageView = new ImageView();

    @Override
    protected void updateItem(String imageData, boolean empty) {
        super.updateItem(imageData, empty);
        if (empty || imageData == null) {
            setGraphic(null);
        } else {
            // Décoder la chaîne Base64 en données binaires
            byte[] imageDataBytes = Base64.getDecoder().decode(imageData);

            // Charger l'image à partir des données binaires et l'afficher dans l'ImageView
            Image image = new Image(new ByteArrayInputStream(imageDataBytes));
            imageView.setImage(image);
            imageView.setFitWidth(50); // Définir la largeur souhaitée pour l'image
            imageView.setFitHeight(50); // Définir la hauteur souhaitée pour l'image
            setGraphic(imageView);
        }
    }
}
