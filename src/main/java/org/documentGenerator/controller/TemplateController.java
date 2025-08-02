package org.documentGenerator.controller;

import org.documentGenerator.entity.Template;
import org.documentGenerator.service.TemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/templates")
public class TemplateController {
    private final TemplateService templateService;

    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Template> uploadTemplate(
            @RequestPart("file") MultipartFile file,
            @RequestPart("name") String name) throws Exception {
        return ResponseEntity.ok(templateService.uploadTemplate(file, name));
    }

    @GetMapping
    public ResponseEntity<List<Template>> getAllTemplates() {
        return ResponseEntity.ok(templateService.getAllTemplates());
    }
}
