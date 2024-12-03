package com.alexovidiupopa.outbox.service;

import com.alexovidiupopa.outbox.model.Outbox;
import com.alexovidiupopa.outbox.msg.MessageBroker;
import com.alexovidiupopa.outbox.repository.OutboxRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class OutboxProcessor {
    private final OutboxRepository outboxRepository;
    private final MessageBroker messageBroker;

    public OutboxProcessor(OutboxRepository outboxRepository, MessageBroker messageBroker) {
        this.outboxRepository = outboxRepository;
        this.messageBroker = messageBroker;
    }

    @Scheduled(fixedRate = 10_000)  // Poll every 10 seconds
    @Transactional
    public void processOutbox() {
        log.info("Polling the Outbox table...");
        List<Outbox> unprocessedOutboxEntries = outboxRepository.findByProcessedAtIsNull();

        for (Outbox outbox : unprocessedOutboxEntries) {
            try {
                // Publish the message
                messageBroker.publish(outbox.getEventType(), outbox.getPayload());

                // Mark the entry as processed
                outbox.setProcessedAt(java.time.Instant.now());
                outboxRepository.save(outbox);
            } catch (Exception e) {
                // Handle failures (e.g., retry logic, logging)
                log.error(e.getMessage(), e);
            }
        }
    }
}
