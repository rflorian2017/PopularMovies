package com.example.roby.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.roby.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Roby on 2/22/2018.
 */

// Create the basic adapter extending from RecyclerView.Adapter
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView mPosterImageView;

        //create a constructor that accepts the entire item cell
        // and does the view lookups to find each subview
        public MovieViewHolder(View itemView) {

            super(itemView);
            mPosterImageView = (ImageView) itemView.findViewById(R.id.poster_iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Movie movie = mMovies.get(adapterPosition);
            mClickHandler.onClick(movie);
        }
    }

    // Store a member variable for the movies
    private List<Movie> mMovies;
    // Store the context for easy access
    private Context mContext;

    //string for sorting criteria used in JSON request later on
    String mSortingCriteria;

    // Click handler to allow transition to a detail activity
    final private MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie param);

    }

    // Pass in the click handler to the constructor
    public MoviesAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    //Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        mContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        // Inflate the custom layout
        View movieView = inflater.inflate(R.layout.movie_poster, viewGroup, false);

        // Return a new holder instance
        MovieViewHolder viewHolder = new MovieViewHolder(movieView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder viewHolder, int position) {
        // Get the data model based on position
        Movie movie = mMovies.get(position);
        ImageView imageView = viewHolder.mPosterImageView;

        Picasso.with(this.getContext())
                .load(movie.getMoviePoster())
                .into(imageView);
        // Set item views based on your views and data model

    }

    @Override
    public int getItemCount() {
        if (null == mMovies) return 0;
        return mMovies.size();
    }

    public void setMovieData(List<Movie> movieData) {
        mMovies = movieData;
        notifyDataSetChanged();
    }

    public void setSortingCriteria(String sortingCriteria) {
        mSortingCriteria = sortingCriteria;
    }

    public String getSortingCriteria() {
        return mSortingCriteria;
    }
}
