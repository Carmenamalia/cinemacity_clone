package com.springapps.cinemacityclone.dto;

import java.time.LocalDateTime;

public class ProjectionRequestDTO {
    private Long movieId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public ProjectionRequestDTO() {
    }

    public ProjectionRequestDTO(Long movieId, LocalDateTime startTime, LocalDateTime endTime) {
        this.movieId = movieId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {

        this.endTime = endTime;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
}
