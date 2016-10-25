package com.moaz.mymovieapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.moaz.mymovieapp.BuildConfig;
import com.moaz.mymovieapp.R;
import com.moaz.mymovieapp.Utils.ApiClient;
import com.moaz.mymovieapp.Utils.ApiInterface;
import com.moaz.mymovieapp.adapters.MoviesAdapter;
import com.moaz.mymovieapp.models.Movie;
import com.moaz.mymovieapp.response.MoviesResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by xkcl0301 on 6/19/2016.
 */
public class MoviesFragment extends Fragment {
    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;
    @BindView(R.id.pb_movies)
    ProgressBar pbMovies;
    private MoviesAdapter moviesAdapter;
    private Context context;
    public static String TAG = MoviesFragment.class.getSimpleName();
    private Unbinder unbinder;


    public MoviesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        context = getActivity().getApplicationContext();
        moviesAdapter = new MoviesAdapter(context);
        rvMovies.setLayoutManager(new GridLayoutManager(context,3));
        rvMovies.setAdapter(moviesAdapter);


//        rvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//
//            }
//        });
       return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    public void updateMovies() {
        // show progress Dialog before the request
        pbMovies.setVisibility(View.VISIBLE);
        rvMovies.setVisibility(View.GONE);

        String sortBy = PreferenceManager.getDefaultSharedPreferences(context).getString(getString(R.string.pref_sort_key), getString(R.string.pref_most_popular_value));
//        new FetchMoviesTask().execute(sortBy);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MoviesResponse> call = apiService.getMoviesByType(sortBy, BuildConfig.OPEN_WEATHER_MAP_API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                Log.e("Response",String.valueOf(response.code()));
                List<Movie> movies = response.body().getResults();
                Log.d(TAG, "Number of movies received: " + movies.size());
                // hide the progress dialog and show our grid
                pbMovies.setVisibility(View.GONE);
                rvMovies.setVisibility(View.VISIBLE);
                if (movies != null) {
                    moviesAdapter.clear();
                    moviesAdapter.addAll(movies);
                    moviesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

    }

}
