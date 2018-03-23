package com.example.android.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.PopularMoviesPreferences;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Matthew on 19/03/2018.
 */

public class DetailActivity extends AppCompatActivity {

    private ImageView movieBackdrop;
    private TextView movieTitle;
    private TextView movieRelease;
    private TextView movieDescription;
    private String formattedDate;
    private Button userRating;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // assign views to IDs
        movieBackdrop = findViewById(R.id.movie_backdrop_iv);
        movieTitle = findViewById(R.id.movie_title_tv);
        movieRelease = findViewById(R.id.release_date_tv);
        movieDescription = findViewById(R.id.movie_description_tv);
        userRating = findViewById(R.id.user_rating_button);

        // get data from MainActivity
        Intent intent = getIntent();
        final Movie movie = intent.getParcelableExtra("movie");

        // convert JSON date to readable Year
        String JSONdate = movie.getMovieReleaseDate();

        try {
            SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date year = currentDateFormat.parse(JSONdate);

            SimpleDateFormat revisedDateFormat = new SimpleDateFormat("YYYY");
            formattedDate = revisedDateFormat.format(year);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        // set views to data from MainActivity
        movieTitle.setText(movie.getMovieTitle());
        movieRelease.setText(formattedDate);
        movieDescription.setText(movie.getMovieOverview());
        userRating.setText(movie.getMovieRating());

        String backdropImage = movie.getBackdropImage();

        // remove image view if there is no image
        if (TextUtils.isEmpty(backdropImage)) {
            movieBackdrop.setVisibility(View.GONE);

            // use Picasso for backdrop image
        } else {
            String backdropImageUrl = PopularMoviesPreferences.getImageBaseUrl() + backdropImage;

            Picasso.with(this)
                    .load(backdropImageUrl)
                    .into(movieBackdrop);

        }

        userRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String reviewUrl = PopularMoviesPreferences.getBaseUrl() + movie.getMovieId() + "/reviews?" + PopularMoviesPreferences.getApiKey();
                Intent reviewIntent = new Intent(Intent.ACTION_VIEW);
                reviewIntent.setData(Uri.parse(reviewUrl));
                startActivity(reviewIntent);

            }
        });

        // get movie ID and assign to getReview


    }


}
