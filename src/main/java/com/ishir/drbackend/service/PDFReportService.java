package com.ishir.drbackend.service;

import com.ishir.drbackend.model.Patient;
import com.ishir.drbackend.model.Screening;
import com.ishir.drbackend.repository.PatientRepository;
import com.ishir.drbackend.repository.ScreeningRepository;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Service
public class PDFReportService {

    private final ScreeningRepository screeningRepository;
    private final PatientRepository patientRepository;

    public PDFReportService(
            ScreeningRepository screeningRepository,
            PatientRepository patientRepository
    ) {
        this.screeningRepository = screeningRepository;
        this.patientRepository = patientRepository;
    }

    public byte[] generateReport(Long screeningId) throws Exception {

        Screening screening = screeningRepository
                .findById(screeningId)
                .orElseThrow(() ->
                        new RuntimeException("Screening not found")
                );

        Patient patient = patientRepository
                .findById(screening.getPatientId())
                .orElseThrow(() ->
                        new RuntimeException("Patient not found")
                );

        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();

        Document document = new Document(
                new Rectangle(595, 842),
                36,
                36,
                40,
                40
        );

        PdfWriter.getInstance(document, outputStream);

        document.open();

        Font titleFont = new Font(
                Font.HELVETICA,
                20,
                Font.BOLD
        );

        Font headingFont = new Font(
                Font.HELVETICA,
                13,
                Font.BOLD
        );

        Font normalFont = new Font(
                Font.HELVETICA,
                10,
                Font.NORMAL
        );

        Font smallFont = new Font(
                Font.HELVETICA,
                8,
                Font.NORMAL
        );

        // Title
        Paragraph title = new Paragraph(
                "DR SCREENING REPORT",
                titleFont
        );

        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph subtitle = new Paragraph(
                "AI-Assisted Retinal Screening",
                normalFont
        );

        subtitle.setAlignment(Element.ALIGN_CENTER);
        document.add(subtitle);

        document.add(new Paragraph(" "));

        // Patient Information
        document.add(
                new Paragraph(
                        "PATIENT INFORMATION",
                        headingFont
                )
        );

        PdfPTable patientTable = new PdfPTable(2);
        patientTable.setWidthPercentage(100);
        patientTable.setSpacingBefore(8);
        patientTable.setSpacingAfter(15);

        addRow(patientTable, "Patient ID",
                String.valueOf(patient.getId()), normalFont);

        addRow(patientTable, "Patient Name",
                patient.getName(), normalFont);

        addRow(patientTable, "Age",
                String.valueOf(patient.getAge()), normalFont);

        addRow(patientTable, "Gender",
                patient.getGender(), normalFont);

        document.add(patientTable);

        // Screening Information
        document.add(
                new Paragraph(
                        "SCREENING INFORMATION",
                        headingFont
                )
        );

        PdfPTable screeningTable = new PdfPTable(2);
        screeningTable.setWidthPercentage(100);
        screeningTable.setSpacingBefore(8);
        screeningTable.setSpacingAfter(15);

        addRow(screeningTable, "Screening ID",
                String.valueOf(screening.getId()), normalFont);

        addRow(screeningTable, "DR Grade",
                "Grade " + screening.getDrGrade(), normalFont);

        addRow(screeningTable, "DR Class",
                screening.getDrClass(), normalFont);

        addRow(screeningTable, "Confidence",
                formatPercent(screening.getConfidence()), normalFont);

        addRow(screeningTable, "Referable Status",
                screening.getReferableStatus(), normalFont);

        addRow(screeningTable, "Referable Probability",
                formatPercent(screening.getReferableProbability()),
                normalFont);

        document.add(screeningTable);

        // Image Quality
        document.add(
                new Paragraph(
                        "IMAGE QUALITY",
                        headingFont
                )
        );

        PdfPTable qualityTable = new PdfPTable(2);
        qualityTable.setWidthPercentage(100);
        qualityTable.setSpacingBefore(8);
        qualityTable.setSpacingAfter(15);

        addRow(qualityTable, "Sharpness",
                formatNumber(screening.getSharpness()),
                normalFont);

        addRow(qualityTable, "Brightness",
                formatNumber(screening.getBrightness()),
                normalFont);

        addRow(qualityTable, "Contrast",
                formatNumber(screening.getContrast()),
                normalFont);

        document.add(qualityTable);

        // Original Fundus Image
        addSectionHeading(
                document,
                "ORIGINAL FUNDUS IMAGE",
                headingFont
        );

        addBase64Image(
                document,
                screening.getOriginalImage(),
                350,
                260
        );

        // Grad-CAM
        addSectionHeading(
                document,
                "AI EXPLANATION - GRAD-CAM",
                headingFont
        );

        Paragraph gradcamExplanation = new Paragraph(
                "The highlighted regions indicate areas that "
                        + "influenced the AI model's prediction.",
                smallFont
        );

        gradcamExplanation.setSpacingAfter(8);
        document.add(gradcamExplanation);

        addBase64Image(
                document,
                screening.getGradcamImage(),
                350,
                260
        );

        // Recommendation
        addSectionHeading(
                document,
                "RECOMMENDATION",
                headingFont
        );

        Paragraph recommendation = new Paragraph(
                screening.getRecommendation() != null
                        ? screening.getRecommendation()
                        : "No recommendation available.",
                normalFont
        );

        recommendation.setSpacingAfter(20);
        document.add(recommendation);

        // Disclaimer
        addSectionHeading(
                document,
                "DISCLAIMER",
                headingFont
        );

        Paragraph disclaimer = new Paragraph(
                "This report is generated by an AI-assisted "
                        + "screening system and is not a definitive "
                        + "medical diagnosis. Clinical interpretation "
                        + "should be performed by a qualified "
                        + "healthcare professional.",
                smallFont
        );

        document.add(disclaimer);

        document.close();

        return outputStream.toByteArray();
    }

    private void addRow(
            PdfPTable table,
            String label,
            String value,
            Font font
    ) {
        PdfPCell labelCell =
                new PdfPCell(new Phrase(label, font));

        PdfPCell valueCell =
                new PdfPCell(new Phrase(
                        value != null ? value : "N/A",
                        font
                ));

        labelCell.setPadding(7);
        valueCell.setPadding(7);

        table.addCell(labelCell);
        table.addCell(valueCell);
    }

    private void addSectionHeading(
            Document document,
            String text,
            Font font
    ) {
        Paragraph heading = new Paragraph(text, font);
        heading.setSpacingBefore(8);
        heading.setSpacingAfter(8);
        document.add(heading);
    }

    private void addBase64Image(
            Document document,
            String base64Image,
            float maxWidth,
            float maxHeight
    ) throws Exception {

        if (base64Image == null || base64Image.isBlank()) {
            document.add(
                    new Paragraph(
                            "Image not available."
                    )
            );
            return;
        }

        String imageData = base64Image;

        if (imageData.contains(",")) {
            imageData = imageData.substring(
                    imageData.indexOf(",") + 1
            );
        }

        byte[] imageBytes =
                Base64.getDecoder().decode(imageData);

        Image image = Image.getInstance(imageBytes);

        image.scaleToFit(maxWidth, maxHeight);
        image.setAlignment(Element.ALIGN_CENTER);

        document.add(image);
        document.add(new Paragraph(" "));
    }

    private String formatPercent(Double value) {
        if (value == null) {
            return "N/A";
        }

        return String.format("%.2f%%", value);
    }

    private String formatNumber(Double value) {
        if (value == null) {
            return "N/A";
        }

        return String.format("%.2f", value);
    }
}