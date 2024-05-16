package com.springapps.cinemacityclone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MovieRequestDTO {

    private String title;
    private Long cinemaRoomId;
    private Double price;
    @JsonProperty("dates")
    List<ProjectionRequestDTO> projectionRequestDTOs;

    public MovieRequestDTO() {
    }

    public MovieRequestDTO(String title, Long cinemaRoomId, Double price, List<ProjectionRequestDTO> projectionRequestDTOs) {
        this.title = title;
        this.cinemaRoomId = cinemaRoomId;
        this.price = price;
        this.projectionRequestDTOs = projectionRequestDTOs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCinemaRoomId() {
        return cinemaRoomId;
    }

    public void setCinemaRoomId(Long cinemaRoomId) {
        this.cinemaRoomId = cinemaRoomId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<ProjectionRequestDTO> getProjectionRequestDTOs() {
        return projectionRequestDTOs;
    }

    public void setProjectionRequestDTOs(List<ProjectionRequestDTO> projectionRequestDTOs) {
        this.projectionRequestDTOs = projectionRequestDTOs;
    }
}
