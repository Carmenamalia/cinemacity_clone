package com.springapps.cinemacityclone.controller;

import com.springapps.cinemacityclone.dto.ProjectionRequestDTO;
import com.springapps.cinemacityclone.model.Projection;
import com.springapps.cinemacityclone.service.ProjectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/projection")
public class ProjectionController {

    private ProjectionService projectionService;

    @Autowired
    public ProjectionController(ProjectionService projectionService) {
        this.projectionService = projectionService;
    }

    @PostMapping
    ResponseEntity<Projection> addProjectionToMovie(@RequestBody ProjectionRequestDTO projectionRequestDTO) {
        return ResponseEntity.ok(projectionService.addProjectionToMovie(projectionRequestDTO));
    }
    @PutMapping("/{projectionId}")
    ResponseEntity<Projection> upDateProjection(@PathVariable Long projectionId,@RequestBody ProjectionRequestDTO updatedProjectionDTO) {
        return ResponseEntity.ok(projectionService.upDateProjection(projectionId, updatedProjectionDTO));

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Projection> deleteProjectionById(@PathVariable Long id) {
        projectionService.deleteProjectionById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Projection> findProjectionById(@PathVariable Long id) {
        return ResponseEntity.ok(projectionService.findProjectionById(id));
    }
    @GetMapping("/")
    public  ResponseEntity<List<Projection>> findAllProjection() {
        return ResponseEntity.ok(projectionService.findAllProjection());
    }
    //de verificat primesc 400 bad request
    @GetMapping("/between")
    public ResponseEntity<List<Projection>> findAllProjectionBetweenTwoDates(
            @RequestParam("startDateTime") String startDateTimeStr,
            @RequestParam("endDateTime") String endDateTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeStr.trim(), formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDateTimeStr.trim(), formatter);

        List<Projection> projections = projectionService.findAllProjectionBetweenTwoDates(startDateTime, endDateTime);
        return ResponseEntity.ok(projections);
    }
}
