package com.springapps.cinemacityclone.dto;

import java.util.List;

public class CinemaRoomRequestDTO {

    private Integer numberOfRows;
    private Integer numberOfCols;
    private String name;

    private List<ExtraPriceDTO> extraPrices;

    public CinemaRoomRequestDTO() {
    }

    public CinemaRoomRequestDTO(Integer numberOfRows, Integer numberOfCols, String name, List<ExtraPriceDTO> extraPrices) {
        this.numberOfRows = numberOfRows;
        this.numberOfCols = numberOfCols;
        this.name = name;
        this.extraPrices = extraPrices;
    }

    public Integer getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(Integer numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public Integer getNumberOfCols() {
        return numberOfCols;
    }

    public void setNumberOfCols(Integer numberOfCols) {
        this.numberOfCols = numberOfCols;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ExtraPriceDTO> getExtraPrices() {
        return extraPrices;
    }

    public void setExtraPrices(List<ExtraPriceDTO> extraPrices) {
        this.extraPrices = extraPrices;
    }
}
