package com.example.android.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.popularmovies.R;

/**
 * Created by mbeev on 08/04/2018.
 */

public class MovieProvider extends ContentProvider {

    // static codes or the movies table and a single movie
    private static final int MOVIES = 100;
    private static final int MOVIES_ID = 101;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // static initialiser run each time anything is called from database
    static {
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.MOVIE_PATH, MOVIES);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.MOVIE_PATH + "#", MOVIES_ID);
    }

    private DBHandler dbHandler;

    @Override
    public boolean onCreate() {
        dbHandler = new DBHandler(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase database = dbHandler.getReadableDatabase();

        // create cursor for database
        Cursor cursor;

        int match = uriMatcher.match(uri);
        switch (match) {

            case MOVIES:
                cursor = database.query(MovieContract.MovieEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case MOVIES_ID:
                selection = MovieContract.MovieEntry._ID + "=?";
                selectionArgs = new String[]{
                        String.valueOf(ContentUris.parseId(uri))
                };
                cursor = database.query(MovieContract.MovieEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException(R.string.database_error + uri.toString());
        }

        // udpate cursor if data changes
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        final int match = uriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                return MovieContract.MovieEntry.CONTENT_LIST_TYPE;
            case MOVIES_ID:
                return MovieContract.MovieEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException(R.string.unknown_uri + uri.toString() + R.string.with_match + match);
        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        final int match = uriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                return insertMovie(uri, contentValues);
            default:
                throw new IllegalArgumentException(R.string.insertion_unsupported + uri.toString());
        }

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        // number of rows to be deleted
        int rowsDeleted;

        // access writeable database
        SQLiteDatabase database = dbHandler.getWritableDatabase();

        // notify listener data has changed
        getContext().getContentResolver().notifyChange(uri, null);

        final int match = uriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                rowsDeleted = database.delete(MovieContract.MovieEntry.TABLE_NAME,
                        selection, selectionArgs);
                if (rowsDeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsDeleted;

            case MOVIES_ID:
                selection = MovieContract.MovieEntry._ID + "=?";
                selectionArgs = new String[]{
                        String.valueOf(ContentUris.parseId(uri))
                };
                rowsDeleted = database.delete(MovieContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
                if (rowsDeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsDeleted;

            default:
                throw new IllegalArgumentException(R.string.deletion_failed + uri.toString());

        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }


    // method to add favourite movie
    private Uri insertMovie(Uri uri, ContentValues contentValues) {

        SQLiteDatabase database = dbHandler.getWritableDatabase();
        long id = database.insert(MovieContract.MovieEntry.TABLE_NAME, null, contentValues);
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

}

