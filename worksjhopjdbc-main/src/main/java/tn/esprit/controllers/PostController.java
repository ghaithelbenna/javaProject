package tn.esprit.controllers;

import tn.esprit.test.Main;
import tn.esprit.models.Post;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.services.EmailServices;
import tn.esprit.services.PdfGeneratorService;
import tn.esprit.services.ServicePost;

import javax.mail.MessagingException;

public class PostController implements Initializable {

    @FXML
    private DatePicker tfDate;
    @FXML
    private TableView<Post> tableViewPosts;
    @FXML
    private TableColumn<Post, String> colDescription;
    @FXML
    private TableColumn<Post, String> colImage;
    @FXML
    private TableColumn<Post, String> colHashtag;
    @FXML
    private TableColumn<Post, String> colDate;
    @FXML
    private BarChart<?, ?> likesChart;
    @FXML
    private TextArea taDescription;

    @FXML
    private TextField tfHashtag;
    @FXML
    private AnchorPane pane;
    File file;

    private ServicePost servicePost;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        servicePost = new ServicePost(); // Instancier votre service ici

        // Configurer les colonnes du TableView
        colHashtag.setCellValueFactory(new PropertyValueFactory<>("hashtag"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colImage.setCellValueFactory(new PropertyValueFactory<>("image_p"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date_p"));
        tfDate.setValue(LocalDate.now());
        // Charger les publications initiales
        chargerPosts();
        setChartData();
    }

    @FXML
    private void ajouterPost(ActionEvent event) {
        String description = taDescription.getText();
        String hashtag = tfHashtag.getText();
        Date date = java.sql.Date.valueOf(tfDate.getValue());
        String image="";
        Post post = new Post(description, image, hashtag, date, 0);

        if (validateInput(hashtag)) {
            // Créer un objet Post
            if (file != null) {
                try {
                    String destinationDirectory = "C:/Users/Mrrae/Desktop/Pijava1/gestion_Raed/src/main/resources/blogImages/";

                    File destDir = new File(destinationDirectory);
                    if (!destDir.exists()) {
                        destDir.mkdirs();
                    }


                        String fileName = file.getName();
                        Path destinationPath = Paths.get(destinationDirectory + fileName);
                        Files.copy(file.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

                        post.setImage_p(fileName);
                        System.out.println("File saved to: " + destinationPath);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // Appeler la méthode d'ajout du service
            servicePost.ajouter(post);

            // Rafraîchir le TableView
            chargerPosts();

            // Effacer les champs de saisie
            taDescription.clear();
            tfHashtag.clear();

            EmailServices emailServices = new EmailServices();
            try {
                emailServices.sendEmail("nasri.raed@esprit.tn", post.getHashtag());
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private boolean validateInput(String hashtag) {
        boolean isValid = true;

        // Vérifier si le champ hashtag commence par #
        if (!hashtag.startsWith("#")) {
            isValid = false;
            Alert alert = new Alert(Alert.AlertType.ERROR, "Le champ Hashtag doit commencer par #.", ButtonType.OK);
            alert.showAndWait();
        }

        return isValid;
    }

    @FXML
    private void modifierPost(ActionEvent event) {
        // Récupérer la publication sélectionnée dans le TableView
        Post selectedPost = tableViewPosts.getSelectionModel().getSelectedItem();
        if (selectedPost != null) {
            // Mettre à jour les attributs de la publication avec les valeurs saisies
            selectedPost.setDescription(taDescription.getText());
            //selectedPost.setImage_p(tfImage.getText());
            selectedPost.setHashtag(tfHashtag.getText());
            selectedPost.setDate_p(java.sql.Date.valueOf(tfDate.getValue()));

            if (validateInput( selectedPost.getHashtag())) {
                // Appeler la méthode de modification du service
                servicePost.modifier(selectedPost);

                // Rafraîchir le TableView
                chargerPosts();

                // Effacer les champs de saisie
                taDescription.clear();
                //tfImage.clear();
                tfHashtag.clear();
            }
        } else {
            // Afficher un message d'alerte si aucune publication n'est sélectionnée
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner une publication à modifier.", ButtonType.OK);
            alert.showAndWait();
        }
    }



    @FXML
    private void supprimerPost(ActionEvent event) {
        // Récupérer la publication sélectionnée dans le TableView
        Post selectedPost = tableViewPosts.getSelectionModel().getSelectedItem();
        if (selectedPost != null) {
            // Demander confirmation avant de supprimer la publication
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Êtes-vous sûr de vouloir supprimer cette publication ?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                // Appeler la méthode de suppression du service
                servicePost.supprimer(selectedPost.getIdPost());

                // Rafraîchir le TableView
                chargerPosts();

                // Effacer les champs de saisie
                taDescription.clear();
                //tfImage.clear();
                tfHashtag.clear();
            }
        } else {
            // Afficher un message d'alerte si aucune publication n'est sélectionnée
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner une publication à supprimer.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    private void chargerPosts() {
        List<Post> posts = servicePost.listerPosts();
        tableViewPosts.setItems(FXCollections.observableArrayList(posts));
    }

    @FXML
    private void allerACommentaire(ActionEvent actionEvent) throws IOException {

            Post selectedPost = tableViewPosts.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLCommentaire.fxml"));
            Parent root = loader.load();
            CommentaireController controller = loader.getController();
            controller.setPane(pane);
            controller.setData(selectedPost.getIdPost(),selectedPost.getHashtag());
            pane.getChildren().clear();
            pane.getChildren().setAll(root);


    }

    @FXML
    private void chooseImage(javafx.event.ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        //fileChooser.setInitialDirectory(new File("C:/Users/manna/OneDrive/Bureau/test"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPEG Files", ".jpeg", ".jpg"),
                new FileChooser.ExtensionFilter("PNG Files", "*.png")
        );
        Stage stage = (Stage) pane.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);


    }

    public void pdf(ActionEvent event) throws IOException {
        Post selectedPost = tableViewPosts.getSelectionModel().getSelectedItem();
        // Create a file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF File");

        // Set initial directory
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(null);

        // Check if a file was selected
        if (file != null) {
            // User selected a file, generate PDF
            PdfGeneratorService pdfGeneratorService = new PdfGeneratorService();
            pdfGeneratorService.generatePdf(file.getAbsolutePath(), selectedPost);
        }}

    public void setChartData(){
        ServicePost servicePost = new ServicePost();
        List<Post> posts= servicePost.listerPosts();
        XYChart.Series series = new XYChart.Series();
        for (Post post : posts) {
            series.getData().add(new XYChart.Data(String.valueOf(post.getIdPost()),post.getLikes()));
        }
        series.setName("likes in posts");
        likesChart.getData().add(series);
    }


}
