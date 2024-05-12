package tn.esprit.controllers;

import com.sun.speech.freetts.audio.SingleFileAudioPlayer;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import com.sun.speech.freetts.audio.AudioPlayer;

import javax.sound.sampled.AudioFileFormat;
import java.util.List;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import java.util.Locale;
import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;


import tn.esprit.models.Commentaire;
import tn.esprit.models.Post;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import tn.esprit.services.ServiceCommentaire;
import tn.esprit.services.ServicePost;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

public class BlogDetails {

    private Synthesizer synthesizer;

    @FXML
    private Button addCommentButton;

    @FXML
    private Button allActivitiesButton;
    @FXML
    private Pane pane;

    @FXML
    private TextArea comment;

    @FXML
    private VBox commentsVbox;

    @FXML
    private Text content;

    @FXML
    private Text desc;
    @FXML
    private ImageView image;

    @FXML
    private Button favButton;

    @FXML
    private AnchorPane imageSlider;

    @FXML
    private TextField rating;

    @FXML
    private Text title;
    private Post post;

    @FXML
    void navigateBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/blogs.fxml"));
        Parent fxml = loader.load();
        pane.getChildren().clear();
        pane.getChildren().setAll(fxml);
    }

    public void setData(Post post){
        Rectangle clip = new Rectangle(
                image.getFitWidth(), image.getFitHeight()
        );
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        image.setClip(clip);

        // Snapshot the rounded image
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage roundedImage = image.snapshot(parameters, null);

        image.setImage(roundedImage);
        //mainAnchor.getChildren().add(image);
        title.setText(post.getHashtag());
        desc.setText(post.getDescription());
        Image postImage = new Image("file:///C:/Users/Mrrae/Desktop/Pijava1/gestion_Raed/src/main/resources/blogImages/" + post.getImage_p());
        image.setImage(postImage);
        this.post = post;

        setComments();

        try {
            // Setting the system properties to use Kevin's dictionary of FreeTTS
            System.setProperty("freetts.voices",
                    "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

            // Registering the speech engine
            Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");

            // Create a Synthesizer that generates voice
            synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));

            // Allocates a synthesizer
            synthesizer.allocate();

            // Resume a Synthesizer
            synthesizer.resume();
        } catch (Exception e) {
            System.err.println("Error initializing Synthesizer: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setPane(Pane pane){
        this.pane=pane;
    }

    public void addComment(ActionEvent event){
        ServiceCommentaire commentService = new ServiceCommentaire();
        LocalDate localDate = LocalDate.now();
        Date date = java.sql.Date.valueOf(localDate);
        String filteredComment=filterBadWords(comment.getText());
        Commentaire commentaire=new Commentaire(post.getIdPost(), filteredComment, date);
        commentService.ajouter(commentaire);
        comment.clear();
        setComments();
    }

    public void like(ActionEvent event){
        ServicePost servicePost = new ServicePost();
        servicePost.like(post);
    }

    private String filterBadWords(String comment) {
        String[] badWords = {"hate", "shit", "fuck"};

        String regexPattern = "\\b(" + String.join("|", badWords) + ")\\b";
        Pattern pattern = Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(comment);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(stringBuffer, "*".repeat(matcher.group().length()));
        }
        matcher.appendTail(stringBuffer);

        return stringBuffer.toString();
    }

    public void setComments(){
        commentsVbox.getChildren().clear();
        ServiceCommentaire commentService =new ServiceCommentaire();
        List<Commentaire> comments =commentService.listerCommentairesParPost(post.getIdPost());

        try {
            for (int i = comments.size()-1; i >= 0; i--) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/commentCard.fxml"));
                Parent fxml = fxmlLoader.load();
                CommentCard controller = fxmlLoader.getController();
                controller.setData(comments.get(i));

                commentsVbox.getChildren().add(fxml);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGenerateAudio(ActionEvent event) {
        if (post == null) {
            System.err.println("No post provided for text-to-speech.");
            return;
        }

        String text = post.getDescription();
        if (text == null || text.isEmpty()) {
            System.err.println("No content to speak.");
            return;
        }

        try {
            // Speak the specified text until the QUEUE become empty
            synthesizer.speakPlainText(text, null);
            synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
        } catch (Exception e) {
            System.err.println("Error during speech synthesis: " + e.getMessage());
            e.printStackTrace();
        }
        //generateAndSaveAudio(post);

    }
    @FXML
    private void generateAndSaveAudio(ActionEvent event) {
        if (post == null || post.getDescription().isEmpty()) {
            System.err.println("No content to generate audio.");
            return;
        }

        try {
            // Set the properties to use the desired voice
            System.setProperty("freetts.voices",
                    "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

            VoiceManager voiceManager = VoiceManager.getInstance();
            Voice voice = voiceManager.getVoice("kevin16"); // Ensure this voice is available in your setup

            if (voice == null) {
                System.err.println("Voice not available.");
                return;
            }

            // Set up the audio player to write to a file
            String basePath = "C:/Users/Mrrae/Desktop/Pijava1/gestion_Raed/src/main/resources/audio/";
            String sanitizedTitle = sanitizeFilename(post.getHashtag());

            String filename = basePath +sanitizedTitle; // Custom filename within the audio folder
            AudioPlayer audioPlayer = new SingleFileAudioPlayer(filename, AudioFileFormat.Type.WAVE);
            voice.setAudioPlayer(audioPlayer);
            voice.allocate();
            voice.speak(post.getDescription());
            voice.deallocate();

            audioPlayer.close();
            System.out.println("Audio file saved as: " + filename + ".wav");
        } catch (Exception e) {
            System.err.println("Error in generating audio file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private String sanitizeFilename(String name) {
        // Remove any characters that are not letters, digits, or common punctuation.
        name = name.replaceAll("[^a-zA-Z0-9_\\-\\s]", "");

        // Replace spaces with underscores to avoid issues in URLs or command lines.
        name = name.replace(" ", "_");

        // Truncate the filename to 100 characters if it's excessively long.
        if (name.length() > 100) {
            name = name.substring(0, 100);
        }

        return name;
    }


}
