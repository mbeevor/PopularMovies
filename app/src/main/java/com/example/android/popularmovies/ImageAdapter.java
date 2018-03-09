package com.example.android.popularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageAdapter extends BaseAdapter {

    private Context context;

    public static String[] mThumbIds = {

            "http://image.tmdb.org/t/p/original/tWqifoYuwLETmmasnGHO7xBjEtt.jpg",
            "http://image.tmdb.org/t/p/original/tWqifoYuwLETmmasnGHO7xBjEtt.jpg",
            "http://image.tmdb.org/t/p/original/tWqifoYuwLETmmasnGHO7xBjEtt.jpg",

    };


    public ImageAdapter(Context c) {
        context = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public String getItem(int position) {
        return mThumbIds[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(480, 480));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        // convert each gridbox into an image, if there is one!
        String url = getItem(position);
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.loader)
                .fit()
                .into(imageView);

        return imageView;
    }


}