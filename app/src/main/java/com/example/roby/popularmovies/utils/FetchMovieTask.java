package com.example.roby.popularmovies.utils;

import android.os.AsyncTask;

import com.example.roby.popularmovies.MoviesAdapter;
import com.example.roby.popularmovies.model.Movie;

import java.net.URL;
import java.util.List;

/**
 * Created by Roby-L on 27-Feb-18.
 */

// async task used to get the movie data in background
public class FetchMovieTask extends AsyncTask<String, Void, List<Movie>> {

    private MoviesAdapter moviesAdapter;

    public FetchMovieTask(MoviesAdapter moviesAdapter) {
        this.moviesAdapter = moviesAdapter;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Movie> doInBackground(String... params) {

        if (params.length == 0) {
            return null;
        }

        String movieRequest = params[0];
        URL movieRequestUrl = NetworkUtils.buildUrl(movieRequest);

        try {
            String jsonWeatherResponse = NetworkUtils
                    .getResponseFromHttpUrl(movieRequestUrl);

            List<Movie> simpleJsonMovieData = JsonUtils.parseMovieJson(jsonWeatherResponse);

            return simpleJsonMovieData;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Movie> movieData) {
        if (movieData != null) {
            moviesAdapter.setMovieData(movieData);
        } else {

        }
    }
}
