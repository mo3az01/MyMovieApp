package com.moaz.mymovieapp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moaz.mymovieapp.R;
import com.moaz.mymovieapp.Utils.GlobalValues;
import com.moaz.mymovieapp.models.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by XKCL0301 on 6/20/2016.
 */
public class MovieDetailFragment extends Fragment {

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

    //public TextView tvOverview, tvReleaseDate, tvVoteAverage, tvMovieTitle;
    //public ImageView ivMoviePoster;
    Movie movie = null;
    Activity mContext;
    private Unbinder unbinder;

    public MovieDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        mContext = getActivity();

        if (getActivity().getIntent().hasExtra(GlobalValues.EXTRA_MOVIE)) {
            movie = mContext.getIntent().getParcelableExtra(GlobalValues.EXTRA_MOVIE);
        }
        if (movie != null) {
            fillData();
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void fillData() {
        tvMovieTitle.setText(movie.getTitle());
        tvReleaseDate.setText(movie.getRelease_date());
        tvVoteAverage.setText(String.valueOf(movie.getVote_average()));
        tvOverview.setText(movie.getOverview());
        Picasso.with(mContext).load(GlobalValues.IMAGE_BASE_URL + movie.getPoster_path()).into(ivMoviePoster);
    }
}
