package com.example.android.popularmovies.utlities;

import android.net.Uri;

import com.example.android.popularmovies.data.PopularMoviesPreferences;

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

    // the format we want the API to return
    private static final String format = "json";

    private static final String baseUrl = PopularMoviesPreferences.getBaseUrl();
    private static final String popular = PopularMoviesPreferences.getPopular();
    private static final String apiKey = PopularMoviesPreferences.getApiKey();


    /**
     * this method builds the URL that will query the API.
     * Depending on the type of query inputed, a different URL will be created
     *
     * @param typeOfQuery either 'popular' or 'top rated'
     * @return The URL to query the API
     */

    public static URL buildUrl(String typeOfQuery) {

        Uri builtUri = Uri.parse(baseUrl).buildUpon()
                .appendPath(popular)
                .appendPath(apiKey)
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
