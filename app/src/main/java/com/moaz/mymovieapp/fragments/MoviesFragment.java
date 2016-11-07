package com.moaz.mymovieapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.moaz.mymovieapp.BuildConfig;
import com.moaz.mymovieapp.R;
import com.moaz.mymovieapp.adapters.MoviesAdapter;
import com.moaz.mymovieapp.models.Movie;
import com.moaz.mymovieapp.models.RealmMovie;
import com.moaz.mymovieapp.response.MoviesResponse;
import com.moaz.mymovieapp.utils.ApiClient;
import com.moaz.mymovieapp.utils.ApiInterface;
import com.moaz.mymovieapp.utils.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
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
    @BindView(R.id.tv_no_internet)
    TextView tvNoInternet;
    GridLayoutManager gridLayoutManager;
    private MoviesAdapter moviesAdapter;
    private Context mContext;
    public static String TAG = MoviesFragment.class.getSimpleName();
    private Unbinder unbinder;
    private int curPage = 1;
    private int lastPage = 1;
    private String sortBy;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    RealmResults<RealmMovie> realmMovies;
    private int mPos;
    private static final String SELECTION_POS = "SELCTION_POS";
    private static final String MOVIES = "MOVIES";

    public MoviesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        mContext = getActivity().getApplicationContext();
        moviesAdapter = new MoviesAdapter(getActivity());
        gridLayoutManager = new GridLayoutManager(mContext, 3);
        rvMovies.setLayoutManager(gridLayoutManager);
        rvMovies.setAdapter(moviesAdapter);

        rvMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                         @Override
                                         public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                             super.onScrolled(recyclerView, dx, dy);
                                             visibleItemCount = rvMovies.getChildCount();
                                             totalItemCount = gridLayoutManager.getItemCount();
                                             firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();

                                             if (loading) {
                                                 if (totalItemCount > previousTotal) {
                                                     loading = false;
                                                     previousTotal = totalItemCount;
                                                 }
                                             }
                                             if (!loading && (totalItemCount - visibleItemCount)
                                                     <= (firstVisibleItem + visibleThreshold)) {
                                                 // End has been reached

                                                 Log.i("Yaeye!", "end called");
                                                 LoadMoreMovies();
                                                 // Do something

                                                 loading = true;
                                             }
                                         }
                                     }
        );
        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTION_POS)) {
            mPos = savedInstanceState.getInt(SELECTION_POS);
            Movie[] movies = (Movie[]) savedInstanceState.getParcelableArray(MOVIES);
            moviesAdapter.addAll(Arrays.asList(movies));
            moviesAdapter.notifyDataSetChanged();
            rvMovies.scrollToPosition(mPos);
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Utilities.isNetworkConnected(mContext))
            updateMovies();
        else {
            tvNoInternet.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (realmMovies != null)
            realmMovies.removeChangeListeners();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mPos != GridView.INVALID_POSITION) {
            outState.putInt(SELECTION_POS, mPos);
        }
        outState.putParcelableArrayList(MOVIES, (ArrayList<? extends Parcelable>) moviesAdapter.getMMovies());
        super.onSaveInstanceState(outState);
    }

    public void LoadMoreMovies() {
        if (sortBy.equals(getString(R.string.pref_favorite_value))) return;
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        if (curPage + 1 <= lastPage) {
            Call<MoviesResponse> call = apiService.getMoviesByType(sortBy, BuildConfig.OPEN_WEATHER_MAP_API_KEY, ++curPage);
            call.enqueue(new Callback<MoviesResponse>() {

                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    List<Movie> movies = response.body().getResults();
                    if (movies != null) {
                        moviesAdapter.addAll(movies);
                        moviesAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });
        }
    }

    public void updateMovies() {
        // show progress Dialog before the request
        if (moviesAdapter.getItemCount() != 0) return;
        pbMovies.setVisibility(View.VISIBLE);
        rvMovies.setVisibility(View.GONE);
        tvNoInternet.setVisibility(View.GONE);

        sortBy = PreferenceManager.getDefaultSharedPreferences(mContext).getString(getString(R.string.pref_sort_key), getString(R.string.pref_most_popular_value));
//        new FetchMoviesTask().execute(sortBy);
        if (sortBy.equals(getString(R.string.pref_favorite_value))) {
            final Realm realm = Realm.getDefaultInstance();
            realmMovies = realm.where(RealmMovie.class).findAllAsync();
            RealmChangeListener callback = new RealmChangeListener() {

                @Override
                public void onChange(Object o) {
                    moviesAdapter.clear();
                    for (int i = 0; i < realmMovies.size(); i++) {
                        RealmMovie rm = realmMovies.get(i);
                        moviesAdapter.add(new Movie(rm.getId(), rm.getPoster_path(), rm.getOverview(), rm.getRelease_date(), rm.getTitle(), rm.getVote_average()));
                    }
                    moviesAdapter.notifyDataSetChanged();
                    pbMovies.setVisibility(View.GONE);
                    rvMovies.setVisibility(View.VISIBLE);
                }
            };
            realmMovies.addChangeListener(callback);
        } else {
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<MoviesResponse> call = apiService.getMoviesByType(sortBy, BuildConfig.OPEN_WEATHER_MAP_API_KEY, curPage);
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    Log.e("Response", String.valueOf(response.code()));
                    List<Movie> movies = response.body().getResults();
                    lastPage = response.body().getTotal_pages();
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

}
