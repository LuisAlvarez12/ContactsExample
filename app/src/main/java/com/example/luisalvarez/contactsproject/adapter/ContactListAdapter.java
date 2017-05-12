package com.example.luisalvarez.contactsproject.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.luisalvarez.contactsproject.R;
import com.example.luisalvarez.contactsproject.util.Config;
import com.squareup.picasso.Picasso;

/**
 * Created by luisalvarez on 5/10/17.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactViewHolder> {

    private Cursor mItems;
    private Context mContext;
    private ListOnClickListener mOnClickListener;
    private int mLayout;

    public ContactListAdapter(Context activity, Cursor items, ListOnClickListener listOnClickListener, int layout) {
        this.mItems = items;
        this.mContext = activity;
        this.mOnClickListener = listOnClickListener;
        this.mLayout = layout;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(mLayout, parent, false);
        return new ContactViewHolder(v, 0);
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder holder, final int position) {
        if (mItems != null) {
            mItems.moveToPosition(position);
            switch (mLayout) {
                case R.layout.item_list_med_contact:
                    final String numMobile = mItems.getString(Config.POSITION_PROJECTION_CONTACT_MOBILE);
                    final String numHome = mItems.getString(Config.POSITION_PROJECTION_HOME);
                    final String numWork = mItems.getString(Config.POSITION_PROJECTION_WORK);
                    if (numMobile.equals("")) {
                        holder.iconMobile.setVisibility(View.GONE);
                    }
                    if (numHome.equals("")) {
                        holder.iconHome.setVisibility(View.GONE);
                    }
                    if (numWork.equals("")) {
                        holder.iconWork.setVisibility(View.GONE);
                    }

                    holder.contactName.setText(mItems.getString(Config.POSITION_PROJECTION_CONTACT_NAME));
                    Picasso.with(mContext)
                            .load(mItems.getString(Config.POSITION_PROJECTION_CONTACT_IMAGE_SMALL))
                            .into(holder.contactPhoto);


                    holder.iconHome.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!numHome.equals("")) {
                                mOnClickListener.ondialClick(numHome);
                            }
                        }
                    });
                    holder.iconWork.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!numWork.equals("")) {
                                mOnClickListener.ondialClick(numWork);
                            }
                        }
                    });

                    holder.iconMobile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!numMobile.equals("")) {
                                mOnClickListener.ondialClick(numMobile);
                            }
                        }
                    });

                    holder.contactPhoto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnClickListener.onContactClicked(holder, position);
                        }
                    });
                    break;
                case R.layout.item_list_small_contact:
                    holder.contactName.setText(mItems.getString(Config.POSITION_PROJECTION_CONTACT_NAME));
                    Picasso.with(mContext)
                            .load(mItems.getString(Config.POSITION_PROJECTION_CONTACT_IMAGE_SMALL))
                            .into(holder.contactPhoto);

                    holder.contactPhoto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnClickListener.onContactClicked(holder, position);
                        }
                    });
                    break;
            }
        }
    }

    public void setCursor(Cursor cursor) {
        this.mItems = cursor;
        notifyDataSetChanged();
    }

    public void setmLayout(int layout) {
        this.mLayout = layout;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        int count = 0;
        if (mItems != null) {
            count = mItems.getCount();
        }
        return count;
    }

    interface StockAdapterOnClickHandler {
        void onClick(String symbol);
    }


}
