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

    public ReviewAdapter(@NonNull Context context, List<Review> list) {

        super(context, 0, list);
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // get data for this position
        Review review = getItem(position);

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.review_list_item, parent, false);

        }


        // find IDs to assign views to
        TextView authorTv = convertView.findViewById(R.id.review_author);
        TextView contentTv = convertView.findViewById(R.id.review_content);

        // update views to show movie review details
        authorTv.setText(review.getReviewAuthor());
        contentTv.setText(review.getReviewContent());

        return convertView;

    }

}
