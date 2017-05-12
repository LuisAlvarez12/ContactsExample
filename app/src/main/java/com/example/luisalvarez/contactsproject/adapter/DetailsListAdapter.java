package com.example.luisalvarez.contactsproject.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.luisalvarez.contactsproject.R;
import com.example.luisalvarez.contactsproject.util.Config;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by luisalvarez on 5/12/17.
 */

public class DetailsListAdapter extends RecyclerView.Adapter<ContactViewHolder> {

    private Cursor mItems;
    private Context mContext;
    private ListOnClickListener mOnClickListener;
    private int[] iconArray = {
            R.drawable.icon_black_home,
            R.drawable.icon_black_mobile,
            R.drawable.icon_black_work,
            R.drawable.ic_web_black_48dp,
            R.drawable.ic_date_range_black_48dp,
            R.drawable.ic_location_on_black_48dp
    };

    public DetailsListAdapter(Context activity, Cursor items, ListOnClickListener listOnClickListener) {
        this.mItems = items;
        this.mContext = activity;
        this.mOnClickListener = listOnClickListener;
        Log.d("tran", "created tje adpadter");
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_small_contact, parent, false);
        Log.d("tran", "created tje viewholder");
        return new ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder holder, final int position) {
        if (mItems != null) {
            mItems.moveToFirst();
            Log.d("tran", "" + position);
            holder.contactPhoto.setImageDrawable(mContext.getResources().getDrawable(iconArray[position]));
            switch (position) {
                case 0:
                    holder.contactName.setText(mItems.getString(Config.POSITION_LIST_CONTACT_PHONE_HOME));
                    holder.holderlayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnClickListener.ondialClick(mItems.getString(Config.POSITION_LIST_CONTACT_PHONE_HOME));
                        }
                    });
                    break;
                case 1:
                    holder.contactName.setText(mItems.getString(Config.POSITION_LIST_CONTACT_PHONE_MOBILE));
                    holder.holderlayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnClickListener.ondialClick(mItems.getString(Config.POSITION_LIST_CONTACT_PHONE_MOBILE));
                        }
                    });
                    break;
                case 2:
                    holder.contactName.setText(mItems.getString(Config.POSITION_LIST_CONTACT_COMPANY) + "\n"
                            + mItems.getString(Config.POSITION_LIST_CONTACT_PHONE_WORK));
                    holder.holderlayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnClickListener.ondialClick(mItems.getString(Config.POSITION_LIST_CONTACT_PHONE_WORK));
                        }
                    });
                    break;
                case 3:
                    holder.contactName.setText(mItems.getString(Config.POSITION_LIST_CONTACT_WEBSITE));
                    holder.holderlayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnClickListener.onWebsiteClick(mItems.getString(Config.POSITION_LIST_CONTACT_WEBSITE));
                        }
                    });
                    break;
                case 4:
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                    String dateInMilli = mItems.getString(Config.POSITION_LIST_CONTACT_BIRTHDATE);

                    // Create a calendar object that will convert the date and time value in milliseconds to date.
                    if (!dateInMilli.equals("") || dateInMilli != null || Long.parseLong(dateInMilli) < 0) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(Long.parseLong(dateInMilli));
                        holder.contactName.setText(formatter.format(calendar.getTime()));
                    } else {
                        holder.contactName.setText("");
                    }
                    break;
                case 5:
                    String address = "";
                    address = address + mItems.getString(Config.POSITION_LIST_CONTACT_ADDRESS_STREET) + "\n"
                            + mItems.getString(Config.POSITION_LIST_CONTACT_ADDRESS_CITY) + ","
                            + mItems.getString(Config.POSITION_LIST_CONTACT_ADDRESS_STATE) + "\n"
                            + mItems.getString(Config.POSITION_LIST_CONTACT_ADDRESS_ZIP);
                    holder.contactName.setText(address);

            }
//                holder.contactPhoto.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mOnClickListener.onContactClicked(holder, position);
//                    }
//                });
        }
    }

    public void setCursor(Cursor cursor) {
        this.mItems = cursor;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    interface StockAdapterOnClickHandler {
        void onClick(String symbol);
    }
}


