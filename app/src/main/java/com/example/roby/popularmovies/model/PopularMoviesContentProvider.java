package com.example.roby.popularmovies.model;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.roby.popularmovies.utils.FavoriteMovieDbHelper;

/**
 * Created by Roby on 3/17/2018.
 */

public class PopularMoviesContentProvider extends ContentProvider {

    //define constants for query match
    public static final int MOVIES = 100;
    public static final int MOVIES_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMathcer();

    public static UriMatcher buildUriMathcer() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        //add matches with addUri
        uriMatcher.addURI(FavoriteMovieContract.AUTHORITY, FavoriteMovieContract.PATH_MOVIES, MOVIES);
        //single item
        uriMatcher.addURI(FavoriteMovieContract.AUTHORITY, FavoriteMovieContract.PATH_MOVIES + "/*", MOVIES_WITH_ID);
        return uriMatcher;
    }

    private FavoriteMovieDbHelper mFavoriteMovieDbHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mFavoriteMovieDbHelper = new FavoriteMovieDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mFavoriteMovieDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);

        Cursor retCursor;

        switch (match) {
            case MOVIES:
                retCursor = db.query(FavoriteMovieContract.FavoriteMovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uei: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mFavoriteMovieDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        Uri returnUri;
        switch (match) {
            case MOVIES:
                long id = db.insert(FavoriteMovieContract.FavoriteMovieEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    //success
                    returnUri = ContentUris.withAppendedId(FavoriteMovieContract.FavoriteMovieEntry.CONTENT_URI, id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uei: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mFavoriteMovieDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        int moviesDeleted;
        switch (match) {
            case MOVIES:
                String movieId = uri.getLastPathSegment();
                moviesDeleted = db.delete(FavoriteMovieContract.FavoriteMovieEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uei: " + uri);
        }
        return moviesDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
