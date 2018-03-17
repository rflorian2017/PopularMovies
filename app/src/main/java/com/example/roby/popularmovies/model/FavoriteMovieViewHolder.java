package com.example.roby.popularmovies.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.roby.popularmovies.R;

/**
 * Created by Roby on 3/16/2018.
 */

public class FavoriteMovieViewHolder extends RecyclerView.ViewHolder {
    private TextView tvMovieTitle;
    public FavoriteMovieViewHolder(View itemView) {
        super(itemView);
        tvMovieTitle = itemView.findViewById(R.id.tv_movie_db_title);
    }

    public TextView getTvMovieTitle() {
        return tvMovieTitle;
    }
}
