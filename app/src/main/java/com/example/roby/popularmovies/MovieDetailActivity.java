package com.example.roby.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roby.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

//class used for the detail activity that displays a movie information. Reads the shared information between the intents
public class MovieDetailActivity extends AppCompatActivity {
    public static final String POSTER_URL = "poster_url";
    public static final String MOVIE_TITLE = "movie_title";
    public static final String MOVIE_USER_RATING = "movie_rating";
    public static final String MOVIE_RELEASE_DATE = "movie_date";
    public static final String MOVIE_PLOT = "movie_plot";
    public static final String MOVIE_PARCEL = "movie_parcel";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ImageView posterIv = findViewById(R.id.movie_poster_iv);

        Intent intent = getIntent();
        if (intent == null) {

        }

        Movie passedMovie = (Movie) intent.getParcelableExtra(MOVIE_PARCEL);

        Picasso.with(this)
                .load(passedMovie.getMoviePoster())
                .into(posterIv);

        TextView movieTitle = findViewById(R.id.original_title);
        movieTitle.setText(passedMovie.getOriginalTitle());

        TextView moviePlot = findViewById(R.id.movie_synopsis);
        moviePlot.setText(passedMovie.getPlotSynopsis());

        TextView movieUserRating = findViewById(R.id.movie_user_rating);
        movieUserRating.setText(passedMovie.getUserRating());

        TextView movieReleaseDate = findViewById(R.id.movie_release_date);
        movieReleaseDate.setText(passedMovie.getReleaseDate());
    }
}
