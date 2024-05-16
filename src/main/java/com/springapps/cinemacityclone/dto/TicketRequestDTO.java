package com.springapps.cinemacityclone.dto;

public class TicketRequestDTO {

    private Integer row;
    private Integer col;

    private Long projectionId;
    private Long seatId;

    public TicketRequestDTO() {
    }

    public TicketRequestDTO(Integer row, Integer col, Long projectionId, Long seatId) {
        this.row = row;
        this.col = col;
        this.projectionId = projectionId;
        this.seatId = seatId;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getCol() {
        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
    }

    public Long getProjectionId() {
        return projectionId;
    }

    public void setProjectionId(Long projectionId) {
        this.projectionId = projectionId;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }
}
