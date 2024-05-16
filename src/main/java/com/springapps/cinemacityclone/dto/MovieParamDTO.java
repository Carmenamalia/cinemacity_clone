package com.springapps.cinemacityclone.dto;

public class MovieParamDTO {
    private String movieName;
    private String rated;

    private String genre;

    private String ratings;

    public MovieParamDTO() {
    }

    public MovieParamDTO(String movieName, String rated, String genre, String ratings) {
        this.movieName = movieName;
        this.rated = rated;
        this.genre = genre;
        this.ratings = ratings;
    }


    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }
}
