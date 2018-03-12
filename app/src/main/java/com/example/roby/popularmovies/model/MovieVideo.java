package com.example.roby.popularmovies.model;

/**
 * Created by Roby on 3/12/2018.
 */

public class MovieVideo {
    private String movieYoutubeId;
    private int idInSequence;

    public MovieVideo(String movieYoutubeId, int idInSequence) {
        this.movieYoutubeId = movieYoutubeId;
        this.idInSequence = idInSequence;
    }

    public String getMovieYoutubeId() {
        return movieYoutubeId;
    }

    public int getIdInSequence() {
        return idInSequence;
    }
}
