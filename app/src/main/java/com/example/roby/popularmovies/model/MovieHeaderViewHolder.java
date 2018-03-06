package com.example.roby.popularmovies.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roby.popularmovies.R;

/**
 * Created by Roby on 3/6/2018.
 */

public class MovieHeaderViewHolder extends RecyclerView.ViewHolder {

    private TextView movieSynopsis, movieReleaseDate, movieUserRating, movieTitle;
    private ImageView moviePoster;

    public MovieHeaderViewHolder(View itemView) {
        super(itemView);
        movieReleaseDate = itemView.findViewById(R.id.movie_release_date);
        movieUserRating = itemView.findViewById(R.id.movie_user_rating);
        movieSynopsis = itemView.findViewById(R.id.movie_synopsis);
        moviePoster = itemView.findViewById(R.id.movie_poster_iv);
        movieTitle = itemView.findViewById(R.id.original_title);
    }

    public ImageView getMoviePoster() {
        return moviePoster;
    }

    public TextView getMovieTitle() {
        return movieTitle;
    }

    public TextView getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public TextView getMovieSynopsis() {
        return movieSynopsis;
    }

    public TextView getMovieUserRating() {
        return movieUserRating;
    }
}
