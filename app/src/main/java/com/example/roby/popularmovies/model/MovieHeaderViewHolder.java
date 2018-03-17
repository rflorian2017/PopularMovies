package com.example.roby.popularmovies.model;

import android.content.ContentValues;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roby.popularmovies.R;

/**
 * Created by Roby on 3/6/2018.
 */

public class MovieHeaderViewHolder extends RecyclerView.ViewHolder {

    private TextView movieSynopsis, movieReleaseDate, movieUserRating, movieTitle;
    private ImageView moviePoster;
    private Button mButtonFavorite;
    private Movie passedMovie;
    private boolean insertNewRow;
    private Context mContext;

    public MovieHeaderViewHolder(final View itemView) {
        super(itemView);
        movieReleaseDate = itemView.findViewById(R.id.movie_release_date);
        movieUserRating = itemView.findViewById(R.id.movie_user_rating);
        movieSynopsis = itemView.findViewById(R.id.movie_synopsis);
        moviePoster = itemView.findViewById(R.id.movie_poster_iv);
        movieTitle = itemView.findViewById(R.id.original_title);
        mButtonFavorite = itemView.findViewById(R.id.button_favorite);
        mButtonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMovieToDatabase();
            }
        });
        mContext = itemView.getContext();
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

    public Button getButtonFavorite() {
        return mButtonFavorite;
    }

    public void addMovieToDatabase() {
        //create content values to add to database
        ContentValues cv = new ContentValues();
        cv.put(FavoriteMovieContract.FavoriteMovieEntry.COLUMN_ID, passedMovie.getMovieId());
        cv.put(FavoriteMovieContract.FavoriteMovieEntry.COLUMN_TITLE, passedMovie.getOriginalTitle());
        //insert into db
        if (insertNewRow) {
            mContext.getContentResolver().insert(FavoriteMovieContract.FavoriteMovieEntry.CONTENT_URI, cv);
            mButtonFavorite.setText(R.string.remove_from_favorite_button_text);
            insertNewRow = false;
        } else {
            mContext.getContentResolver().delete(FavoriteMovieContract.FavoriteMovieEntry.CONTENT_URI,
                    FavoriteMovieContract.FavoriteMovieEntry.COLUMN_ID + "=?",
            new String[] {passedMovie.getMovieId()});
            mButtonFavorite.setText(R.string.mark_as_favorite_button_text);
            insertNewRow = true;
        }
    }

    public void setMovie(Movie movie) {
        passedMovie = movie;
    }

    public void setInsertNewRow(boolean insertNewRow) {
        this.insertNewRow = insertNewRow;
    }
}
