package org.documentGenerator.service;

import lombok.RequiredArgsConstructor;
import org.documentGenerator.entity.GeneratedDocument;
import org.documentGenerator.repository.GeneratedDocumentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DocumentStorageService {
    @Value("${storage.generated-path}")
    private String generatedFolderPath;

    private final GeneratedDocumentRepository documentRepository;

    public Path save(byte[] docBytes, String filename, Map<String, String> inputData) throws Exception {
        // Ensure output directory exists
        Path dir = Path.of(generatedFolderPath);
        Files.createDirectories(dir);

        // Generate full path
        Path outputFile = dir.resolve(filename);
        try (FileOutputStream fos = new FileOutputStream(outputFile.toFile())) {
            fos.write(docBytes);
        }

        // Save metadata to DB
        GeneratedDocument doc = new GeneratedDocument();
        doc.setFilename(filename);
        doc.setFilepath(outputFile.toAbsolutePath().toString());
        doc.setGeneratedAt(LocalDateTime.now());
        doc.setContent(docBytes); // You can serialize as JSON

        documentRepository.save(doc);

        return outputFile;
    }
}
