package com.example.roby.popularmovies;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roby.popularmovies.model.Movie;
import com.example.roby.popularmovies.model.MovieDetails;
import com.example.roby.popularmovies.utils.JsonUtils;
import com.example.roby.popularmovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//class used for the detail activity that displays a movie information. Reads the shared information between the intents
public class MovieDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<MovieDetails> {
    public static final String MOVIE_PARCEL = "movie_parcel";
    protected String mMovieId;
    private static final int QUERY_LOADER_MOVIE_DETAILS = 1;
    private RecyclerView mRecyclerViewDetails;

    private MovieVideoAdapter mMovieVideoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mRecyclerViewDetails = findViewById(R.id.rv_details);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewDetails.setLayoutManager(layoutManager);

        mRecyclerViewDetails.setHasFixedSize(true);

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
        movieUserRating.setText(passedMovie.getUserRating() + "/10");

        TextView movieReleaseDate = findViewById(R.id.movie_release_date);
        movieReleaseDate.setText(passedMovie.getReleaseDate());

        mMovieId = passedMovie.getMovieId();

        getSupportLoaderManager().initLoader(QUERY_LOADER_MOVIE_DETAILS, null, this);
    }

    @Override
    public Loader<MovieDetails> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<MovieDetails>(this) {

            //for caching purposes
            MovieDetails mMovieDetails = null;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if (mMovieDetails != null) {
                    deliverResult(mMovieDetails);
                } else {
                    forceLoad();
                }
            }

            @Override
            public MovieDetails loadInBackground() {

                URL movieVideoRequestUrl = NetworkUtils.buildMovieDataUrl(mMovieId, NetworkUtils.VIDEOS_PARAM);
                URL movieReviewsRequestUrl = NetworkUtils.buildMovieDataUrl(mMovieId, NetworkUtils.REVIEWS_PARAM);

                try {
                    String jsonVideosResponse = NetworkUtils
                            .getResponseFromHttpUrl(movieVideoRequestUrl);

                    String jsonReviewsResponse = NetworkUtils
                            .getResponseFromHttpUrl(movieReviewsRequestUrl);

                    MovieDetails movieDetails = new MovieDetails(JsonUtils.getReviews(jsonReviewsResponse), JsonUtils.getVideos(jsonVideosResponse));

                    return movieDetails;

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(MovieDetails data) {
                super.deliverResult(data);
                mMovieDetails = data;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<MovieDetails> loader, MovieDetails data) {
        List<Object> itemList = new ArrayList<>();
        itemList.addAll(data.getmUserReview());
        itemList.addAll(data.getmVideos());

        mMovieVideoAdapter = new MovieVideoAdapter(itemList);

        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mRecyclerViewDetails.setAdapter(mMovieVideoAdapter);
    }

    @Override
    public void onLoaderReset(Loader<MovieDetails> loader) {

    }
}
