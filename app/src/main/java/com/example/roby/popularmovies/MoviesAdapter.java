package com.example.roby.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.roby.popularmovies.model.FavoriteMovieContract;
import com.example.roby.popularmovies.model.FavoriteMovieViewHolder;
import com.example.roby.popularmovies.model.Movie;
import com.example.roby.popularmovies.model.MovieViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Roby on 2/22/2018.
 */

// Create the basic adapter extending from RecyclerView.Adapter
public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int MOVIE_VIEW = 0, TITLE_VIEW = 1;

    // Store a member variable for the movies
    private List<Object> items;
    // Store the context for easy access
    private Context mContext;

    //string for sorting criteria used in JSON request later on
    String mSortingCriteria;

    //define a Cursor for the movies passed from the activity
    private Cursor mFavoriteMovieCursor;

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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        mContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case MOVIE_VIEW:
                // Inflate the custom layout
                View movieView = inflater.inflate(R.layout.movie_poster, viewGroup, false);
                // Return a new holder instance
                viewHolder = new MovieViewHolder(movieView, mClickHandler);
                ((MovieViewHolder)viewHolder).setMovies(items);

                break;
            case TITLE_VIEW:
                View favoriteMovieView = inflater.inflate(R.layout.favorite_movie, viewGroup, false);
                GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) favoriteMovieView.getLayoutParams();
                lp.height = viewGroup.getMeasuredHeight() / 4;
                favoriteMovieView.setLayoutParams(lp);
                viewHolder = new FavoriteMovieViewHolder(favoriteMovieView);
                break;
            default:
                break;
        }
        return viewHolder;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case MOVIE_VIEW:
                MovieViewHolder movieViewHolder = (MovieViewHolder) viewHolder;
                configureMovieViewHolder(movieViewHolder, position);
                break;
            case TITLE_VIEW:
                FavoriteMovieViewHolder favoriteMovieView = (FavoriteMovieViewHolder) viewHolder;
                configureFavoriteMovieViewHolder(favoriteMovieView, position);
                break;
            default:
                break;
        }
    }

    private void configureFavoriteMovieViewHolder(FavoriteMovieViewHolder favoriteMovieView, int position) {
        //again assume here that we only have one cursor. And ensure this
        /* TODO: make it accept any number of cursors */
        mFavoriteMovieCursor = (Cursor) items.get(0);
        if (!mFavoriteMovieCursor.moveToPosition(position))
            return;
        //get movie title
        String favoriteMovieName = mFavoriteMovieCursor.getString(mFavoriteMovieCursor.getColumnIndex(FavoriteMovieContract.FavoriteMovieEntry.COLUMN_TITLE));
        favoriteMovieView.getTvMovieTitle().setText(favoriteMovieName);
    }

    private void configureMovieViewHolder(MovieViewHolder movieViewHolder, int position) {
        Movie passedMovie = (Movie) items.get(position);
        Picasso.with(this.getContext())
                .load(passedMovie.getMoviePoster())
                .into(movieViewHolder.getmPosterImageView());
        // Set item views based on your views and data model
    }

    @Override
    public int getItemCount() {
        if (null == items) return 0;
        //assume here that only one cursor is present. For now, as the movie app contains only one cursor, it is ok.
        /* TODO: In the future, try here to make it generic */
        if ((items.size() == 1) && (items.get(0) instanceof Cursor)) {
            return ((Cursor) items.get(0)).getCount();
        }
        return items.size();
    }

    public void setMovieData(List<Object> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    //Returns the view type of the item at position for the purposes of view recycling.
    @Override
    public int getItemViewType(int position) {
        //here we have a cursor!!!
        if(items.size() <= position) {
            return TITLE_VIEW;
        }
        if (items.get(position) instanceof Movie) {
            return MOVIE_VIEW;
        } else if (items.get(position) instanceof Cursor) {
            return TITLE_VIEW;
        }
        return -1;
    }

    public void setSortingCriteria(String sortingCriteria) {
        mSortingCriteria = sortingCriteria;
    }

    public String getSortingCriteria() {
        return mSortingCriteria;
    }
}
