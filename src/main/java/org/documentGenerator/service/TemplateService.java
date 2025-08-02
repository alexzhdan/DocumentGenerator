package org.documentGenerator.service;

import lombok.RequiredArgsConstructor;
import org.documentGenerator.entity.Template;
import org.documentGenerator.repository.TemplateRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TemplateService {
    private final TemplateRepository templateRepository;

    public Template uploadTemplate(MultipartFile file, String name) throws Exception {
        Template template = new Template();
        template.setName(name);
        template.setOriginalFilename(file.getOriginalFilename());
        template.setUploadedAt(LocalDateTime.now());
        template.setContent(file.getBytes());

        return templateRepository.save(template);
    }

    public List<Template> getAllTemplates() {
        return templateRepository.findAll();
    }

    public Optional<Template> getTemplateById(Long id) {
        return templateRepository.findById(id);
    }
}
