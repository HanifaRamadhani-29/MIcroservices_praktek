package com.hanifa.order_service.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.hanifa.order_service.model.Order;

public interface OrderRepository extends JpaRepository<Order,Long>{

}