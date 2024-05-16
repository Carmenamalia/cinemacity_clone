package com.springapps.cinemacityclone.controller;

import com.springapps.cinemacityclone.model.Seat;
import com.springapps.cinemacityclone.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seat")
public class SeatController {

    private SeatService seatService;

    @Autowired
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }
    @GetMapping("/available")
    public ResponseEntity<List<Seat>> getAvailableSeats(@RequestParam Long projectionId) {
        List<Seat> availableSeats = seatService.getAvailableSeats(projectionId);
        return ResponseEntity.ok(availableSeats);
    }
}
