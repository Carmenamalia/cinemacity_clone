package com.springapps.cinemacityclone.controller;

import com.springapps.cinemacityclone.dto.MovieParamDTO;
import com.springapps.cinemacityclone.dto.MovieRequestDTO;
import com.springapps.cinemacityclone.model.Movie;
import com.springapps.cinemacityclone.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {

    private MovieService movieService;
    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody MovieRequestDTO movieRequestDTO){
        return ResponseEntity.ok(movieService.addMovie(movieRequestDTO));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Movie> findMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.findMovieById(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<Movie>> findAllMovies() {
        List<Movie> movies = movieService.findAllMovies();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{movieName}/characteristics")
    public ResponseEntity<MovieParamDTO> getMovieParamCharacteristics(@PathVariable String movieName) {
        return ResponseEntity.ok(movieService.getMovieParamCharacteristics(movieName));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteById(@PathVariable Long id) {
        movieService.deleteMovieById(id);
        return ResponseEntity.noContent().build();
    }
}
