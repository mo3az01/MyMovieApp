package com.moaz.mymovieapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moaz.mymovieapp.R;
import com.moaz.mymovieapp.models.Trailer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xkcl0301 on 10/30/2016.
 */
public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ViewHolder> {

    List<Trailer> trailers;
    Context mContex;
    LayoutInflater mLayoutInflater;

    public TrailersAdapter(Context mContex,List<Trailer> trailers) {
        this.trailers = trailers;
        this.mContex = mContex;
        mLayoutInflater = (LayoutInflater) mContex.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public TrailersAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.trailer_item,null));
    }

    @Override
    public void onBindViewHolder(TrailersAdapter.ViewHolder viewHolder, int i) {
        viewHolder.tvTrailerName.setText(trailers.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_trailer_name)
        TextView tvTrailerName;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
        }
    }

}
