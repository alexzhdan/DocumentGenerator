package org.documentGenerator.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class GeneratedDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    private String filepath;

    private LocalDateTime generatedAt;

    @Lob
    private byte[] content;
}
