package com.example.roby.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Roby on 2/22/2018.
 * The movie object consists for now from the title, the id, poster, plot, user rating and release date.
 * I provided accessors for each propriety of this object. The constructor contains the fields described above
 */

public class Movie implements Parcelable{
    private String originalTitle;
    private String moviePoster;
    private String plotSynopsis;
    private String userRating;
    private String releaseDate;
    private String id;

    /**
     * No args for constructor
     */
    public Movie() {

    }

    public Movie(String originalTitle, String moviePoster, String plotSynopsis, String userRating, String releaseDate, String id) {
        this.moviePoster = moviePoster;
        this.originalTitle = originalTitle;
        this.plotSynopsis = plotSynopsis;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
        this.id = id;
    }

    public String getMoviePoster() {
        return this.moviePoster;
    }

    public String getMovieId() {
        return this.id;
    }

    public String getOriginalTitle() {
        return this.originalTitle;
    }

    public String getPlotSynopsis() {
        return this.plotSynopsis;
    }

    public String getUserRating() {
        return this.userRating;
    }

    public String getReleaseDate() {
        return this.releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(moviePoster);
        parcel.writeString(originalTitle);
        parcel.writeString(plotSynopsis);
        parcel.writeString(userRating);
        parcel.writeString(releaseDate);
    }

    // Using the `in` variable, we can retrieve the values that
    // we originally wrote into the `Parcel`.  This constructor is usually
    // private so that only the `CREATOR` field can access.
    private Movie(Parcel in) {
        id = in.readString();
        moviePoster = in.readString();
        originalTitle = in.readString();
        plotSynopsis = in.readString();
        userRating = in.readString();
        releaseDate = in.readString();
    }

    // After implementing the `Parcelable` interface, we need to create the
    // `Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
    // Notice how it has our class specified as its type.
    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
