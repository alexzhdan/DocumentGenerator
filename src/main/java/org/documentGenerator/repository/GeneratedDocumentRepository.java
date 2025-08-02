package org.documentGenerator.repository;

import org.documentGenerator.entity.GeneratedDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneratedDocumentRepository extends JpaRepository<GeneratedDocument,Long> {
}
