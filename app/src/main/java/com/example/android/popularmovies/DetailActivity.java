package com.example.android.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.popularmovies.adapters.ReviewAdapter;
import com.example.android.popularmovies.adapters.TrailerAdapter;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.model.PopularMoviesPreferences;
import com.example.android.popularmovies.model.Review;
import com.example.android.popularmovies.model.Trailer;
import com.example.android.popularmovies.tasks.GetReviewDataTask;
import com.example.android.popularmovies.tasks.GetTrailerDataTask;
import com.example.android.popularmovies.tasks.ReviewAsyncTaskListener;
import com.example.android.popularmovies.tasks.TrailerAsyncTaskListener;
import com.example.android.popularmovies.utlities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Matthew on 19/03/2018.
 */

public class DetailActivity extends AppCompatActivity {

    private static final String YOUTUBE = "YouTube";
    private ImageView movieBackdrop;
    private TextView movieTitle;
    private TextView movieRelease;
    private TextView movieDescription;
    private String formattedDate;
    private Button userRating;
    private String movieId;
    private List<Trailer> trailerList;
    private RecyclerView trailerRecyclerView;
    private TrailerAdapter trailerAdapter;

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
        trailerRecyclerView = findViewById(R.id.trailer_listView);

        // get data from MainActivity
        Intent intent = getIntent();
        final Movie movie = intent.getParcelableExtra("movie");

        if (movie != null) {

            // get Movie Id for reviews and trailer Array
            movieId = movie.getMovieId();

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


            // create RecylcerView for trailer list view, and assign adapter to it
            final RecyclerView.LayoutManager trailerLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            trailerRecyclerView.setLayoutManager(trailerLayoutManager);

            // set recyclerView to have a fixed size so that all items in the list are the same size.
            trailerRecyclerView.setHasFixedSize(true);

            trailerList = new ArrayList<>();
            trailerAdapter = new TrailerAdapter(this, new TrailerAdapter.OnTrailerClickHandler() {
                @Override
                public void OnTrailerClick(View item, int position) {

                    Trailer trailerSelected = trailerList.get(position);
                    Trailer trailer;
                    trailer = new Trailer(trailerSelected.getTrailerKey(),
                            trailerSelected.getTrailerName(), trailerSelected.getTrailerSite());

                    // check the trailer link is of YouTube type and launch intent if true
                    if (trailer.getTrailerSite().equals(YOUTUBE)) {
                        Intent launchTrailerIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(PopularMoviesPreferences.getTrailerBaseUrl()
                                + trailer.getTrailerKey()));
                        startActivity(launchTrailerIntent);
                    }

                }
            });
            trailerRecyclerView.setAdapter(trailerAdapter);
            loadTrailerData(movieId);


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

                    Intent reviewActivityIntent = new Intent(getApplicationContext(), ReviewActivity.class);
                    reviewActivityIntent.putExtra("movieId", movieId);
                    startActivity(reviewActivityIntent);

                }
            });


        }
    }

    public void loadTrailerData(String movieId) {

        URL getTrailerUrl = NetworkUtils.trailerUrl(movieId);
        new GetTrailerDataTask(new GetTrailerDataListener())
                .execute(getTrailerUrl);

    }

    public class GetTrailerDataListener implements TrailerAsyncTaskListener {

        @Override
        public void onTaskComplete(List<Trailer> list) {

            trailerList = list;

            // add reviews to adapter
            if (trailerList != null) {
                trailerAdapter.updateTrailerData(trailerList);
            }

            // if adapter is empty, hide the trailer list view
            if (trailerAdapter.getItemCount() == 0) {
                trailerRecyclerView.setVisibility(View.GONE);

            }
        }
    }

}
