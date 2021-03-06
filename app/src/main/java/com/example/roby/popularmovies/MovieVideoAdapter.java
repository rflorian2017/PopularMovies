package com.example.roby.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roby.popularmovies.model.FavoriteMovieContract;
import com.example.roby.popularmovies.model.Movie;
import com.example.roby.popularmovies.model.MovieHeaderViewHolder;
import com.example.roby.popularmovies.model.MovieReviewViewHolder;
import com.example.roby.popularmovies.model.MovieVideo;
import com.example.roby.popularmovies.model.MovieVideoViewHolder;
import com.example.roby.popularmovies.model.SectionHeaderViewHolder;
import com.example.roby.popularmovies.model.UserReview;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Roby on 3/3/2018.
 */

public class MovieVideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int MOVIE_VIDEO_VIEW = 0, MOVIE_REVIEW_VIEW = 1, MOVIE_VIDEO_DETAILS_HEADER = 2, SECTION_HEADER = 3;
    private final String MOVIE_TRAILER_STRING = "Trailer ";

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
            case MOVIE_VIDEO_DETAILS_HEADER:
                View movieHeaderView = inflater.inflate(R.layout.movie_header_info, viewGroup, false);
                viewHolder = new MovieHeaderViewHolder(movieHeaderView);
                break;
            case SECTION_HEADER:
                View sectionHeader = inflater.inflate(R.layout.section_header_layout, viewGroup, false);
                viewHolder = new SectionHeaderViewHolder(sectionHeader);
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
            case MOVIE_VIDEO_DETAILS_HEADER:
                MovieHeaderViewHolder movieHeaderView = (MovieHeaderViewHolder) viewHolder;
                configureMovieHeaderViewHolder(movieHeaderView, position);
                break;
            case SECTION_HEADER:
                SectionHeaderViewHolder sectionHeaderViewHolder = (SectionHeaderViewHolder) viewHolder;
                configureSectionHeaderViewHolder(sectionHeaderViewHolder, position);
            default:

                break;
        }
    }

    private void configureSectionHeaderViewHolder(SectionHeaderViewHolder sectionHeaderViewHolder, int position) {
        sectionHeaderViewHolder.getHeaderSection().setText((CharSequence) items.get(position));
    }

    private void configureMovieHeaderViewHolder(MovieHeaderViewHolder movieHeaderView, int position) {
        Movie passedMovie = (Movie) items.get(position);

        Picasso.with(this.mContext)
                .load(passedMovie.getMoviePoster())
                .into(movieHeaderView.getMoviePoster());

        movieHeaderView.getMovieSynopsis().setText(passedMovie.getPlotSynopsis());

        movieHeaderView.getMovieUserRating().setText(passedMovie.getUserRating() + "/10");

        movieHeaderView.getMovieReleaseDate().setText(passedMovie.getReleaseDate());

        movieHeaderView.getMovieTitle().setText(passedMovie.getOriginalTitle());

        if(checkIfEntryExists(passedMovie)) {
            movieHeaderView.getButtonFavorite().setText(R.string.remove_from_favorite_button_text);
            movieHeaderView.setInsertNewRow(false);
        }
        else {
            movieHeaderView.getButtonFavorite().setText(R.string.mark_as_favorite_button_text);
            movieHeaderView.setInsertNewRow(true);
        }
        movieHeaderView.setMovie(passedMovie);
    }

    private void configureMovieVideoViewHolder(MovieVideoViewHolder movieVideoView, int position) {
        MovieVideo movieVideo = (MovieVideo) items.get(position);
        movieVideoView.getVideoLink().setText(MOVIE_TRAILER_STRING + movieVideo.getIdInSequence());
        movieVideoView.setYoutubeLink(movieVideo.getMovieYoutubeId());
    }

    private void configureReviewViewHolder(MovieReviewViewHolder movieReviewView, int position) {
        UserReview userReview = (UserReview) items.get(position);
        movieReviewView.getCommentAuthor().setText(userReview.getmAuthor());
        movieReviewView.getCommentContent().setText(userReview.getmContent());
    }

    private boolean checkIfEntryExists(Movie movie) {
        Cursor cursor = mContext.getContentResolver().query(FavoriteMovieContract.FavoriteMovieEntry.CONTENT_URI,
                new String[]{FavoriteMovieContract.FavoriteMovieEntry.COLUMN_ID},
                FavoriteMovieContract.FavoriteMovieEntry.COLUMN_ID + "=?",
                new String[] {movie.getMovieId()},
                FavoriteMovieContract.FavoriteMovieEntry.COLUMN_ID);
        return cursor.getCount() > 0;
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
        } else if (items.get(position) instanceof MovieVideo) {
            return MOVIE_VIDEO_VIEW;
        } else if (items.get(position) instanceof Movie) {
            return MOVIE_VIDEO_DETAILS_HEADER;
        } else if (items.get(position) instanceof String) {
            return SECTION_HEADER;
        }
        return -1;
    }
}
