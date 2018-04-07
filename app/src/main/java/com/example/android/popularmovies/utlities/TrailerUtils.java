package com.example.android.popularmovies.utlities;

import android.text.TextUtils;

import com.example.android.popularmovies.model.Review;
import com.example.android.popularmovies.model.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbeev on 06/04/2018.
 */

public class TrailerUtils {

    private static final String TRAILER_RESULTS = "results";
    private static final String TRAILER_KEY = "key";
    private static final String TRAILER_NAME = "name";
    private static final String TRAILER_SITE = "site";

    public static List<Trailer> getSimpleReviewQueryStringFromJson(String trailerJson) {


        if (TextUtils.isEmpty(trailerJson)) {
            return null;
        }

        List<Trailer> trailers = new ArrayList<>();

        try {

            JSONObject trailerJsonObject = new JSONObject(trailerJson);

            JSONArray trailerArray = trailerJsonObject.getJSONArray(TRAILER_RESULTS);

            for (int k = 0; 1 < trailerArray.length(); k++) {

                JSONObject currentTrailer = trailerArray.getJSONObject(k);

                String trailerKey = currentTrailer.getString(TRAILER_KEY);
                String trailerName = currentTrailer.getString(TRAILER_NAME);
                String trailerSite = currentTrailer.getString(TRAILER_SITE);

                Trailer trailer = new Trailer(trailerKey, trailerName, trailerSite);
                trailers.add(trailer);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trailers;
    }

}
