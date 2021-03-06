/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.roby.popularmovies.utils;

import android.net.Uri;
import android.util.Log;

import com.example.roby.popularmovies.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the movie database server.
 */
public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String THEMOVIEDB_BASE_URL =
            "https://api.themoviedb.org";


    /* The format we want our API to return */
    private static final String format = "json";

	// the API key that has to be provided for accessing the API 
    final static String API_KEY_PARAM = "api_key";
    final static String API_KEY = BuildConfig.API_KEY;
    final static String API_VER = "3";

    final static String QUERY_PARAM = "popular";
    final static String QUERY_REQ = "movie";
    public final static String VIDEOS_PARAM = "videos";
    public final static String REVIEWS_PARAM = "reviews";

    /**
     * Builds the URL used to talk to the movie server using a sorting criteria, named queryBase. 
     *
     * @param queryBase The sorting cirteria : popular or top_rated
     * @return The URL to use to query the movie server.
     */
    public static URL buildSortingCriteriaUrl(String queryBase) {
        Uri builtUri = Uri.parse(THEMOVIEDB_BASE_URL).buildUpon()
                .appendPath(API_VER)
                .appendPath(QUERY_REQ)
                .appendPath(queryBase)
                .appendQueryParameter(API_KEY_PARAM, API_KEY)

                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    /**
     *
     * @param movieId movie Id
     * @param infotypeParameter Can be videos or user reviews
     * @return The formed URL for the request
     */
    public static URL buildMovieDataUrl(String movieId, String infotypeParameter) {
        Uri builtUri = Uri.parse(THEMOVIEDB_BASE_URL).buildUpon()
                .appendPath(API_VER)
                .appendPath(QUERY_REQ)
                .appendPath(movieId)
                .appendPath(infotypeParameter)
                .appendQueryParameter(API_KEY_PARAM, API_KEY)

                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}