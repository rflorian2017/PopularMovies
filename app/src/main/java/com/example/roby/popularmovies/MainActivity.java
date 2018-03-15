package com.example.roby.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.roby.popularmovies.model.Movie;
import com.example.roby.popularmovies.utils.JsonUtils;
import com.example.roby.popularmovies.utils.NetworkUtils;

import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MovieAdapterOnClickHandler, SharedPreferences.OnSharedPreferenceChangeListener, LoaderManager.LoaderCallbacks<List<Movie>> {
    private RecyclerView mRecyclerView;
    private MoviesAdapter mMovieAdapter;
    public static final int SPAN_COUNT = 2;
//    private static final String SORTING_BY_POPULARITY = "popular";
//    private static final String SORTING_BY_FAVORITES = "favorites";
//    private static final String SORTING_BY_TOP_RATED = "top_rated";
    private static final String CHECK_INTERNET_CONNECTION = "Please check the internet connection";
    private static final int MOVIE_QUERIES_LOADER_ID = 0;
    private static boolean PREFERENCES_HAVE_BEEN_UPDATED = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.rv_movies);

        //set the layout manager to grid
        GridLayoutManager layoutManager = new GridLayoutManager(this, SPAN_COUNT, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        //set the adapter of the recycle view
        mMovieAdapter = new MoviesAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);

        //preferences are used to switch the sorting criteria
        setupSharedPreferences();

        //LoaderManager.enableDebugLogging(true);
        getSupportLoaderManager().initLoader(MOVIE_QUERIES_LOADER_ID, null, this);

    }

    //this is used to change the pref of the sorting criteria
    private void setupSharedPreferences() {
        //by default I am showing movies by popularity
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mMovieAdapter.setSortingCriteria(sharedPrefs.getString(getString(R.string.pref_sort_crit),
                getString(R.string.pref_sort_crit_top_rated_key)));

        sharedPrefs.registerOnSharedPreferenceChangeListener(this);
    }

    /**
     * This method is used when we are resetting data, so that at one point in time during a
     * refresh of our data, you can see that there is no data showing.
     */
    private void invalidateData() {
        mMovieAdapter.setMovieData(null);
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
            mMovieAdapter.setSortingCriteria(sharedPreferences.getString(getString(R.string.pref_sort_crit),
                    getString(R.string.pref_sort_crit_top_rated_key)));
            PREFERENCES_HAVE_BEEN_UPDATED = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unregister the shared pref listener
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int i, final Bundle args) {
        return new AsyncTaskLoader<List<Movie>>(this) {
            //for caching purposes
            List<Movie> mMovies = null;

            @Override
            protected void onStartLoading() {
                if (mMovies != null) {
                    deliverResult(mMovies);
                } else {
                    forceLoad();
                }
            }

            @Override
            public List<Movie> loadInBackground() {
                String sortingCriteria = mMovieAdapter.getSortingCriteria();
                if ((sortingCriteria == null) || TextUtils.isEmpty(sortingCriteria)) {
                    return null;
                }
                String movieRequest = sortingCriteria;
                URL movieRequestUrl = NetworkUtils.buildSortingCriteriaUrl(movieRequest);

                try {
                    String jsonWeatherResponse = NetworkUtils
                            .getResponseFromHttpUrl(movieRequestUrl);

                    return JsonUtils.parseMovieJson(jsonWeatherResponse);

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(List<Movie> data) {
                super.deliverResult(data);
                mMovies = data;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        mMovieAdapter.setMovieData(movies);
        if (null == movies) {
            showErrorMessage();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (PREFERENCES_HAVE_BEEN_UPDATED) {
            invalidateData();
            getSupportLoaderManager().restartLoader(MOVIE_QUERIES_LOADER_ID, null, this);
            PREFERENCES_HAVE_BEEN_UPDATED = false;
        }
    }

    private void showErrorMessage() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = (activeNetwork != null) &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {

        }
        else
            Toast.makeText(this, CHECK_INTERNET_CONNECTION, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {

    }
}
