package com.rafi.order_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rafi.order_service.model.Order;
import com.rafi.order_service.repository.OrderRepository;
import com.rafi.order_service.vo.Customer;
import com.rafi.order_service.vo.Product;
import com.rafi.order_service.vo.ResponseTemplate;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public List<ResponseTemplate> getAllOrderWithProductAndCustomer(Long id) {
        List<ResponseTemplate> responseList = new ArrayList<>();
        Order order = getOrderById(id);
        if (order == null) {
            return null;
        }
        Product product = restTemplate.getForObject("http://localhost:8082/api/products/"
            + order.getProdukId(), Product.class);
        Customer customer = restTemplate.getForObject("http://localhost:8081/api/customers/"
            + order.getPelangganId(), Customer.class);
        ResponseTemplate vo = new ResponseTemplate();
        vo.setOrder(order);
        vo.setProduct(product);
        vo.setCustomer(customer);
        responseList.add(vo);
        return responseList;
    }

    
}
