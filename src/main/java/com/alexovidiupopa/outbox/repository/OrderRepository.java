package com.alexovidiupopa.outbox.repository;

import com.alexovidiupopa.outbox.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
