package com.example.android.popularmovies.utlities;

import android.net.Uri;

import com.example.android.popularmovies.model.PopularMoviesPreferences;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Matthew on 15/03/2018.
 */

public class NetworkUtils {

    public static final String baseUrl = PopularMoviesPreferences.getBaseUrl();
    public static final String apiKeyBuilder = PopularMoviesPreferences.getApiBuilder();
    public static final String reviewsString = PopularMoviesPreferences.getReviews();
    public static final String trailerString = PopularMoviesPreferences.getVideos();
    public static final String apiKey = PopularMoviesPreferences.getApiKey();


    /**
     * this method builds the URL that will query the API.
     * Depending on the type of query inputed, a different URL will be created
     *
     * @param typeOfQuery either 'popular' or 'top rated'
     * @return The URL to query the API
     */

    public static URL queryUrl(String typeOfQuery) {

        Uri builtUri = Uri.parse(baseUrl + typeOfQuery + apiKeyBuilder + apiKey).buildUpon()
                                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL reviewUrl(String movieId) {

       Uri builtUri = Uri.parse(baseUrl + movieId + reviewsString + apiKeyBuilder + apiKey).buildUpon()
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL trailerUrl(String movieId) {

        Uri builtUri = Uri.parse(baseUrl + movieId + trailerString + apiKeyBuilder + apiKey).buildUpon()
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {

            InputStream inputStream = urlConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
