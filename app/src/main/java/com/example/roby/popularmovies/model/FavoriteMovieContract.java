package com.example.roby.popularmovies.model;

import android.provider.BaseColumns;

/**
 * Created by Roby on 3/12/2018.
 */

public class FavoriteMovieContract {
    public static final class FavoriteMovieEntry {
        /**
         * TABLE NAME = 'movies'
         * ID, TITLE
         */
        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
    }
}
