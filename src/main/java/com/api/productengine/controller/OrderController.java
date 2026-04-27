package com.api.productengine.controller;

import com.api.productengine.model.Order;
import com.api.productengine.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    // Inyección por constructor (sin @Autowired, como te gusta)
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Order> createOrder(@PathVariable Long productId) {
        // Llamamos al servicio que tiene toda la lógica de validación
        Order newOrder = orderService.createOrder(productId);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Order> getAll() {
        return orderService.findAll();
    }
}