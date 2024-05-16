package com.springapps.cinemacityclone.dto;

public class ExtraPriceDTO {

    private Integer startingRow;
    private Integer endingRow;
    private Double extraPrice;

    public ExtraPriceDTO() {
    }

    public ExtraPriceDTO(Integer startingRow, Integer endingRow, Double extraPrice) {
        this.startingRow = startingRow;
        this.endingRow = endingRow;
        this.extraPrice = extraPrice;
    }

    public Integer getStartingRow() {
        return startingRow;
    }

    public void setStartingRow(Integer startingRow) {
        this.startingRow = startingRow;
    }

    public Integer getEndingRow() {
        return endingRow;
    }

    public void setEndingRow(Integer endingRow) {
        this.endingRow = endingRow;
    }

    public Double getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(Double extraPrice) {
        this.extraPrice = extraPrice;
    }
}
