package com.alexovidiupopa.outbox.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Outbox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long aggregateId;  // Reference to the associated entity (e.g., order ID)
    private String aggregateType;
    private String eventType;

    @Lob
    private String payload;  // JSON representation of the event

    @Column(updatable = false)
    private java.time.Instant createdAt = java.time.Instant.now();

    private java.time.Instant processedAt;  // Set when the message is published
}
