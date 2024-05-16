package com.springapps.cinemacityclone.repository;

import com.springapps.cinemacityclone.model.CinemaRoom;
import com.springapps.cinemacityclone.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findAllByCinemaRoom(CinemaRoom cinemaRoom);

    @Query("SELECT s FROM Seat s WHERE  s.id NOT IN (SELECT t.seat FROM Ticket t WHERE t.projection.id = :projectionId)")
    List<Seat> findAvailableSeatsBy(@Param("projectionId") Long projectionId);

    Optional<Seat> findSeatBySeatRowAndSeatColumnAndCinemaRoom_Id(Integer row, Integer col, Long cinemaRoomId);
}
