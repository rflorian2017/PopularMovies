package com.example.roby.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {
    public static final String POSTER_URL = "poster_url";
    public static final String MOVIE_TITLE = "movie_title";
    public static final String MOVIE_USER_RATING = "movie_rating";
    public static final String MOVIE_RELEASE_DATE = "movie_date";
    public static final String MOVIE_PLOT = "movie_plot";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ImageView posterIv = findViewById(R.id.movie_poster_iv);

        Intent intent = getIntent();
        if (intent == null) {

        }

        Picasso.with(this)
                .load(intent.getStringExtra(POSTER_URL))
                .into(posterIv);

        TextView movieTitle = findViewById(R.id.original_title);
        movieTitle.setText(intent.getStringExtra(MOVIE_TITLE));

        TextView moviePlot = findViewById(R.id.movie_synopsis);
        moviePlot.setText(intent.getStringExtra(MOVIE_PLOT));

        TextView movieUserRating = findViewById(R.id.movie_user_rating);
        movieUserRating.setText(intent.getStringExtra(MOVIE_USER_RATING));

        TextView movieReleaseDate = findViewById(R.id.movie_release_date);
        movieReleaseDate.setText(intent.getStringExtra(MOVIE_RELEASE_DATE));
    }
}
