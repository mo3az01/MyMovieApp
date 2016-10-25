package com.moaz.mymovieapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.moaz.mymovieapp.BuildConfig;
import com.moaz.mymovieapp.R;
import com.moaz.mymovieapp.Utils.GlobalValues;
import com.moaz.mymovieapp.activities.MovieDetailActivity;
import com.moaz.mymovieapp.adapters.MoviesAdapter;
import com.moaz.mymovieapp.models.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by xkcl0301 on 6/19/2016.
 */
public class MoviesFragment extends Fragment {
    @BindView(R.id.gv_movies)
    GridView gvMovies;
    @BindView(R.id.pb_movies)
    ProgressBar pbMovies;
    private MoviesAdapter moviesAdapter;
    private Context context;

    private Unbinder unbinder;


    public MoviesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);
        context = getActivity().getApplicationContext();
        moviesAdapter = new MoviesAdapter(context);
        gvMovies.setAdapter(moviesAdapter);

        gvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(context, MovieDetailActivity.class);
                Movie movie = moviesAdapter.getItem(position);
                intent.putExtra(GlobalValues.EXTRA_MOVIE, movie);
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    public void updateMovies() {
        String sortBy = PreferenceManager.getDefaultSharedPreferences(context).getString(getString(R.string.pref_sort_key), getString(R.string.pref_most_popular_value));
        new FetchMoviesTask().execute(sortBy);
    }

    public class FetchMoviesTask extends AsyncTask<String, Integer, List<Movie>> {

        public final String TAG = FetchMoviesTask.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbMovies.setVisibility(View.VISIBLE);
            gvMovies.setVisibility(View.GONE);
        }

        @Override
            protected List<Movie> doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String moviesJsonStr = null;
            String baseUrl = "http://api.themoviedb.org/3/movie/";
            String sortBy = params[0];
            String THE_MOVIE_DB_API_KEY = BuildConfig.OPEN_WEATHER_MAP_API_KEY;
            try {
                Uri uri = Uri.parse(baseUrl + sortBy).buildUpon().
                        appendQueryParameter("api_key", THE_MOVIE_DB_API_KEY).build();
                Log.d(TAG, uri.toString());
                URL url = new URL(uri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                moviesJsonStr = buffer.toString();
                return getMoviesFromJson(moviesJsonStr);
            } catch (Exception e) {

            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);
            pbMovies.setVisibility(View.GONE);
            gvMovies.setVisibility(View.VISIBLE);
            if (movies == null || movies.isEmpty()) return;
            moviesAdapter.addAll(movies);
            moviesAdapter.notifyDataSetChanged();
        }

        public List<Movie> getMoviesFromJson(String moviesJsonStr) {
            List<Movie> movies = new ArrayList<>();
            try {
                JSONObject response = new JSONObject(moviesJsonStr);
                JSONArray results = response.getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject obj = results.getJSONObject(i);
                    Movie movie = new Movie(obj.getInt("id"), obj.getString("poster_path"), obj.getBoolean("adult"), obj.getString("overview"), obj.getString("release_date"), obj.getString("original_title"), obj.getString("original_language"), obj.getString("title"), obj.getString("backdrop_path"), obj.getDouble("popularity"), obj.getDouble("vote_average"), obj.getInt("vote_count"), obj.getBoolean("video"));
                    movies.add(movie);
                }
            } catch (Exception e) {

            }
            return movies;
        }
    }

}
