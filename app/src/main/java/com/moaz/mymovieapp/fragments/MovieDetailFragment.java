package com.moaz.mymovieapp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moaz.mymovieapp.BuildConfig;
import com.moaz.mymovieapp.R;
import com.moaz.mymovieapp.adapters.TrailersReviewsAdapter;
import com.moaz.mymovieapp.models.Movie;
import com.moaz.mymovieapp.models.RealmMovie;
import com.moaz.mymovieapp.models.Review;
import com.moaz.mymovieapp.models.Trailer;
import com.moaz.mymovieapp.response.ReviewsResponse;
import com.moaz.mymovieapp.response.TrailersResponse;
import com.moaz.mymovieapp.utils.ApiClient;
import com.moaz.mymovieapp.utils.ApiInterface;
import com.moaz.mymovieapp.utils.GlobalValues;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by XKCL0301 on 6/20/2016.
 */
public class MovieDetailFragment extends Fragment {
    public static String TAG = MovieDetailFragment.class.getSimpleName();
    @BindView(R.id.tv_movie_title)
    TextView tvMovieTitle;
    @BindView(R.id.tv_release_date)
    TextView tvReleaseDate;
    @BindView(R.id.tv_vote_average)
    TextView tvVoteAverage;
    @BindView(R.id.tv_overview)
    TextView tvOverview;
    @BindView(R.id.iv_movie_poster)
    ImageView ivMoviePoster;
    @BindView(R.id.iv_fav)
    ImageView ivFavoriteIcon;
    @BindView(R.id.vp_trailers_reviews)
    ViewPager vpTrailersAndReviews;
    @BindView(R.id.pts_trailers_reviews)
    PagerTitleStrip pagerTitleStrip;

    Movie movie = null;
    Activity mContext;
    private Unbinder unbinder;
    boolean isFav = false;
    TrailersReviewsAdapter trailersReviewsAdapter;
    Realm realm;

    public MovieDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        mContext = getActivity();

        // Get a Realm instance for this thread
        realm = Realm.getDefaultInstance();
        trailersReviewsAdapter = new TrailersReviewsAdapter(mContext);
        vpTrailersAndReviews.setAdapter(trailersReviewsAdapter);

        Bundle args = getArguments();
        if (args != null) {
            movie = args.getParcelable(GlobalValues.EXTRA_MOVIE);
            updateFav();
            fillData();
            String sortBy = PreferenceManager.getDefaultSharedPreferences(mContext).getString(getString(R.string.pref_sort_key), getString(R.string.pref_most_popular_value));

            if (sortBy.equals(getString(R.string.pref_favorite_value))) {
                RealmMovie rm = realm.where(RealmMovie.class).equalTo("id", movie.getId()).findFirst();
                trailersReviewsAdapter.setTrailers(rm.getTrailerRealmList());
                trailersReviewsAdapter.notifyDataSetChanged();
                trailersReviewsAdapter.setReviews(rm.getReviewRealmList());
                trailersReviewsAdapter.notifyDataSetChanged();
            } else {
                fetchTrailers();
                fetchReviewers();
            }
        }
        ivFavoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivFavoriteIcon.setEnabled(false);
                isFav = !isFav;
                if (isFav) {
                    ivFavoriteIcon.setImageResource(R.drawable.fav_icon);
                    final RealmMovie realmMovie = new RealmMovie(movie, trailersReviewsAdapter.getTrailers(), trailersReviewsAdapter.getReviews());

                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.copyToRealm(realmMovie);
                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            ivFavoriteIcon.setEnabled(true);
                            Log.e(TAG, "Success added");
                            // Transaction was a success.
                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                            ivFavoriteIcon.setEnabled(true);
                            Log.e(TAG, "Failed to added");
                            // Transaction failed and was automatically canceled.
                        }
                    });
                } else {
                    ivFavoriteIcon.setImageResource(R.drawable.unfav_icon);
                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            RealmMovie find = realm.where(RealmMovie.class).equalTo("id", movie.getId()).findFirst();
                            find.deleteFromRealm();

                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            ivFavoriteIcon.setEnabled(true);
                            Log.e(TAG, "Success deleted");
                            // Transaction was a success.
                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                            Log.e(TAG, "Failed deleted");
                            ivFavoriteIcon.setEnabled(true);
                            // Transaction failed and was automatically canceled.
                        }
                    });
                }
            }
        });

        return rootView;
    }

    private void updateFav() {
        RealmMovie find = realm.where(RealmMovie.class).equalTo("id", movie.getId()).findFirst();

        if (find != null) {
            isFav = true;
            ivFavoriteIcon.setImageResource(R.drawable.fav_icon);
        } else {
            isFav = false;
            ivFavoriteIcon.setImageResource(R.drawable.unfav_icon);
        }
    }

    public void fetchTrailers() {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<TrailersResponse> call = apiService.getTrailers(movie.getId(), BuildConfig.OPEN_WEATHER_MAP_API_KEY);
        call.enqueue(new Callback<TrailersResponse>() {
            @Override
            public void onResponse(Call<TrailersResponse> call, Response<TrailersResponse> response) {
                Log.e("Response", String.valueOf(response.code()));
                List<Trailer> trailers = response.body().getResults();
                Log.e(TAG, "Number of trailers received: " + trailers.size());
                trailersReviewsAdapter.setTrailers(trailers);
                trailersReviewsAdapter.notifyDataSetChanged();
                // hide the progress dialog and show our grid
//                pbMovies.setVisibility(View.GONE);
//                rvMovies.setVisibility(View.VISIBLE);
//                if (movies != null) {
//                    moviesAdapter.clear();
//                    moviesAdapter.addAll(movies);
//                    moviesAdapter.notifyDataSetChanged();
//                }
            }

            @Override
            public void onFailure(Call<TrailersResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    public void fetchReviewers() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ReviewsResponse> call = apiService.getReviews(movie.getId(), BuildConfig.OPEN_WEATHER_MAP_API_KEY);
        call.enqueue(new Callback<ReviewsResponse>() {
            @Override
            public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
                Log.e("Response", String.valueOf(response.code()));
                List<Review> reviews = response.body().getResults();
                Log.e(TAG, "Number of reviews received: " + reviews.size());
                trailersReviewsAdapter.setReviews(reviews);
                trailersReviewsAdapter.notifyDataSetChanged();

                // hide the progress dialog and show our grid
//                pbMovies.setVisibility(View.GONE);
//                rvMovies.setVisibility(View.VISIBLE);
//                if (movies != null) {
//                    moviesAdapter.clear();
//                    moviesAdapter.addAll(movies);
//                    moviesAdapter.notifyDataSetChanged();
//                }
            }

            @Override
            public void onFailure(Call<ReviewsResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        realm.close();
    }

    private void fillData() {
        tvMovieTitle.setText(movie.getTitle());
        tvReleaseDate.setText(movie.getRelease_date());
        tvVoteAverage.setText(String.valueOf(movie.getVote_average()) + "/10.0");
        tvOverview.setText(movie.getOverview());
        Picasso.with(mContext).load(GlobalValues.IMAGE_BASE_URL + movie.getPoster_path()).into(ivMoviePoster);
    }

}
