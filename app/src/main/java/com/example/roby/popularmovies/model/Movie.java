package com.example.roby.popularmovies.model;

/**
 * Created by Roby on 2/22/2018.
 * The movie object consists for now from the title, the id, poster, plot, user rating and release date.
 * I provided accessors for each propriety of this object. The constructor contains the fields described above
 */

public class Movie {
    private String originalTitle;
    private String moviePoster;
    private String plotSynopsis;
    private String userRating;
    private String releaseDate;
    private String id;

    /**
     * No args for constructor
     */
    public Movie() {

    }

    public Movie(String originalTitle, String moviePoster, String plotSynopsis, String userRating, String releaseDate, String id) {
        this.moviePoster = moviePoster;
        this.originalTitle = originalTitle;
        this.plotSynopsis = plotSynopsis;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
        this.id = id;
    }

    public String getMoviePoster() {
        return this.moviePoster;
    }

    public String getMovieId() {
        return this.id;
    }

    public String getOriginalTitle() {
        return this.originalTitle;
    }

    public String getPlotSynopsis() {
        return this.plotSynopsis;
    }

    public String getUserRating() {
        return this.userRating;
    }

    public String getReleaseDate() {
        return this.releaseDate;
    }
}
