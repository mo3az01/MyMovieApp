package com.moaz.mymovieapp.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;

import com.moaz.mymovieapp.R;
import com.moaz.mymovieapp.fragments.MovieDetailFragment;
import com.moaz.mymovieapp.utils.GlobalValues;

public class MovieDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if (savedInstanceState == null) {
            MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
            if (getIntent().hasExtra(GlobalValues.EXTRA_MOVIE)) {
                movieDetailFragment.setArguments(getIntent().getExtras());
//                movie = mContext.getIntent().getParcelableExtra(GlobalValues.EXTRA_MOVIE);
            }

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, movieDetailFragment)
                    .commit();
        }
    }

}
