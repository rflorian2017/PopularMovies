package com.example.roby.popularmovies.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.roby.popularmovies.MoviesAdapter;
import com.example.roby.popularmovies.R;

import java.util.List;

/**
 * Created by Roby on 3/16/2018.
 */ // Provide a direct reference to each of the views within a data item
// Used to cache the views within the item layout for fast access
public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ImageView mPosterImageView;
    private List<Object> mMovies;
    MoviesAdapter.MovieAdapterOnClickHandler mClickHandler;
    //create a constructor that accepts the entire item cell
    // and does the view lookups to find each subview
    public MovieViewHolder(View itemView, MoviesAdapter.MovieAdapterOnClickHandler clickHandler) {
        super(itemView);
        mPosterImageView = itemView.findViewById(R.id.poster_iv);
        this.mClickHandler = clickHandler;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int adapterPosition = getAdapterPosition();
        Movie movie = (Movie) mMovies.get(adapterPosition);
        mClickHandler.onClick(movie);
    }

    public void setMovies(List<Object> movies) {
        mMovies = movies;
    }

    public ImageView getmPosterImageView() {
        return mPosterImageView;
    }
}
