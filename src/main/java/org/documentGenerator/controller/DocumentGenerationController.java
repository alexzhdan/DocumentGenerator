package org.documentGenerator.controller;

import org.documentGenerator.entity.Template;
import org.documentGenerator.service.DocumentStorageService;
import org.documentGenerator.service.TemplateService;
import org.documentGenerator.service.WordTemplateProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/documents")
public class DocumentGenerationController {
    private final WordTemplateProcessor wordTemplateProcessor;
    @Autowired
    private TemplateService templateService;
    @Autowired
    private DocumentStorageService documentStorageService;

    public DocumentGenerationController(WordTemplateProcessor wordTemplateProcessor) {
        this.wordTemplateProcessor = wordTemplateProcessor;
    }

    @PostMapping(value = "/generate/from-template-id", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> generateFromStoredTemplate(
            @RequestBody Map<String, Object> request) {
        try {
            Long templateId = Long.parseLong(request.get("templateId").toString());
            Map<String, String> data = (Map<String, String>) request.get("data");

            Template template = templateService.getTemplateById(templateId)
                    .orElseThrow(() -> new RuntimeException("Template not found"));

            byte[] result = wordTemplateProcessor.generateDocument(template.getContent(), data);

            String filename = "generated_" + System.currentTimeMillis() + ".docx";
            documentStorageService.save(result, filename, data);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(result);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(("Failed: " + e.getMessage()).getBytes());
        }
    }

}
