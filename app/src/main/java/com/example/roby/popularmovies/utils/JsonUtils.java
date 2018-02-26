package com.example.roby.popularmovies.utils;

import com.example.roby.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roby on 2/24/2018.
 */

public class JsonUtils {
	//declare final strings for the fields required from the JSON response
    private static final String JSON_REQ_RESULTS = "results";
    private static final String MOVIE_ID = "id";
    private static final String MOVIE_POSTER_PATH = "poster_path";
    private static final String MOVIE_TITLE = "original_title";
    private static final String MOVIE_PLOT = "overview";
    private static final String MOVIE_RATING = "vote_average";
    private static final String MOVIE_RELEASE_DATE = "release_date";

    private static final String POSTER_URL = "http://image.tmdb.org/t/p/w342/";

    //public static final String POSTER_URL = "http://image.tmdb.org/t/p/w185/";

    public static List<Movie> parseMovieJson(String json){

        JSONObject movieResultsJSON = null; //declare a JSON object to interpret the string given as a parameter
        try {
            movieResultsJSON = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        /**
         *
         * Movie object
         private String originalTitle;
         private String moviePoster;
         private String plotSynopsis;
         private String userRating;
         private String releaseDate;
         private String id;
         * Gather all the data needed for the movie object and return directly a list of new objects
         */

        JSONArray resultsArray;
        List<Movie> moviesArray = new ArrayList<>();

        try {
			//read out everything in "one shot" by looping through the JSON array
            resultsArray = movieResultsJSON.getJSONArray(JSON_REQ_RESULTS);
            for (int i=0; i< resultsArray.length(); i++) {
                JSONObject movieRow = new JSONObject(resultsArray.getString(i));
                String movieId = movieRow.getString(MOVIE_ID);
                String moviePoster = POSTER_URL + movieRow.getString(MOVIE_POSTER_PATH);
                String moviePlot = movieRow.getString(MOVIE_PLOT);
                String movieRating = movieRow.getString(MOVIE_RATING);
                String movieReleaseDate = movieRow.getString(MOVIE_RELEASE_DATE);
                String movieTitle = movieRow.getString(MOVIE_TITLE);
                //Movie(String originalTitle, String moviePoster, String plotSynopsis, String userRating, String releaseDate, String id)
                moviesArray.add(new Movie(movieTitle, moviePoster, moviePlot, movieRating, movieReleaseDate, movieId));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return moviesArray;
    }
}
