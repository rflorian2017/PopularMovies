package com.example.roby.popularmovies.model;

import java.util.List;

/**
 * Created by Roby on 3/3/2018.
 */

public class MovieDetails {
    private List<UserReview> mUserReview;
    private List<MovieVideo> mVideos;

    public MovieDetails(List<UserReview> userReviews, List<MovieVideo> videos) {
        this.mUserReview = userReviews;
        this.mVideos = videos;
    }

    public List<MovieVideo> getmVideos() {
        return mVideos;
    }

    public List<UserReview> getmUserReview() {
        return mUserReview;
    }

    public void setmUserReview(List<UserReview> mUserReview) {
        this.mUserReview = mUserReview;
    }

    public void setmVideos(List<MovieVideo> mVideos) {
        this.mVideos = mVideos;
    }
}
