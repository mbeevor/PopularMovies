package com.example.android.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.adapters.TrailerAdapter;
import com.example.android.popularmovies.data.DBHandler;
import com.example.android.popularmovies.data.MovieContract;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.model.PopularMoviesPreferences;
import com.example.android.popularmovies.model.Trailer;
import com.example.android.popularmovies.tasks.GetTrailerDataTask;
import com.example.android.popularmovies.tasks.TrailerAsyncTaskListener;
import com.example.android.popularmovies.utlities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.android.popularmovies.data.MovieContract.MovieEntry.MOVIE_ID;

/**
 * Created by Matthew on 19/03/2018.
 */

public class DetailActivity extends AppCompatActivity {

    private static final String YOUTUBE = "YouTube";
    final Context context = this;
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
    private FloatingActionButton fab;
    private DBHandler dbHandler;
    private Cursor cursor;
    private Movie movie;

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
        fab = findViewById(R.id.floatingActionButton);

        // get data from MainActivity
        Intent intent = getIntent();
        movie = intent.getParcelableExtra("movie");

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
            setTitle(movie.getMovieTitle());
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
                    reviewActivityIntent.putExtra("movieTitle", movie.getMovieTitle());
                    startActivity(reviewActivityIntent);

                }
            });

            // initialise database
            dbHandler = new DBHandler(this);

            // update favourite star based on database entry
            if (movieInDatabase() == true) {
                fab.setImageResource(R.drawable.ic_star_border_selected);
            } else {
                fab.setImageResource(R.drawable.ic_star_border_unselected);
            }

            // add movie to favourites
            fab.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {

                    if (movieInDatabase() == true) {

                        // confirm deletion
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage(R.string.delete_warning_message);

                        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int id) {

                                // delete movie
                                String currentMovieId = MovieContract.MovieEntry.MOVIE_ID;
                                String[] currentMovieIdArray = {movie.getMovieId()};
                                int rowsDeleted = getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI,
                                        currentMovieId + " =? ",
                                        currentMovieIdArray);
                                if (rowsDeleted != 0) {
                                    Toast.makeText(getApplicationContext(), R.string.movie_removed_from_favourites, Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), R.string.movie_not_removed_from_favourites, Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                        // if cancelled, go back
                        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int id) {
                                if (dialogInterface != null) {
                                    dialogInterface.dismiss();
                                }
                            }
                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();


                    } else {

                        // build new movie for database
                        ContentValues values = new ContentValues();
                        values.put(MovieContract.MovieEntry.MOVIE_TITLE, movie.getMovieTitle());
                        values.put(MovieContract.MovieEntry.POSTER_IMAGE, movie.getPosterImage());
                        values.put(MovieContract.MovieEntry.MOVIE_ID, movie.getMovieId());
                        values.put(MovieContract.MovieEntry.BACKDROP_IMAGE, movie.getBackdropImage());
                        values.put(MovieContract.MovieEntry.MOVIE_OVERVIEW, movie.getMovieOverview());
                        values.put(MovieContract.MovieEntry.MOVIE_RELEASE_DATE, movie.getMovieReleaseDate());
                        values.put(MovieContract.MovieEntry.MOVIE_RATING, movie.getMovieRating());


                        // add movie to favourites database
                        Uri newUri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, values);
                        if (newUri != null) {
                            Toast.makeText(getApplicationContext(), R.string.added_to_favourites, Toast.LENGTH_SHORT).show();
                            fab.setImageResource(R.drawable.ic_star_border_selected);
                            String searchUrl = null;
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.not_added_to_favourites, Toast.LENGTH_SHORT).show();
                        }


                    }
                }
            });


        }
    }

    public void loadTrailerData(String movieId) {

        URL getTrailerUrl = NetworkUtils.trailerUrl(movieId);
        new GetTrailerDataTask(new GetTrailerDataListener())
                .execute(getTrailerUrl);

    }

    // check if movie is in favourites list
    public boolean movieInDatabase() {

        cursor = dbHandler.getReadableDatabase().query(MovieContract.MovieEntry.TABLE_NAME,
                new String[]{MOVIE_ID},
                MOVIE_ID + " = ?",
                new String[]{movieId}, null, null, null, null);

        return cursor.moveToFirst();
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
