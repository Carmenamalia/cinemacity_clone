package com.springapps.cinemacityclone.service;

import com.springapps.cinemacityclone.model.Projection;
import com.springapps.cinemacityclone.model.Seat;
import com.springapps.cinemacityclone.model.Ticket;
import com.springapps.cinemacityclone.repository.ProjectionRepository;
import com.springapps.cinemacityclone.repository.SeatRepository;
import com.springapps.cinemacityclone.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class SeatService {

    private SeatRepository seatRepository;
    private ProjectionRepository projectionRepository;
    private TicketRepository ticketRepository;

    @Autowired
    public SeatService(SeatRepository seatRepository, ProjectionRepository projectionRepository, TicketRepository ticketRepository) {
        this.seatRepository = seatRepository;
        this.projectionRepository = projectionRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public List<Seat> getAvailableSeats(Long projectionId) {

        //gasim toate seats care nu au tickets pt proiectia cu projectionId
        //un seat este available daca nu are ticket pt proiectia cu projectionId
        //gasim toate ticketele proiectiei vandute
        //deci stim care sunt seat-urile ocupate
        //gasim toate seat-urile si le filtram(neocupate)

        Projection projection = projectionRepository.findById(projectionId).orElseThrow(() -> new RuntimeException("projection not found"));
        List<Ticket> projectionTickets = ticketRepository.findAllByProjection_Id(projectionId);
        List<Seat> occupiedSeats = projectionTickets.stream()
                .map(ticket -> ticket.getSeat())
                .collect(Collectors.toList());
        List<Seat> allSeats = seatRepository.findAllByCinemaRoom(projection.getCinemaRoom());
        List<Seat> availableSeats = allSeats.stream()
                .filter(seat -> !occupiedSeats.contains(seat))
                .collect(Collectors.toList());
        return availableSeats;
        //return seatRepository.findAvailableSeatsBy(projectionId);
    }

    //un loc este liber daca la proiectia cu un anumit id nu are ticket
    //ma duc in ticket reprository si acolo gasesc ticket-ul
    // pentru locul cu un id si proiectia cu un id
    public boolean isSeatAvailable(Long projectionId, Long seatId) {
        List<Ticket> tickets = ticketRepository.findByProjection_IdAndSeat_Id(projectionId, seatId);
        return tickets.isEmpty();
    }
}
