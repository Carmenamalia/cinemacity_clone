package com.springapps.cinemacityclone.repository;

import com.springapps.cinemacityclone.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {
    Optional<Movie> findById(Long Id);
    List<Movie> findAll();
}
