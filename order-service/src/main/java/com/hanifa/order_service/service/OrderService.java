package com.hanifa.order_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.client.RestTemplate;

import com.hanifa.order_service.model.Order;
import com.hanifa.order_service.repository.OrderRepository;
import com.hanifa.order_service.vo.Pelanggan;
import com.hanifa.order_service.vo.Produk;
import com.hanifa.order_service.vo.ResponseTemplate;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    // public List<ResponseTemplate> getOrderWithProdukById(Long id){
    // List<ResponseTemplate> responseList = new ArrayList<>();
    // Order order = getOrderById(id);
    // Produk produk = restTemplate.getForObject("http://localhost:8081/api/produk/"
    // + order.getProdukId(), Produk.class);
    // Pelanggan pelanggan =
    // restTemplate.getForObject("http://localhost:8082/api/pelanggan/"
    // + order.getPelangganId(), Pelanggan.class);
    // ResponseTemplate vo = new ResponseTemplate();
    // vo.setOrder(order);
    // vo.setProduk(produk);
    // vo.setPelanggan(pelanggan);
    // responseList.add(vo);
    // return responseList;
    // }

    public List<ResponseTemplate> getOrderWithProdukById(Long id) {
        List<ResponseTemplate> responseList = new ArrayList<>();
        Order order = getOrderById(id);
        ServiceInstance serviceInstance = discoveryClient.getInstances("produk-service").get(0);
        Produk produk = restTemplate.getForObject(serviceInstance.getUri() + "/api/produk/"
                + order.getProdukId(), Produk.class);
        serviceInstance = discoveryClient.getInstances("pelanggan-service").get(0);
        Pelanggan pelanggan = restTemplate.getForObject(serviceInstance.getUri() + "/api/pelanggan/"
                + order.getPelangganId(), Pelanggan.class);
        ResponseTemplate vo = new ResponseTemplate();
        vo.setOrder(order);
        vo.setProduk(produk);
        vo.setPelanggan(pelanggan);
        responseList.add(vo);
        return responseList;
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);

    }
}