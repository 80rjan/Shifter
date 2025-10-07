package com.shifterwebapp.shifter.external;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class PdfManipulationService {

    // --- Page Dimensions ---
    private static final float CERTIFICATE_WIDTH = 842.4f;

    // --- Common X Start Position (Left-Aligned) ---
    private static final float COMMON_TEXT_START_X_POSITION = 300.0f;

    // First Sentence (Montserrat size 15 Bold)
    private static final float FIRST_SENTENCE_Y_POSITION = 340.0f;
    private static final float SENTENCE_MAX_WIDTH = 500.0f;

    // User Name (Lexend Deca size 42)
    private static final float USER_NAME_Y_POSITION = 260.0f;
    private static final float USER_NAME_MAX_WIDTH = 500.0f;

    // Second Sentence (Montserrat size 14)
    private static final float SECOND_SENTENCE_Y_POSITION = 220.0f;
    private static final float LINE_SPACING_PT = 18.0f;
    private static final int MAX_LINES_FOR_SECOND_SENTENCE = 4;

    // Date (Montserrat size 13)
    private static final float DATE_START_X_POSITION = 60.0f;
    private static final float DATE_Y_POSITION = 45.5f;

    public byte[] fillPdfForm(InputStream pdfTemplateStream,
                              String name,
                              String firstSentence,
                              String secondSentence,
                              String date,
                              String courseTitle) throws IOException {

        try (PDDocument pdfDocument = PDDocument.load(pdfTemplateStream);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            PDDocumentInformation info = pdfDocument.getDocumentInformation();

            info.setTitle(courseTitle + " Certificate");
            info.setAuthor("Shifter");

            PDPage page = pdfDocument.getPage(0);

            try (PDPageContentStream contentStream = new PDPageContentStream(
                    pdfDocument, page, PDPageContentStream.AppendMode.APPEND, true, true)) {

                // --- 1. Draw and Wrap the First Sentence (No explicit limit) ---
                drawWrappedText(
                        contentStream,
                        firstSentence.toUpperCase(),
                        COMMON_TEXT_START_X_POSITION,
                        FIRST_SENTENCE_Y_POSITION,
                        SENTENCE_MAX_WIDTH,
                        PDType1Font.HELVETICA_BOLD,
                        15,
                        Integer.MAX_VALUE
                );

                // --- 2. Draw and Scale the User Name (Single Line, Scales Down) ---
                drawLeftAlignedAndScaleText(
                        contentStream,
                        name,
                        COMMON_TEXT_START_X_POSITION,
                        USER_NAME_Y_POSITION,
                        USER_NAME_MAX_WIDTH,
                        PDType1Font.HELVETICA_BOLD,
                        42
                );

                // --- 3. Draw and Wrap the Second Sentence (Max 4 Lines) ---
                drawWrappedText(
                        contentStream,
                        secondSentence,
                        COMMON_TEXT_START_X_POSITION,
                        SECOND_SENTENCE_Y_POSITION,
                        SENTENCE_MAX_WIDTH,
                        PDType1Font.HELVETICA,
                        14,
                        MAX_LINES_FOR_SECOND_SENTENCE
                );

                // --- 4. Draw the Date (Left Aligned, Fixed Size) ---
                drawLeftAlignedText(
                        contentStream,
                        date,
                        DATE_START_X_POSITION,
                        DATE_Y_POSITION,
                        PDType1Font.HELVETICA,
                        13
                );

            }

            pdfDocument.save(outputStream);
            return outputStream.toByteArray();
        }
    }

    /**
     * Splits and draws text, automatically wrapping to the next line when max width is exceeded,
     * and enforcing a maximum line limit. **FIXED** to draw each line independently.
     */
    private void drawWrappedText(PDPageContentStream contentStream, String text, float xPosition, float yPosition, float maxWidth, PDType1Font font, float fontSize, int lineLimit) throws IOException {

        List<String> lines = getWrappedLines(text, font, fontSize, maxWidth);
        int linesToDraw = Math.min(lines.size(), lineLimit);

        // Draw each line independently using its own beginText/endText block
        for (int i = 0; i < linesToDraw; i++) {

            float lineY = yPosition - (i * LINE_SPACING_PT);
            String line = lines.get(i);

            // If the line is the last one allowed and the text was truncated, add an ellipsis
            if (i == linesToDraw - 1 && lines.size() > lineLimit) {
                line = line.trim() + "...";
            }

            contentStream.beginText();
            contentStream.setFont(font, fontSize);
            // This sets the absolute position for the current line
            contentStream.newLineAtOffset(xPosition, lineY);
            contentStream.showText(line.trim());
            contentStream.endText();
        }
    }

    /**
     * Finds the largest font size (up to maxFontSize) that fits the text within maxWidth,
     * then draws it left-aligned at the specified xPosition. (Used for single-line text like the Name).
     */
    private void drawLeftAlignedAndScaleText(PDPageContentStream contentStream, String text, float xPosition, float yPosition, float maxWidth, PDType1Font font, float maxFontSize) throws IOException {

        float fontSize = maxFontSize;
        float textWidth = font.getStringWidth(text) / 1000 * fontSize;

        while (textWidth > maxWidth && fontSize > 10) {
            fontSize -= 0.5f;
            textWidth = font.getStringWidth(text) / 1000 * fontSize;
        }

        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        contentStream.newLineAtOffset(xPosition, yPosition);
        contentStream.showText(text);
        contentStream.endText();
    }

    /**
     * Draws text left-aligned at a specific fixed position and size (no scaling, no wrapping).
     */
    private void drawLeftAlignedText(PDPageContentStream contentStream, String text, float xPosition, float yPosition, PDType1Font font, float fontSize) throws IOException {

        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        contentStream.newLineAtOffset(xPosition, yPosition);
        contentStream.showText(text);
        contentStream.endText();
    }

    /**
     * Utility function to calculate how the text should be wrapped.
     */
    private List<String> getWrappedLines(String text, PDType1Font font, float fontSize, float maxWidth) throws IOException {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            String testLine = currentLine.length() == 0 ? word : currentLine.toString() + " " + word;
            float testWidth = font.getStringWidth(testLine) / 1000 * fontSize;

            if (testWidth > maxWidth && currentLine.length() > 0) {
                lines.add(currentLine.toString().trim());
                currentLine = new StringBuilder(word + " ");
            } else {
                currentLine.append(word).append(" ");
            }
        }

        if (currentLine.length() > 0) {
            lines.add(currentLine.toString().trim());
        }

        return lines;
    }
}