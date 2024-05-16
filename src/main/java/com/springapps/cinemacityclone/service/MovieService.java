package com.springapps.cinemacityclone.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springapps.cinemacityclone.dto.MovieParamDTO;
import com.springapps.cinemacityclone.dto.MovieRequestDTO;
import com.springapps.cinemacityclone.dto.ProjectionRequestDTO;
import com.springapps.cinemacityclone.exception.MovieParamFetchException;
import com.springapps.cinemacityclone.exception.ResourceNotFoundException;
import com.springapps.cinemacityclone.model.CinemaRoom;
import com.springapps.cinemacityclone.model.Movie;
import com.springapps.cinemacityclone.model.Projection;
import com.springapps.cinemacityclone.repository.CinemaRoomRepository;
import com.springapps.cinemacityclone.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;
    private static final String MOVIE_BASE_URL = "http://www.omdbapi.com";

    @Value("${movie.api.key}")
    private String apiKey;

    private MovieRepository movieRepository;
    private CinemaRoomRepository cinemaRoomRepository;

    @Autowired
    public MovieService(RestTemplate restTemplate, ObjectMapper objectMapper, MovieRepository movieRepository, CinemaRoomRepository cinemaRoomRepository) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.movieRepository = movieRepository;
        this.cinemaRoomRepository = cinemaRoomRepository;
    }

    public MovieParamDTO getMovieParamCharacteristics(String movieName) {
        try {
            String url = UriComponentsBuilder
                    .fromUriString(MOVIE_BASE_URL)
                    .queryParam("apikey", apiKey)
                    .queryParam("t", movieName)
                    .toUriString();
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);
            return mapFromJsonToMovieParamDTO(root);
        } catch (IOException e) {
            throw new MovieParamFetchException("Error fetching movie parameters from external API", e);

        }
    }

    public MovieParamDTO mapFromJsonToMovieParamDTO(JsonNode root) {
        String movieName = root.path("Title").toString();
        String rated = root.path("Rated").toString();
        String genre = root.path("Genre").toString();
        String ratings = root.path("Ratings").toString();
        return new MovieParamDTO(movieName, rated, genre, ratings);
    }


    //La adaugare film - preluare detalii film din API extern
    @Transactional
    public Movie addMovie(MovieRequestDTO movieRequestDTO) {

        MovieParamDTO movieParamDTO = getMovieParamCharacteristics(movieRequestDTO.getTitle());

        Movie movie = new Movie();
        movie.setTitle(movieRequestDTO.getTitle());
        movie.setMoviePrice(movieRequestDTO.getPrice());
        movie.setRated(movieParamDTO.getRated());
        movie.setGenre(movieParamDTO.getGenre());
        movie.setRatings(movieParamDTO.getRatings());

        CinemaRoom cinemaRoom = cinemaRoomRepository.findById(movieRequestDTO.getCinemaRoomId()).orElseThrow(() -> new RuntimeException("cinemaRoom not found"));

        List<Projection> projections = generateMovieProjections(movieRequestDTO.getProjectionRequestDTOs(), movie, cinemaRoom);

        movie.setProjections(projections);
        return movieRepository.save(movie);
    }

    @Transactional(readOnly = true)
    public Movie findMovieById(Long Id) {
        return  movieRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Movie with Id" + Id + "is not founded"));
    }
    @Transactional(readOnly = true)
    public List<Movie> findAllMovies() {
        return movieRepository.findAll();
    }
    @Transactional
    public void deleteMovieById(Long Id) {
        movieRepository.deleteById(Id);
    }

    @Transactional
    public List<Projection> generateMovieProjections(List<ProjectionRequestDTO> projectionRequestDTOS, Movie movie, CinemaRoom cinemaRoom) {
/*
        List<Projection> result = new ArrayList<>();
       for (ProjectionRequestDTO projectionRequestDTO:projectionRequestDTOS){
           result.add(mapFromDTOToProjection(projectionRequestDTO,movie,cinemaRoom));
       }
        return result;
*/
        return projectionRequestDTOS.stream()
                .map(projectionRequestDTO -> mapFromDTOToProjection(projectionRequestDTO, movie, cinemaRoom))
                .collect(Collectors.toList());

    }

    @Transactional
    public Projection mapFromDTOToProjection(ProjectionRequestDTO projectionRequestDTO, Movie movie, CinemaRoom cinemaRoom) {
        Projection projection = new Projection();
        projection.setStartDate(projectionRequestDTO.getStartTime());
        projection.setEndDate(projectionRequestDTO.getEndTime());
        projection.setMovie(movie);
        projection.setCinemaRoom(cinemaRoom);
        return projection;
    }
}
