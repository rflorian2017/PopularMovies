package com.example.roby.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.roby.popularmovies.model.Movie;
import com.example.roby.popularmovies.utils.FetchMovieTask;
import com.example.roby.popularmovies.utils.JsonUtils;
import com.example.roby.popularmovies.utils.NetworkUtils;

import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MovieAdapterOnClickHandler, SharedPreferences.OnSharedPreferenceChangeListener {
    private RecyclerView mRecyclerView;
    private MoviesAdapter mMovieAdapter;
    public static final int SPAN_COUNT = 2;
    private static final String SORTING_BY_POPULARITY = "popular";
    private static final String SORTING_BY_TOP_RATED = "top_rated";
    private static final String CHECK_INTERNET_CONNECTION = "Please check the internet connection";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movies);

        //set the layout manager to grid
        GridLayoutManager layoutManager = new GridLayoutManager(this, SPAN_COUNT, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        //set the adapter of the recycle view
        mMovieAdapter = new MoviesAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);

        //preferences are used to switch the sorting criteria
        setupSharedPreferences();

        //load the data from the server
        loadMovieData();
    }

    //this is used to change the pref of the sorting criteria
    private void setupSharedPreferences() {
        //by default I am showing movies by popularity
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mMovieAdapter.setSortingCriteria(sharedPrefs.getBoolean(getString(R.string.pref_sort_crit), getResources().getBoolean(R.bool.pref_sort_crit_default)) ? SORTING_BY_POPULARITY : SORTING_BY_TOP_RATED);
        sharedPrefs.registerOnSharedPreferenceChangeListener(this);
    }

    /**
     * This method will get the user's sorting criteria, and then tell a
     * background method to get the movie data in the background.
     */
    private void loadMovieData() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = (activeNetwork != null) &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected)
            new FetchMovieTask(mMovieAdapter).execute(mMovieAdapter.getSortingCriteria());
        else
            Toast.makeText(this, CHECK_INTERNET_CONNECTION, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(Movie param) {
        launchDetailActivity(param);
    }

    /**
     * Used to transfer the data between the main activity and the detail activity
     *
     * @param param
     */
    private void launchDetailActivity(Movie param) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.MOVIE_PARCEL, param);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sorting_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, Settings.class);
            startActivity(startSettingsActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    /**
     * Need to get the sorting criteria from the shared pref
     */
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals((getString(R.string.pref_sort_crit)))) {
            mMovieAdapter.setSortingCriteria(sharedPreferences.getBoolean(key, getResources().getBoolean(R.bool.pref_sort_crit_default)) ? SORTING_BY_POPULARITY : SORTING_BY_TOP_RATED);
            loadMovieData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unregister the shared pref listener
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
