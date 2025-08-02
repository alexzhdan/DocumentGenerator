package org.documentGenerator.repository;

import org.documentGenerator.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<Template,Long> {
}
