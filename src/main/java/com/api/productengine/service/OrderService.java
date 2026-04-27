package com.api.productengine.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.api.productengine.model.Order;
import com.api.productengine.model.Product;
import com.api.productengine.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;

    // Inyección por constructor
    public OrderService(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    public Order createOrder(Long productId) {
        // 1. Validar que el producto existe
        Product product = productService.findById(productId);

        // 2. Validar existencias (stock)
        if (product.getStock() <= 0) {
            throw new RuntimeException("No hay stock disponible para el producto: " + product.getName());
        }

        // 3. Validar que el saldo sea mayor a 0
        if (product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El precio del producto debe ser mayor a $0");
        }

        // 4. Lógica de negocio: Restar stock
        product.setStock(product.getStock() - 1);
        productService.update(product.getId(), product);

        // 5. Crear la orden
        Order order = new Order();
        order.setProduct(product);
        order.setTotal(product.getPrice());

        return orderRepository.save(order);
    }
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}