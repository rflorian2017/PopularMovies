package com.example.roby.popularmovies.utils;

import android.util.Log;

import com.example.roby.popularmovies.model.Movie;
import com.example.roby.popularmovies.model.UserReview;

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
    private static final String MOVIE_URL_KEY = "key";
    private static final String MOVIE_REVIEW_AUTHOR = "author";
    private static final String MOVIE_REVIEW_CONTENT = "content";

    private static final String POSTER_URL = "http://image.tmdb.org/t/p/w342/";

    //public static final String POSTER_URL = "http://image.tmdb.org/t/p/w185/";

    public static List<String> getVideos(String json) {
        JSONObject videoResultsJSON = null; //declare a JSON object to interpret the string given as a parameter
        try {
            videoResultsJSON = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        /* example of result :
        {"id":19404,"results":[{"id":"5581bd68c3a3685df70000c6","iso_639_1":"en","iso_3166_1":"US","key":"c25GKl5VNeY","name":"Trailer 1","site":"YouTube","size":720,"type":"Trailer"},
         */

        JSONArray resultsArray;
        List<String> videosArray = new ArrayList<>();
        try {
            //read out everything in "one shot" by looping through the JSON array
            resultsArray = videoResultsJSON.getJSONArray(JSON_REQ_RESULTS);
            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject videoRow = new JSONObject(resultsArray.getString(i));
                videosArray.add(videoRow.getString(MOVIE_URL_KEY));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.v("DEBUG", "Eroare " + e.getMessage());
        }
        return videosArray;
    }

    public static List<UserReview> getReviews(String json) {
        JSONObject videoResultsJSON = null; //declare a JSON object to interpret the string given as a parameter
        try {
            videoResultsJSON = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        /* example of result :
        {"id":19404,"page":1,"results":[{"id":"59eb3d42925141565100e901","author":"MohamedElsharkawy","content":"The Dilwale Dulhania Le Jayenge is a film considered by most to be one of the greatest ever made. From The American Film Institute to as voted by users on the Internet Movie Database (IMDB) it is consider to be one of the best.","url":"https://www.themoviedb.org/review/59eb3d42925141565100e901"}],"total_pages":1,"total_results":1}
         */

        JSONArray resultsArray;
        List<UserReview> userReviews = new ArrayList<>();
        try {
            //read out everything in "one shot" by looping through the JSON array
            resultsArray = videoResultsJSON.getJSONArray(JSON_REQ_RESULTS);
            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject reviewRow = new JSONObject(resultsArray.getString(i));
                userReviews.add(new UserReview(reviewRow.getString(MOVIE_REVIEW_AUTHOR), reviewRow.getString(MOVIE_REVIEW_CONTENT)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.v("DEBUG", "Eroare " + e.getMessage());
        }
        return userReviews;
    }

    public static List<Movie> parseMovieJson(String json) {

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
            for (int i = 0; i < resultsArray.length(); i++) {
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
