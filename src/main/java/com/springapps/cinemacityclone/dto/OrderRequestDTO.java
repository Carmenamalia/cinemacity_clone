package com.springapps.cinemacityclone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OrderRequestDTO {
    private Long projectionId;
    private Long userId;
    @JsonProperty("seats")
    private List<TicketRequestDTO> ticketRequestDTOs;

    public OrderRequestDTO() {
    }

    public OrderRequestDTO(Long projectionId, Long userId, List<TicketRequestDTO> ticketRequestDTOs) {
        this.projectionId = projectionId;
        this.userId = userId;
        this.ticketRequestDTOs = ticketRequestDTOs;
    }

    public Long getProjectionId() {
        return projectionId;
    }

    public void setProjectionId(Long projectionId) {
        this.projectionId = projectionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<TicketRequestDTO> getTicketRequestDTOs() {
        return ticketRequestDTOs;
    }

    public void setTicketRequestDTOs(List<TicketRequestDTO> ticketRequestDTOs) {
        this.ticketRequestDTOs = ticketRequestDTOs;
    }
}
