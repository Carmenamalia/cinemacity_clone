package com.springapps.cinemacityclone.repository;

import com.springapps.cinemacityclone.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {

    List<Ticket> findAllByProjection_Id(Long projectionId);

    List<Ticket> findByProjection_IdAndSeat_Id(Long projectionId, Long seatId);

    Optional<Ticket> findById(Long id);

    List<Ticket> findAll();
}
