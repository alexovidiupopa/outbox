package com.alexovidiupopa.outbox.repository;

import com.alexovidiupopa.outbox.model.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutboxRepository extends JpaRepository<Outbox, Long> {

    @Query("SELECT o FROM Outbox o WHERE o.processedAt IS NULL ORDER BY o.createdAt ASC")
    List<Outbox> findByProcessedAtIsNull();  // Find unprocessed outbox entries
}