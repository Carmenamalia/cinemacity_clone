package com.springapps.cinemacityclone.service;

import com.springapps.cinemacityclone.dto.CinemaRoomRequestDTO;
import com.springapps.cinemacityclone.dto.ExtraPriceDTO;
import com.springapps.cinemacityclone.exception.ResourceNotFoundException;
import com.springapps.cinemacityclone.model.CinemaRoom;
import com.springapps.cinemacityclone.model.Seat;
import com.springapps.cinemacityclone.repository.CinemaRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CinemaRoomService {

    private CinemaRoomRepository cinemaRoomRepository;

    @Autowired
    public CinemaRoomService(CinemaRoomRepository cinemaRoomRepository) {
        this.cinemaRoomRepository = cinemaRoomRepository;
    }

    @Transactional 
    public CinemaRoom addCinemaRoom(CinemaRoomRequestDTO cinemaRoomRequestDTO) {
        CinemaRoom cinemaRoom = new CinemaRoom();
        cinemaRoom.setName(cinemaRoomRequestDTO.getName());
        cinemaRoom.setSeats(generateSeatsForCinemaRoom(cinemaRoomRequestDTO, cinemaRoom));
        return cinemaRoomRepository.save(cinemaRoom);
    }

    @Transactional(readOnly = true)
    public CinemaRoom findById(Long Id) {
        return cinemaRoomRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Cinema" +
                " Room with this ID" + Id + "not found"));

    }
    @Transactional(readOnly = true)
    public List<CinemaRoom> findAll() {
        return cinemaRoomRepository.findAll();
    }

    @Transactional
    public List<Seat> generateSeatsForCinemaRoom(CinemaRoomRequestDTO cinemaRoomRequestDTO, CinemaRoom cinemaRoom) {
        List<Seat> result = new ArrayList<>();
        for (int i = 1; i <= cinemaRoomRequestDTO.getNumberOfRows(); i++) {
            for (int j = 1; j <= cinemaRoomRequestDTO.getNumberOfCols(); j++) {
                Seat seat = new Seat();
                seat.setSeatRow(i);
                seat.setSeatColumn(j);
                //seat.setAvailable(true);
                seat.setCinemaRoom(cinemaRoom);
                seat.setExtraPrice(getExtraPrice(i, cinemaRoomRequestDTO.getExtraPrices()));
                result.add(seat);
            }
        }
        return result;
    }

    @Transactional
    public Double getExtraPrice(Integer row, List<ExtraPriceDTO> extraPriceDTOS) {
//        for (ExtraPriceDTO extraPriceDTO : extraPriceDTOS) {
//            if (row >= extraPriceDTO.getStartingRow() && row <= extraPriceDTO.getEndingRow()) {
//                return extraPriceDTO.getExtraPrice();
//            }
//        }
//        return 0.0;
        Optional<Double> extraPriceOptional = extraPriceDTOS.stream()
                .filter(extraPriceDTO -> row >= extraPriceDTO.getStartingRow() && row <= extraPriceDTO.getEndingRow())
                .map(extraPriceDTO -> extraPriceDTO.getExtraPrice())
                .findFirst();
        return extraPriceOptional.orElse(0.0);
    }
    @Transactional
    public void deleteCinemaRoomById(Long Id) {

        cinemaRoomRepository.deleteById(Id);
    }
}
