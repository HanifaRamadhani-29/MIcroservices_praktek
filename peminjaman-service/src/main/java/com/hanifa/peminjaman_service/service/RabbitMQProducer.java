package com.hanifa.peminjaman_service.service;

import com.hanifa.peminjaman_service.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendNotification(String message) {
        System.out.println("Sending notification to RabbitMQ: " + message);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, message);
    }

    public void sendEvent(Object event, String eventType) {
        // === LOGIKA DIPERBAIKI TOTAL ===
        
        // 1. Tentukan routing key (ini sudah benar)
        String routingKey = RabbitMQConfig.EVENT_ROUTING_KEY_BASE + "." + eventType;
        
        System.out.println("Sending event to RabbitMQ (" + eventType + ") with routing key: " + routingKey);
        
        // 2. Kirim OBJEK JAVA ASLI, bukan String JSON.
        // RabbitTemplate akan otomatis mengubah 'event' menjadi JSON menggunakan MessageConverter.
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, routingKey, event);
    }
}