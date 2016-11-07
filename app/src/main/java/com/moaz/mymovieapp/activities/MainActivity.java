package com.moaz.mymovieapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.moaz.mymovieapp.R;
import com.moaz.mymovieapp.utils.GlobalValues;
import com.moaz.mymovieapp.adapters.MoviesAdapter;
import com.moaz.mymovieapp.fragments.MovieDetailFragment;
import com.moaz.mymovieapp.models.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends ActionBarActivity implements MoviesAdapter.MovieSelection {
    @Nullable
    @BindView(R.id.details_container)
    FrameLayout details;
    private boolean twoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (details != null) {
            twoPane = true;
        } else {
            twoPane = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelect(Movie movie) {
        if (twoPane) {
            MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
            Bundle args = new Bundle();
            args.putParcelable(GlobalValues.EXTRA_MOVIE, movie);
            movieDetailFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.details_container, movieDetailFragment).commit();
        } else {
            Intent intent = new Intent(this, MovieDetailActivity.class);
            intent.putExtra(GlobalValues.EXTRA_MOVIE, movie);
            startActivity(intent);
//            startActivity(new Intent(this,mActivity.class));
        }
    }
}
