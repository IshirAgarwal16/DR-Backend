package com.ishir.drbackend.controller;

import com.ishir.drbackend.service.PDFReportService;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final PDFReportService pdfReportService;

    public ReportController(PDFReportService pdfReportService) {
        this.pdfReportService = pdfReportService;
    }

    @GetMapping("/screening/{screeningId}")
    public ResponseEntity<byte[]> generateScreeningReport(
            @PathVariable Long screeningId
    ) {

        try {

            byte[] pdf =
                    pdfReportService.generateReport(screeningId);

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(
                    MediaType.APPLICATION_PDF
            );

            headers.setContentDisposition(
                    ContentDisposition
                            .attachment()
                            .filename(
                                    "DR_Screening_Report_"
                                            + screeningId
                                            + ".pdf"
                            )
                            .build()
            );

            headers.setContentLength(pdf.length);

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(pdf);

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }
}