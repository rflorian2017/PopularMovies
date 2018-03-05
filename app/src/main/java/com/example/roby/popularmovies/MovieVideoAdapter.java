package com.example.roby.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.roby.popularmovies.model.MovieReviewViewHolder;
import com.example.roby.popularmovies.model.MovieVideoViewHolder;
import com.example.roby.popularmovies.model.UserReview;

import java.util.List;

/**
 * Created by Roby on 3/3/2018.
 */

public class MovieVideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int MOVIE_VIDEO_VIEW = 0, MOVIE_REVIEW_VIEW = 1;

    private List<Object> items;

    // Store the context for easy access
    private Context mContext;


    public MovieVideoAdapter(List<Object> items) {
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        mContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case MOVIE_REVIEW_VIEW:
                View movieReviewView = inflater.inflate(R.layout.movie_review, viewGroup, false);
                viewHolder = new MovieReviewViewHolder(movieReviewView);
                break;
            case MOVIE_VIDEO_VIEW:
                View movieVideoView = inflater.inflate(R.layout.movie_video, viewGroup, false);
                viewHolder = new MovieVideoViewHolder(movieVideoView);
                break;
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case MOVIE_REVIEW_VIEW:
                MovieReviewViewHolder movieReviewView = (MovieReviewViewHolder) viewHolder;
                configureReviewViewHolder(movieReviewView, position);
                break;
            case MOVIE_VIDEO_VIEW:
                MovieVideoViewHolder movieVideoView = (MovieVideoViewHolder) viewHolder;
                configureMovieVideoViewHolder(movieVideoView, position);
                break;
            default:

                break;
        }
    }

    private void configureMovieVideoViewHolder(MovieVideoViewHolder movieVideoView, int position) {
        movieVideoView.getVideoLink().setText((CharSequence) items.get(position));
    }

    private void configureReviewViewHolder(MovieReviewViewHolder movieReviewView, int position) {
        UserReview userReview = (UserReview) items.get(position);
        movieReviewView.getCommentAuthor().setText(userReview.getmAuthor());
        movieReviewView.getCommentContent().setText(userReview.getmContent());
    }

    @Override
    public int getItemCount() {
        if (null == items) return 0;
        return items.size();
    }

    //Returns the view type of the item at position for the purposes of view recycling.
    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof UserReview) {
            return MOVIE_REVIEW_VIEW;
        } else if (items.get(position) instanceof String) {
            return MOVIE_VIDEO_VIEW;
        }
        return -1;
    }
}
