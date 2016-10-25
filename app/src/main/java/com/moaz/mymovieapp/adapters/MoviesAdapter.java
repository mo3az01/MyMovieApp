package com.moaz.mymovieapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.moaz.mymovieapp.R;
import com.moaz.mymovieapp.Utils.GlobalValues;
import com.moaz.mymovieapp.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xkcl0301 on 6/19/2016.
 */
public class MoviesAdapter extends BaseAdapter {
    List<Movie> mMovies;
    Context mContext;

    public MoviesAdapter() {
        mMovies = new ArrayList<>();
    }

    public MoviesAdapter(Context context) {
        mMovies = new ArrayList<>();
        mContext = context;

    }

    public MoviesAdapter(List<Movie> movies, Context context) {
        this.mMovies = movies;
        mContext = context;
    }

    public void add(Movie movie) {
        mMovies.add(movie);
    }

    public void addAll(List<Movie> movieList) {
        mMovies.addAll(movieList);
    }

    @Override
    public int getCount() {
        return mMovies.size();
    }

    @Override
    public Movie getItem(int i) {
        return mMovies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mMovies.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grid_movie_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tvMovieName.setText(mMovies.get(i).getTitle());
        Picasso.with(mContext).load(GlobalValues.IMAGE_BASE_URL + mMovies.get(i).getPoster_path()).into(viewHolder.ivMoviePoster);
        return view;
    }

    public class ViewHolder {
        @BindView(R.id.iv_movie_poster)
        ImageView ivMoviePoster;
        @BindView(R.id.tv_movie_poster)
        TextView tvMovieName;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
