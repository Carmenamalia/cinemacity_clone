package com.springapps.cinemacityclone.controller;

import com.springapps.cinemacityclone.dto.TicketRequestDTO;
import com.springapps.cinemacityclone.model.Ticket;
import com.springapps.cinemacityclone.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/add-to-projection")
    ResponseEntity<Ticket> addTicketToProjection (@RequestBody TicketRequestDTO ticketRequestDTO) {
        return ResponseEntity.ok(ticketService.addTicketToProjection(ticketRequestDTO));
    }
    @GetMapping("/{id}")
    ResponseEntity<Ticket> findTicketById(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.findTicketById(id));
    }
    @GetMapping("/")
    ResponseEntity<List<Ticket>> findAllTickets() {
        List <Ticket> tickets = ticketService.findAllTickets();
        return ResponseEntity.ok(tickets);
    }
}
