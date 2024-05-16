package com.springapps.cinemacityclone.repository;

import com.springapps.cinemacityclone.model.CinemaRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CinemaRoomRepository extends JpaRepository<CinemaRoom, Long> {

    Optional<CinemaRoom> findById(Long Id);

    List<CinemaRoom> findAll();

}
