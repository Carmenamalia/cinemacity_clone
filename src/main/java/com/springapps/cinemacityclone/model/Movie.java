package com.springapps.cinemacityclone.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Double moviePrice;
    private String genre;
    private String rated;
    private String ratings;
    @OneToMany(mappedBy = "movie",cascade = {CascadeType.PERSIST, CascadeType.MERGE},orphanRemoval = true)
    @JsonManagedReference("movies-projections")
    List<Projection> projections;

    public Movie() {
    }

    @Transactional
    public void addProjection(Projection projection) {
        if (projections == null) {
            projections = new ArrayList<>();
        }
        projections.add(projection);
        projection.setMovie(this);
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getMoviePrice() {
        return moviePrice;
    }

    public void setMoviePrice(Double moviePrice) {
        this.moviePrice = moviePrice;
    }

    public List<Projection> getProjections() {
        return projections;
    }

    public void setProjections(List<Projection> projections) {

        this.projections = projections;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }
}
