package com.moaz.mymovieapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moaz.mymovieapp.R;
import com.moaz.mymovieapp.Utils.GlobalValues;
import com.moaz.mymovieapp.activities.MovieDetailActivity;
import com.moaz.mymovieapp.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xkcl0301 on 6/19/2016.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    List<Movie> mMovies;
    Context mContext;

    public MoviesAdapter() {
        mMovies = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.grid_movie_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.tvMovieName.setText(mMovies.get(i).getTitle());
        Picasso.with(mContext).load(GlobalValues.IMAGE_BASE_URL + mMovies.get(i).getPoster_path()).into(viewHolder.ivMoviePoster);
    }

    public MoviesAdapter(Context context) {
        mMovies = new ArrayList<>();
        mContext = context;
    }

    public MoviesAdapter(List<Movie> movies, Context context) {
        this.mMovies = movies;
        mContext = context;
    }

    public void clear() {
        mMovies.clear();
    }

    public void add(Movie movie) {
        mMovies.add(movie);
    }

    public void addAll(List<Movie> movieList) {
        mMovies.addAll(movieList);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_movie_poster)
        ImageView ivMoviePoster;
        @BindView(R.id.tv_movie_poster)
        TextView tvMovieName;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mContext, MovieDetailActivity.class);
            Movie movie = mMovies.get(getPosition());
            intent.putExtra(GlobalValues.EXTRA_MOVIE, movie);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }
}
