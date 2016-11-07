package com.moaz.mymovieapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moaz.mymovieapp.R;
import com.moaz.mymovieapp.models.Review;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xkcl0301 on 10/30/2016.
 */
public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    List<Review> reviews;
    Context mContex;
    LayoutInflater mLayoutInflater;

    public ReviewsAdapter(Context mContex, List<Review> reviews) {
        this.reviews = reviews;
        this.mContex = mContex;
        mLayoutInflater = (LayoutInflater) mContex.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.review_item, null));
    }

    @Override
    public void onBindViewHolder(ReviewsAdapter.ViewHolder viewHolder, int i) {
        viewHolder.tvReviewAuthor.setText(reviews.get(i).getAuthor());
//        viewHolder.tvReviewContent.setText(reviews.get(i).getContent());
        viewHolder.etvReviewContent.setText(reviews.get(i).getContent());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_review_author)
        TextView tvReviewAuthor;
        //        @BindView(R.id.tv_review_content)
//        TextView tvReviewContent;
        @BindView(R.id.expand_text_view)
        ExpandableTextView etvReviewContent;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
