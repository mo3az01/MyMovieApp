package com.moaz.mymovieapp.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.moaz.mymovieapp.R;
import com.moaz.mymovieapp.models.Review;
import com.moaz.mymovieapp.models.Trailer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xkcl0301 on 10/30/2016.
 */
public class TrailersReviewsAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    private List<Trailer> trailers;
    private List<Review> reviews;

    public TrailersReviewsAdapter(Context context) {
        mContext = context;
        trailers = new ArrayList<>();
        reviews = new ArrayList<>();
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return mContext.getString(R.string.trailers);
        else
            return mContext.getString(R.string.reviews);
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    @Override
    public int getCount() {
        return (trailers.isEmpty() ? 0 : 1) + (reviews.isEmpty() ? 0 : 1);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mLayoutInflater.inflate(R.layout.trailer_reviewer_item, container, false);
        RecyclerView list = (RecyclerView) view.findViewById(R.id.rv_trailers_reviews);

        if (position == 0) {
            list.setLayoutManager(new LinearLayoutManager(mContext));
            list.setAdapter(new TrailersAdapter(mContext, trailers));
        } else {
            list.setLayoutManager(new LinearLayoutManager(mContext));
            list.setAdapter(new ReviewsAdapter(mContext, reviews));
        }

        container.addView(view);
        return view;
    }
}
