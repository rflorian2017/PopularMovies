package com.example.roby.popularmovies.model;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Roby on 3/12/2018.
 */

public class FavoriteMovieContract {
    //define the authority
    public static final String AUTHORITY = "com.example.roby.popularmovies";

    //base uri : content://<authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    //provide path for accessing movies
    public static final String PATH_MOVIES = "movies";

    public static final class FavoriteMovieEntry implements BaseColumns {

        //Movie content path : content://<authority>/movies
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();
        /**
         * TABLE NAME = 'movies'
         * ID, TITLE
         */
        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
    }
}
