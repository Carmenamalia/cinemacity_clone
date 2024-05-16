package com.springapps.cinemacityclone.service;

import com.springapps.cinemacityclone.dto.TicketRequestDTO;
import com.springapps.cinemacityclone.exception.ResourceNotFoundException;
import com.springapps.cinemacityclone.model.Projection;
import com.springapps.cinemacityclone.model.Seat;
import com.springapps.cinemacityclone.model.Ticket;
import com.springapps.cinemacityclone.repository.ProjectionRepository;
import com.springapps.cinemacityclone.repository.SeatRepository;
import com.springapps.cinemacityclone.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TicketService {

    private TicketRepository ticketRepository;
    private ProjectionRepository projectionRepository;
    private SeatRepository seatRepository;
    @Autowired
    public TicketService(SeatRepository seatRepository, TicketRepository ticketRepository, ProjectionRepository projectionRepository) {
        this.ticketRepository = ticketRepository;
        this.projectionRepository = projectionRepository;
        this.seatRepository = seatRepository;
    }

    @Transactional
    public Ticket addTicketToProjection(TicketRequestDTO ticketRequestDTO) {
        Projection projection = projectionRepository.findById(ticketRequestDTO.getProjectionId()).orElseThrow(() -> new ResourceNotFoundException("Projection with id" + ticketRequestDTO.getProjectionId() + "noy found"));
        Seat seat = seatRepository.findById(ticketRequestDTO.getSeatId()).orElseThrow(() -> new ResourceNotFoundException("Seat with id" + ticketRequestDTO.getSeatId() + "not found"));
        Ticket ticket = new Ticket();
        ticket.setProjection(projection);
        ticket.setSeat(seat);
        return ticketRepository.save(ticket);
    }
    @Transactional
    public Double computeTicketPrice(Ticket ticket){
        return ticket.getSeat().getExtraPrice() + ticket.getProjection().getMovie().getMoviePrice();
    }

    @Transactional(readOnly = true)
    public Ticket findTicketById(Long id) {
        return ticketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ticket with id "+ id + "not found"));
    }
    @Transactional(readOnly = true)
    public List<Ticket> findAllTickets() {
        return ticketRepository.findAll();
    }
    @Transactional
    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }
}
