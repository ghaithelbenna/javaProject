package tn.esprit.services;

import tn.esprit.models.Post;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;

public class PdfGeneratorService {

    public void generatePdf(String filePath, Post post) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Add Image
                PDImageXObject image = PDImageXObject.createFromFile("C:/Users/Mrrae/Desktop/Pijava1/gestion_Raed/src/main/resources/blogImages/"+post.getImage_p(), document);
                float imageWidth = page.getMediaBox().getWidth() - 100;
                float imageHeight = 100;
                float x = (page.getMediaBox().getWidth() - imageWidth) / 2;
                float y = page.getMediaBox().getHeight() - imageHeight - 50;
                contentStream.drawImage(image, x, y, imageWidth, imageHeight);

                // Add Title
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                contentStream.newLineAtOffset(100, y - 30);
                contentStream.showText("Post Details");
                contentStream.endText();

                // Add Title
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, y - 60);
                contentStream.setLeading(14);
                contentStream.showText("Title: " + post.getHashtag());
                contentStream.endText();

                // Add Description
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, y - 80);
                contentStream.setLeading(14);
                contentStream.showText("Description: " + post.getDescription());
                contentStream.endText();
            }

            document.save(filePath);
            System.out.println("PDF created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
