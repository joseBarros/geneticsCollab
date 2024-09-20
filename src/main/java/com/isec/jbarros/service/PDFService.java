package com.isec.jbarros.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
public class PDFService {

    public String extractTextFromPDF(byte[] file) throws IOException {
//        if (!file.getContentType().equalsIgnoreCase("application/pdf")) {
//            throw new IllegalArgumentException("The provided file is not a PDF.");
//        }
        if(file == null) {
            return null;
        }

        String extractedText;

        // Load the PDF document from the uploaded file
        try (PDDocument document = Loader.loadPDF(file)) {
            // Use PDFTextStripper to extract text
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            extractedText = pdfTextStripper.getText(document);
        }

        return extractedText;
    }
}
