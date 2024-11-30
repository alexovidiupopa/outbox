package com.alexovidiupopa.outbox.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;
    private String status;

    @Column(updatable = false)
    private java.time.Instant createdAt = java.time.Instant.now();
}