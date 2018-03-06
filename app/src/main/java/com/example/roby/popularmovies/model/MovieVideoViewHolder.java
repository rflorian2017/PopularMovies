package com.example.roby.popularmovies.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.roby.popularmovies.R;

/**
 * Created by Roby on 3/4/2018.
 */

public class MovieVideoViewHolder extends RecyclerView.ViewHolder {
    private TextView videoLink;

    public MovieVideoViewHolder(View v) {
        super(v);
        videoLink = v.findViewById(R.id.movie_video_link);
    }

    public TextView getVideoLink() {
        return videoLink;
    }

}