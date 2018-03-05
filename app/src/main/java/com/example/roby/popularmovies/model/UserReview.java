package com.example.roby.popularmovies.model;

/**
 * Created by Roby on 3/3/2018.
 */

public class UserReview {
    private String mAuthor;
    private String mContent;

    public UserReview() {

    }

    public UserReview(String author, String content) {
        this.mAuthor = author;
        this.mContent = content;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmContent() {
        return mContent;
    }
}
