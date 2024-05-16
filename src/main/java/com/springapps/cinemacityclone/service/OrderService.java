package com.springapps.cinemacityclone.service;

import com.springapps.cinemacityclone.dto.OrderRequestDTO;
import com.springapps.cinemacityclone.dto.TicketRequestDTO;
import com.springapps.cinemacityclone.exception.ResourceNotFoundException;
import com.springapps.cinemacityclone.model.*;
import com.springapps.cinemacityclone.repository.OrderRepository;
import com.springapps.cinemacityclone.repository.ProjectionRepository;
import com.springapps.cinemacityclone.repository.SeatRepository;
import com.springapps.cinemacityclone.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    private UserRepository userRepository;

    private ProjectionRepository projectionRepository;

    private SeatRepository seatRepository;
    private SeatService seatService;
    private TicketService ticketService;
    private EmailService emailService;
    private PdfGenerationService pdfGenerationService;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ProjectionRepository projectionRepository, SeatRepository seatRepository, SeatService seatService, TicketService ticketService, EmailService emailService, PdfGenerationService pdfGenerationService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.projectionRepository = projectionRepository;
        this.seatRepository = seatRepository;
        this.seatService = seatService;
        this.ticketService = ticketService;
        this.emailService = emailService;
        this.pdfGenerationService = pdfGenerationService;
    }

    @Transactional
    public Order addOrder(OrderRequestDTO orderRequestDTO) throws MessagingException {
        //cautam proiectia dupa id
        //cautam user-ul dupa id
        //salvam order
        //generam tickets in functie de locurile cerute in request
        User user = userRepository.findById(orderRequestDTO.getUserId()).orElseThrow(() -> new RuntimeException("user not found"));
        Projection projection = projectionRepository.findById(orderRequestDTO.getProjectionId()).orElseThrow(() -> new RuntimeException("projection not found"));

        Order order = new Order();
        order.setUser(user);
        order.setTickets(generateOrderTickets(order, projection, orderRequestDTO.getTicketRequestDTOs()));
        order.setTotalPrice(computeTotalPrice(order.getTickets()));
        Order savedOrder = orderRepository.save(order);
        for (Ticket ticket:savedOrder.getTickets()) {
            ticket.setOrder(savedOrder);
            ticketService.saveTicket(ticket);
        }
        String movieName = projection.getMovie().getTitle();
        pdfGenerationService.generatePdf("Ai cumparat bilet la filmul " + movieName, "src/main/resources/order.pdf");
        String userEmail = user.getEmail();
        emailService.sendMessageWithAttachment(userEmail, "Bilete", "Ai luat bilete", "src/main/resources/order.pdf");
        return savedOrder;
    }

    @Transactional(readOnly = true)
    public Order findOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Order with ID" + id + "not found"));
    }
    @Transactional(readOnly = true)
    public List<Order> findAllOrder() {
        return orderRepository.findAll();
    }
    @Transactional
    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }
    @Transactional
    public Double computeTotalPrice(List<Ticket> tickets) {
        return tickets.stream()
                .map(ticket -> ticketService.computeTicketPrice(ticket))
                .reduce((sum, ticketPrice) -> sum + ticketPrice)
                .orElseThrow(() -> new RuntimeException("total price could not be computed"));
    }

    @Transactional
    public List<Ticket> generateOrderTickets(Order order, Projection projection, List<TicketRequestDTO> ticketRequestDTOS) {
        return ticketRequestDTOS.stream()
                .map(ticketRequestDTO -> mapTicketFromDTO(ticketRequestDTO, order, projection))
                .collect(Collectors.toList());
    }

    @Transactional
    public Ticket mapTicketFromDTO(TicketRequestDTO ticketRequestDTO, Order order, Projection projection) {
        Ticket ticket = new Ticket();
        ticket.setOrder(order);
        ticket.setProjection(projection);
        Seat seat = seatRepository.findSeatBySeatRowAndSeatColumnAndCinemaRoom_Id(ticketRequestDTO.getRow(), ticketRequestDTO.getCol(), projection.getCinemaRoom().getId()).orElseThrow(() -> new RuntimeException("seat not found"));
        if (!seatService.isSeatAvailable(projection.getId(), seat.getId())) {
            throw new RuntimeException("seat not available");
        }
        ticket.setSeat(seat);
        return ticket;

    }

}
