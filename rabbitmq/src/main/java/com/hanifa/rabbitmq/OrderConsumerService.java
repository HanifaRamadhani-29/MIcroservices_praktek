package com.hanifa.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderConsumerService {
    private final OrderRepository orderRepository;

    @Autowired
    private MailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public OrderConsumerService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    @Transactional
    public void receiveOrder(@Payload Order order) {
        try {
            System.out.println("Order received from RabbitMQ: " + order);

            // Update status order
            order.setStatus(Order.OrderStatus.PROCESSING);
            orderRepository.save(order);

            // Simulasi proses bisnis
            processOrder(order);

            // Update status setelah selesai diproses
            order.setStatus(Order.OrderStatus.COMPLETED);
            order.setProcessedAt(java.time.LocalDateTime.now());
            orderRepository.save(order);

            System.out.println("Order processed successfully: " + order.getId());

        } catch (Exception e) {
            System.err.println("Error processing order: " + order.getId() + ", Error: " + e.getMessage());

            // Update status jika gagal
            order.setStatus(Order.OrderStatus.FAILED);
            orderRepository.save(order);

            // Bisa ditambahkan logic untuk retry atau dead letter queue
            throw new RuntimeException("Failed to process order", e);
        }
    }

    private void processOrder(Order order) {
        // Simulasi proses bisnis
        System.out.println("Processing order: " + order.getId());
        System.out.println("Sending confirmation email to: " + order.getCustomerEmail());

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(sender);
        msg.setTo(order.getCustomerEmail());
        msg.setSubject("Konfirmasi Pembelian ID: " + order.getId());
        msg.setText(
                "Halo, Pelanggan kami dengan email " + order.getCustomerEmail() + " telah berhasil membeli produk "
                        + order.getProductName() + ".\n\n"
                        + "\tDengan ID Pemesanan: " + order.getId() + ".\n\n"
                        + "Terima kasih atas pembelian Anda. :)");

        try {
            this.mailSender.send(msg);
        } catch (MailException ex) {
            System.err.println("Error sending email: " + ex.getMessage());
        }

        // Simulasi delay processing
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Order processing completed: " + order.getId());
    }
}