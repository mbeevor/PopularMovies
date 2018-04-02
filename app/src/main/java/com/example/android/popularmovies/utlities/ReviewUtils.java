package com.example.android.popularmovies.utlities;

import android.text.TextUtils;

import com.example.android.popularmovies.model.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 23/03/2018.
 */

public class ReviewUtils {

    private static final String REVIEW_RESULTS = "results";
    private static final String REVIEW_AUTHOR = "author";
    private static final String REVIEW_CONTENT = "content";

    public static List<Review> getSimpleReviewQueryStringFromJson(String reviewJson) {

        if (TextUtils.isEmpty(reviewJson)) {
            return null;
        }

        List<Review> reviews = new ArrayList<>();

        try {

            JSONObject reviewJsonObject = new JSONObject(reviewJson);

            JSONArray reviewArray = reviewJsonObject.getJSONArray(REVIEW_RESULTS);

            for (int j = 0; 1 < reviewArray.length(); j++) {

                JSONObject currentReview = reviewArray.getJSONObject(j);

                String reviewAuthor = currentReview.getString(REVIEW_AUTHOR);
                String reviewContent = currentReview.getString(REVIEW_CONTENT);

                Review review = new Review(reviewAuthor, reviewContent);
                reviews.add(review);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviews;
    }
}
