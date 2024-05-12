package tn.esprit.controllers;

import entities.evenement;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class PDFGenerator {

    public static void generatePDF(List<evenement> events) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.setNonStrokingColor(Color.RED);
                contentStream.beginText();
                float titleWidth = PDType1Font.HELVETICA_BOLD.getStringWidth("LISTES DES EVENEMENTS ") / 1000f * 16;
                float titleHeight = PDType1Font.HELVETICA_BOLD.getFontDescriptor().getFontBoundingBox().getHeight() / 1000f * 16;
                contentStream.newLineAtOffset((page.getMediaBox().getWidth() - titleWidth) / 2, page.getMediaBox().getHeight() - 30 - titleHeight);
                contentStream.showText("LISTES DES EVENEMENTS");
                contentStream.endText();

                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.setNonStrokingColor(Color.BLACK);

                float margin = 50;
                float yStart = page.getMediaBox().getHeight() - margin - 30 - titleHeight;
                float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
                float yPosition = yStart;
                float rowHeight = 20;

                // Define column widths and headers
                String[] headers = {"ID", "Nom", "Type", "Durée", "Date"};
                float[] columnWidths = {50, 150, 100, 50, 100}; // Adjust these values as needed

                // Draw table headers
                drawTableRow(contentStream, yPosition, margin, tableWidth, headers, columnWidths);
                yPosition -= rowHeight;

                // Draw table data
                for (evenement event : events) {
                    String[] rowData = {
                            Integer.toString(event.getId_Event()),
                            event.getNom_event(),
                            event.getType_event(),
                            event.getDuree_event(),
                            event.getDate_event().toString()
                    };
                    drawTableRow(contentStream, yPosition, margin, tableWidth, rowData, columnWidths);
                    yPosition -= rowHeight;
                }
            }

            // Save the PDF document
            document.save("events_report.pdf");
        }
    }

    private static void drawTableRow(PDPageContentStream contentStream, float yPosition, float margin, float tableWidth, String[] rowData, float[] columnWidths) throws IOException {
        float xPosition = margin;
        float rowHeight = 20;
        float cellMargin = 2; // Adjust this value to set the margin between cell content and borders

        // Draw borders around the entire row
        contentStream.setStrokingColor(Color.BLACK);
        contentStream.moveTo(margin, yPosition);
        contentStream.lineTo(margin + tableWidth, yPosition);
        contentStream.stroke();

        // Draw table data and borders for each cell
        for (int i = 0; i < rowData.length; i++) {
            // Draw borders around each cell
            contentStream.moveTo(xPosition, yPosition);
            contentStream.lineTo(xPosition, yPosition - rowHeight);
            contentStream.stroke();

            // Draw text content
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.newLineAtOffset(xPosition + cellMargin, yPosition - 12); // Adjust text offset to center vertically
            contentStream.showText(rowData[i]);
            contentStream.endText();

            // Move to the next cell position
            xPosition += columnWidths[i];

            // Draw border at the end of the last cell
            if (i == rowData.length - 1) {
                contentStream.moveTo(xPosition, yPosition);
                contentStream.lineTo(xPosition, yPosition - rowHeight);
                contentStream.stroke();
            }
        }

        // Draw bottom border of the row
        contentStream.moveTo(margin, yPosition - rowHeight);
        contentStream.lineTo(margin + tableWidth, yPosition - rowHeight);
        contentStream.stroke();
    }
}