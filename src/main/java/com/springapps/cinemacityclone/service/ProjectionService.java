package com.springapps.cinemacityclone.service;

import com.springapps.cinemacityclone.dto.ProjectionRequestDTO;
import com.springapps.cinemacityclone.exception.ResourceNotFoundException;
import com.springapps.cinemacityclone.model.Movie;
import com.springapps.cinemacityclone.model.Projection;
import com.springapps.cinemacityclone.repository.MovieRepository;
import com.springapps.cinemacityclone.repository.ProjectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class ProjectionService {

    private ProjectionRepository projectionRepository;

    private MovieRepository movieRepository;

    @Autowired
    public ProjectionService(ProjectionRepository projectionRepository,MovieRepository movieRepository) {
        this.projectionRepository = projectionRepository;
        this.movieRepository = movieRepository;
    }

    @Transactional
    public Projection addProjectionToMovie(ProjectionRequestDTO projectionRequestDTO) {
        Movie movie = movieRepository.findById(projectionRequestDTO.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie with id " + projectionRequestDTO.getMovieId() + " not found"));
        LocalDateTime newStartTime = projectionRequestDTO.getStartTime();
        Projection projection = getProjection(projectionRequestDTO, movie, newStartTime);
        projection.setMovie(movie);
        movie.addProjection(projection);
        movieRepository.save(movie);
        return projection;
    }

    private static Projection getProjection(ProjectionRequestDTO projectionRequestDTO, Movie movie, LocalDateTime newStartTime) {
        LocalDateTime newEndTime = projectionRequestDTO.getEndTime();
        for (Projection existingProjection : movie.getProjections()) {
            LocalDateTime existingStartTime = existingProjection.getStartDate();
            LocalDateTime existingEndTime = existingProjection.getEndDate();

            if (existingStartTime.isBefore(newEndTime) && existingEndTime.isAfter(newStartTime)) {
                throw new IllegalArgumentException("Projection conflict with existing projection");
            }
        }

        Projection projection = new Projection(newStartTime, newEndTime);
        return projection;
    }

    @Transactional
    public Projection upDateProjection(Long projectionId, ProjectionRequestDTO upDateProjectionDTO) {
        Projection existingProjection = projectionRepository.findById(projectionId).orElseThrow(()-> new ResourceNotFoundException("Projection with id" + projectionId + "not found"));
        if (upDateProjectionDTO.getStartTime() != null) {
            existingProjection.setStartDate(upDateProjectionDTO.getStartTime());
        }
        if (upDateProjectionDTO.getEndTime() != null) {
            existingProjection.setEndDate(upDateProjectionDTO.getEndTime());
        }
        return projectionRepository.save(existingProjection);
    }
    @Transactional
    public void deleteProjectionById(Long projectionId) {
        projectionRepository.deleteById(projectionId);
    }
    @Transactional(readOnly = true)
    public Projection findProjectionById(Long projectionId) {
        return projectionRepository.findById(projectionId).orElseThrow(() -> new ResourceNotFoundException("Projection with id"+ projectionId + "not found"));
    }
    @Transactional(readOnly = true)
    public List<Projection> findAllProjection() {
        return projectionRepository.findAll();
    }
    @Transactional
    public List<Projection> findAllProjectionBetweenTwoDates(LocalDateTime startDate, LocalDateTime endDate) {
        return projectionRepository.findAllByStartDateBetween(startDate, endDate);
    }
}
