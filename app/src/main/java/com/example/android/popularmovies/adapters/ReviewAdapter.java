package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 28/03/2018.
 */

public class ReviewAdapter extends ArrayAdapter<Review> {

    private List<Review> reviewsList;

    public ReviewAdapter(@NonNull Context context, List<Review> reviewsList) {

        super(context, 0, reviewsList);
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (reviewsList != null) {

            // get data for this position
            Review review = reviewsList.get(position);

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View reviewView = inflater.inflate(R.layout.review_list_item, parent, false);

            TextView authorTv = reviewView.findViewById(R.id.review_author);
            TextView contentTv = reviewView.findViewById(R.id.review_content);

            authorTv.setText(review.getReviewAuthor());
            contentTv.setText(review.getReviewContent());

            return reviewView;

        } else {

            return null;
        }
    }

    // notify the app that data has changed to refresh the view
    public void updateReviewData(List<Review> list) {

        if (list != null )
            reviewsList = new ArrayList<>(list);
        notifyDataSetChanged();
    }
}
