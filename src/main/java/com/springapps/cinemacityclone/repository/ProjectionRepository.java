package com.springapps.cinemacityclone.repository;

import com.springapps.cinemacityclone.model.Projection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository
public interface ProjectionRepository extends JpaRepository<Projection,Long> {

    Optional<Projection> findById(Long iD);
    List<Projection> findAll();
    List<Projection> findAllByStartDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
