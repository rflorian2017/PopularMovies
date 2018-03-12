package com.example.roby.popularmovies.model;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roby.popularmovies.R;

/**
 * Created by Roby on 3/4/2018.
 */

public class MovieVideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView videoLink;
    private String youtubeLink;

    public MovieVideoViewHolder(View v) {
        super(v);
        videoLink = v.findViewById(R.id.movie_video_link);
        v.setOnClickListener(this);
    }

    public TextView getVideoLink() {
        return videoLink;
    }

    @Override
    public void onClick(View view) {
        watchYoutubeVideo(view.getContext(), youtubeLink);
//        Toast.makeText(view.getContext(), videoLink.getText(), Toast.LENGTH_LONG).show();
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }

    private static void watchYoutubeVideo(Context context, String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }
}