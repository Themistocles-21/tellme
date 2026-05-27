package com.cscd488.html.services;

import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ByteArrayResource;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

@Service
public class PdfStorageService {

    private final Path storageLocation;
    private final Map<String, PdfMetadata> pdfStore = new LinkedHashMap<>();

    public PdfStorageService() {
        this.storageLocation = Paths.get("pdf_storage").toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.storageLocation);
            loadExistingPdfs();
        } catch (IOException e) {
            throw new RuntimeException("Could not create PDF storage directory", e);
        }
    }

    private void loadExistingPdfs() throws IOException {
        try (Stream<Path> paths = Files.walk(this.storageLocation, 1)) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".pdf"))
                    .forEach(path -> {
                        String filename = path.getFileName().toString();
                        if (!pdfStore.containsKey(filename)) {
                            PdfMetadata metadata = new PdfMetadata();
                            metadata.filename = filename;
                            try {
                                metadata.createdAt = Files.getLastModifiedTime(path).toString();
                                metadata.displayName = filename.replace(".pdf", "");
                                String[] parts = filename.replace(".pdf", "").split("_");
                                if (parts.length >= 2) {
                                    metadata.customerName = parts[0];
                                    metadata.orderNumber = parts[1];
                                } else {
                                    metadata.customerName = "Unknown";
                                    metadata.orderNumber = filename.substring(0, Math.min(8, filename.length()));
                                }
                                pdfStore.put(filename, metadata);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }

    public void storePdf(byte[] pdfBytes, String filename) throws IOException {
        Path filePath = this.storageLocation.resolve(filename);
        Files.write(filePath, pdfBytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        PdfMetadata metadata = new PdfMetadata();
        metadata.filename = filename;
        metadata.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        metadata.displayName = filename.replace(".pdf", "");

        String[] parts = filename.replace(".pdf", "").split("_");
        if (parts.length >= 2) {
            metadata.customerName = parts[0];
            metadata.orderNumber = parts[1];
        } else {
            metadata.customerName = "Unknown";
            metadata.orderNumber = filename.substring(0, Math.min(8, filename.length()));
        }

        pdfStore.put(filename, metadata);
    }

    public List<PdfMetadata> getAllPdfs() {
        List<PdfMetadata> list = new ArrayList<>(pdfStore.values());
        list.sort((a, b) -> b.createdAt.compareTo(a.createdAt));
        return list;
    }

    public Resource loadPdfAsResource(String filename) throws IOException {
        Path filePath = this.storageLocation.resolve(filename);
        if (!Files.exists(filePath)) {
            throw new IOException("File not found: " + filename);
        }
        byte[] bytes = Files.readAllBytes(filePath);
        return new ByteArrayResource(bytes);
    }

    public boolean deletePdf(String filename) throws IOException {
        Path filePath = this.storageLocation.resolve(filename);
        boolean deleted = Files.deleteIfExists(filePath);
        if (deleted) {
            pdfStore.remove(filename);
        }
        return deleted;
    }

    public byte[] loadPdfBytes(String filename) throws IOException {
        Path filePath = this.storageLocation.resolve(filename);
        return Files.readAllBytes(filePath);
    }

    public static class PdfMetadata {
        public String filename;
        public String displayName;
        public String customerName;
        public String orderNumber;
        public String createdAt;
    }
}