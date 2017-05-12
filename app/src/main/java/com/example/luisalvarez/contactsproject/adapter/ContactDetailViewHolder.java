package com.example.luisalvarez.contactsproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luisalvarez.contactsproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by luisalvarez on 5/11/17.
 */

public class ContactDetailViewHolder extends RecyclerView.ViewHolder {
    public ImageView contactPhoto;
    @BindView(R.id.item_primary_text)
    TextView contactName;
    @BindView(R.id.item_contact_phone)
    TextView contactPhone;

    public ContactDetailViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        contactPhoto = (ImageView) itemView.findViewById(R.id.item_contact_image);
//            itemView.setOnClickListener(this);
    }
}