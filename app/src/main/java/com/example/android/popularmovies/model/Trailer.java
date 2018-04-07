package com.example.android.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mbeev on 06/04/2018.
 */

public class Trailer implements Parcelable {


    private String trailerKey;
    private String trailerName;
    private String trailerSite;


    // main constructor for creating a review
    public Trailer(String key, String name, String site) {

        trailerKey = key;
        trailerName = name;
        trailerSite = site;

    }

    // parcelable constructor
    private Trailer(Parcel parcel) {

        trailerKey = parcel.readString();
        trailerName = parcel.readString();
        trailerSite = parcel.readString();

    }


    public String getTrailerKey() {
        return trailerKey;
    }

    public String getTrailerName() {
        return trailerName;
    }

    public String getTrailerSite() {
        return trailerSite;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(trailerKey);
        dest.writeString(trailerName);
        dest.writeString(trailerSite);

    }

    public static final Parcelable.Creator<Trailer> CREATOR = new Parcelable.Creator<Trailer>() {


        @Override
        public Trailer createFromParcel(Parcel parcel) {
            return new Trailer(parcel);
        }

        @Override
        public Trailer[] newArray(int i) {
            return new Trailer[0];
        }
    };

}
