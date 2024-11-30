package com.alexovidiupopa.outbox.service;

import com.alexovidiupopa.outbox.dto.OrderPlacedEvent;
import com.alexovidiupopa.outbox.model.Order;
import com.alexovidiupopa.outbox.model.Outbox;
import com.alexovidiupopa.outbox.repository.OrderRepository;
import com.alexovidiupopa.outbox.repository.OutboxRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    public OrderService(OrderRepository orderRepository, OutboxRepository outboxRepository, ObjectMapper objectMapper) {
        this.orderRepository = orderRepository;
        this.outboxRepository = outboxRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public void placeOrder(Long customerId) {
        // Create and save the order
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setStatus("PLACED");
        order = orderRepository.save(order);

        // Create an outbox entry
        Outbox outbox = new Outbox();
        outbox.setAggregateId(order.getId());
        outbox.setAggregateType("Order");
        outbox.setEventType("OrderPlacedEvent");

        try {
            // Serialize event payload
            OrderPlacedEvent event = new OrderPlacedEvent(order.getId(), customerId);
            outbox.setPayload(objectMapper.writeValueAsString(event));
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize event", e);
        }

        // Save the outbox entry
        outboxRepository.save(outbox);
    }
}