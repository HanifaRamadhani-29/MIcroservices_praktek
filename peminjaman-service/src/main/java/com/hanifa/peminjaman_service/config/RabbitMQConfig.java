package com.hanifa.peminjaman_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "peminjaman_notification_queue";
    public static final String EVENT_QUEUE_NAME = "peminjaman_event_queue";
    public static final String EXCHANGE_NAME = "peminjaman_exchange";
    // public static final String ROUTING_KEY = "peminjaman.#";
    public static final String ROUTING_KEY = "peminjaman.notification.#";
    public static final String EVENT_ROUTING_KEY_BASE = "peminjaman.event";

    @Bean
    // public Queue queue() {
    public Queue notificationQueue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    public Queue eventQueue() {
        return new Queue(EVENT_QUEUE_NAME, false);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    // public Binding binding(Queue queue, TopicExchange exchange) {
    //     return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    public Binding notificationBinding(Queue notificationQueue, TopicExchange exchange) {
        return BindingBuilder.bind(notificationQueue).to(exchange).with(ROUTING_KEY);
    }
    @Bean
    public Binding eventBinding(Queue eventQueue, TopicExchange exchange) {
        return BindingBuilder.bind(eventQueue).to(exchange).with(EVENT_ROUTING_KEY_BASE + ".#");
    }

    // Bean ini kita PERTAHANKAN. Ini adalah kuncinya.
    // Spring Boot akan secara otomatis menemukan bean ini dan menggunakannya.
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    
}