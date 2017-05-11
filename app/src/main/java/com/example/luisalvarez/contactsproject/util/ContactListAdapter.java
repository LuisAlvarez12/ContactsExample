package com.example.luisalvarez.contactsproject.util;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luisalvarez.contactsproject.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by luisalvarez on 5/10/17.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

        private Cursor items;
        private Context context;

        public ContactListAdapter(Context activity,Cursor items) {
            this.items = items;
            this.context = activity;
        }

        @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_contact, parent, false);
            return new ViewHolder(v);
        }
        interface StockAdapterOnClickHandler {
            void onClick(String symbol);
        }

        @Override public void onBindViewHolder(ViewHolder holder, int position) {
            items.moveToPosition(position);
            holder.contactName.setText(items.getString(Config.POSITION_PROJECTION_CONTACT_NAME));
            holder.contactPhone.setText(items.getString(Config.POSITION_PROJECTION_CONTACT_PHONE));
            Picasso.with(context)
                    .load(items.getString(Config.POSITION_PROJECTION_CONTACT_IMAGE_SMALL))
                    .centerInside()
                    .into(holder.contactPhoto);
        }

        public void setCursor(Cursor cursor) {
            this.items = cursor;
            notifyDataSetChanged();
        }

        @Override public int getItemCount() {
            int count = 0;
            if (items != null) {
                count = items.getCount();
            }
            return count;
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            @BindView(R.id.item_contact_name)
            TextView contactName;

            @BindView(R.id.item_contact_phone)
            TextView contactPhone;

            @BindView(R.id.item_contact_image)
            ImageView contactPhoto;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int adapterPosition = getAdapterPosition();
                items.moveToPosition(adapterPosition);
//                Intent toDetail = new Intent(context, DetailActivity.class);
//                toDetail.putExtra("id",items.getString(DataContract.WorkoutsEntry.POSITION_WORKOUT_ID));
//                context.startActivity(toDetail);
            }
        }
}