package com.alexovidiupopa.outbox.service;

import com.alexovidiupopa.outbox.model.Outbox;
import com.alexovidiupopa.outbox.msg.MessageBroker;
import com.alexovidiupopa.outbox.repository.OutboxRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OutboxProcessor {
    private final OutboxRepository outboxRepository;
    private final MessageBroker messageBroker;

    public OutboxProcessor(OutboxRepository outboxRepository, MessageBroker messageBroker) {
        this.outboxRepository = outboxRepository;
        this.messageBroker = messageBroker;
    }

    @Scheduled(fixedRate = 5000)  // Poll every 5 seconds
    @Transactional
    public void processOutbox() {
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
                e.printStackTrace();
            }
        }
    }
}
