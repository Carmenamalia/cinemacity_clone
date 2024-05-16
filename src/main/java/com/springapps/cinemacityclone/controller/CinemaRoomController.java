package com.springapps.cinemacityclone.controller;

import com.springapps.cinemacityclone.dto.CinemaRoomRequestDTO;
import com.springapps.cinemacityclone.exception.ResourceNotFoundException;
import com.springapps.cinemacityclone.model.CinemaRoom;
import com.springapps.cinemacityclone.service.CinemaRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/cinemaroom")
public class CinemaRoomController {

    private CinemaRoomService cinemaRoomService;

    @Autowired
    public CinemaRoomController(CinemaRoomService cinemaRoomService) {
        this.cinemaRoomService = cinemaRoomService;
    }

    @PostMapping
    public ResponseEntity<CinemaRoom> addCinemaRoom(@RequestBody CinemaRoomRequestDTO cinemaRoomRequestDTO) {
        return ResponseEntity.ok(cinemaRoomService.addCinemaRoom(cinemaRoomRequestDTO));
    }

    @GetMapping("/{id}")
    ResponseEntity<CinemaRoom> findCinemaRoomById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(cinemaRoomService.findById(id));
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/")
    ResponseEntity<List<CinemaRoom>> findAllCinemaRoom() {
        List<CinemaRoom> cinemaRooms = cinemaRoomService.findAll();
        return ResponseEntity.ok(cinemaRooms);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCinemaRoomById(@PathVariable Long id) {
        cinemaRoomService.deleteCinemaRoomById(id);
        return ResponseEntity.noContent().build();
    }
}
