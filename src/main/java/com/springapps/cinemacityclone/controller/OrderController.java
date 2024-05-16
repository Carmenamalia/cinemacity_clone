package com.springapps.cinemacityclone.controller;

import com.springapps.cinemacityclone.dto.OrderRequestDTO;
import com.springapps.cinemacityclone.model.Movie;
import com.springapps.cinemacityclone.model.Order;
import com.springapps.cinemacityclone.service.OrderService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> addOrder(@RequestBody OrderRequestDTO orderRequestDTO){
        try {
            return ResponseEntity.ok(orderService.addOrder(orderRequestDTO));
        } catch (MessagingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findOrderCinemaById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findOrderById(id));
    }
    @GetMapping("/")
    public ResponseEntity<List<Order>> findAllOrderCinema() {
        List<Order> orderCinemas = orderService.findAllOrder();
        return ResponseEntity.ok(orderCinemas);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteOrderCinemaById(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }
}
