package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;

import static com.example.android.popularmovies.data.MovieContract.MovieEntry._ID;

/**
 * Created by Matthew on 09/04/2018.
 */

public abstract class CursorRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private Context context;
    private Cursor cursor;
    private boolean dataValid;
    private int rowId;
    private DataSetObserver dataSetObserver;

    public CursorRecyclerViewAdapter(Context thisContext, Cursor data) {

        context = thisContext;
        cursor = data;
        dataValid = cursor != null;
        rowId = dataValid ? cursor.getColumnIndex(_ID) : -1;
        dataSetObserver = new NotifyingDataSetObserver();
        if (cursor != null) {
            cursor.registerDataSetObserver(dataSetObserver);
        }

    }

    public Cursor getCursor() {
        return cursor;
    }

    @Override
    public int getItemCount() {

        if (cursor != null) {
            return cursor.getCount();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {

        if (dataValid && cursor != null && cursor.moveToPosition(position)) {
            return cursor.getLong(rowId);
        }
        return 0;
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(true);
    }


    public abstract void onBindViewHolder(VH viewHolder, Cursor cursor);

    @Override
    public void onBindViewHolder(VH holder, int position) {

        onBindViewHolder(holder, cursor);
    }

    public Cursor swapCursor(Cursor newCursor) {

        if (cursor == newCursor) {
            return null;
        }
        final Cursor oldCursor = cursor;
        if (oldCursor != null && dataSetObserver != null) {
            oldCursor.unregisterDataSetObserver(dataSetObserver);

        }

        cursor = newCursor;
        if (cursor != null) {
            if (dataSetObserver != null) {
                cursor.registerDataSetObserver(dataSetObserver);

            }

            rowId = newCursor.getColumnIndexOrThrow(_ID);
            dataValid = true;
            notifyDataSetChanged();
        } else {
            rowId = -1;
            dataValid = false;
            notifyDataSetChanged();
        }

        return oldCursor;
    }

    private class NotifyingDataSetObserver extends DataSetObserver {

        @Override
        public void onChanged() {
            super.onChanged();
            dataValid = true;
            notifyDataSetChanged();
        }
    }
}
