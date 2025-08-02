package org.documentGenerator.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String originalFilename;

    private LocalDateTime uploadedAt;

    @Lob
    @Column
    private byte[] content;
}
