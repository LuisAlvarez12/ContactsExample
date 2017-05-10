package com.example.luisalvarez.contactsproject.util;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.luisalvarez.contactsproject.R;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by luisalvarez on 5/10/17.
 */

private class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {
    private Cursor mCursor;

    public ContactListAdapter(Cursor cursor) {
        mCursor = cursor;
    }

    //get id of current position
    @Override
    public long getItemId(int position) {
        mCursor.moveToPosition(position);
        return ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //view holder detail
//        View view = getLayoutInflater().inflate(R.layout.list_item_article, parent, false);
//        final ViewHolder vh = new ViewHolder(view);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //start act
//                startActivity(new Intent(Intent.ACTION_VIEW,
//                        //getItemId returns long _ID
//                        //actionview redirects to articleDetailActivity as detailed in the manifest
//                        ItemsContract.Items.buildItemUri(getItemId(vh.getAdapterPosition()))));
//            }
//        });
        return vh;
    }



    @Override
    @SuppressWarnings("deprecation")
    public void onBindViewHolder(final ViewHolder holder, int position) {
        mCursor.moveToPosition(position);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }



public static class ViewHolder extends RecyclerView.ViewHolder {


    public ViewHolder(View view) {
        super(view);

    }
}
}