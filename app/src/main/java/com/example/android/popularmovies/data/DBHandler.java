package com.example.android.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.popularmovies.data.MovieContract.MovieEntry;

/**
 * Created by mbeev on 08/04/2018.
 */

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "favourite_movie_list";

    // Default constructor
    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String SQL_CREATE_TABLE = "CREATE_TABLE " + MovieEntry.TABLE_NAME + " ("
                + MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MovieEntry.MOVIE_TITLE + " TEXT NOT NULL, "
                + MovieEntry.POSTER_IMAGE + " TEXT NOT NULL, "
                + MovieEntry.POSTER_IMAGE + " TEXT NOT NULL, "
                + MovieEntry.MOVIE_ID + " TEXT NOT NULL, "
                + MovieEntry.BACKDROP_IMAGE + " TEXT NOT NULL, "
                + MovieEntry.MOVIE_OVERVIEW + " TEXT NOT NULL, "
                + MovieEntry.MOVIE_RELEASE_DATE + " TEXT NOT NULL, "
                + MovieEntry.MOVIE_RATING + " TEXT NOT NULL, );";

       sqLiteDatabase.execSQL(SQL_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
