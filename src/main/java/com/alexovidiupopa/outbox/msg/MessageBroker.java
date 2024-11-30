package com.alexovidiupopa.outbox.msg;

public interface MessageBroker {
    void publish(String topic, String message);
}