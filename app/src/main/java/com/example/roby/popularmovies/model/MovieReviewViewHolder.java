package com.example.roby.popularmovies.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.roby.popularmovies.R;

/**
 * Created by Roby on 3/4/2018.
 */

public class MovieReviewViewHolder extends RecyclerView.ViewHolder {
    private TextView commentAuthor, commentContent;

    public MovieReviewViewHolder(View itemView) {
        super(itemView);
        commentAuthor = itemView.findViewById(R.id.movie_review_author);
        commentContent = itemView.findViewById(R.id.movie_review_content);
    }

    public TextView getCommentAuthor() {
        return commentAuthor;
    }

    public void setCommentAuthor(TextView commentAuthor) {
        this.commentAuthor = commentAuthor;
    }

    public TextView getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(TextView commentContent) {
        this.commentContent = commentContent;
    }
}
