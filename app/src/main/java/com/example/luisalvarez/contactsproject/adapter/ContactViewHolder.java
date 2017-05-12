package com.example.luisalvarez.contactsproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.luisalvarez.contactsproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by luisalvarez on 5/11/17.
 */

public class ContactViewHolder extends RecyclerView.ViewHolder {
    public ImageView contactPhoto;
    public ImageView iconHome;
    public ImageView iconMobile;
    public ImageView iconWork;
    public LinearLayout holderlayout;
    @BindView(R.id.item_primary_text)
    TextView contactName;
    @BindView(R.id.item_contact_phone)
    TextView contactPhone;

    public ContactViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        holderlayout = (LinearLayout) itemView.findViewById(R.id.holder_layout);
        contactPhoto = (ImageView) itemView.findViewById(R.id.item_contact_image);
//            itemView.setOnClickListener(this);
    }

    public ContactViewHolder(View itemView, int i) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        iconHome = (ImageView) itemView.findViewById(R.id.item_icon_home);
        iconMobile = (ImageView) itemView.findViewById(R.id.item_icon_mobile);
        iconWork = (ImageView) itemView.findViewById(R.id.item_icon_work);
        contactPhoto = (ImageView) itemView.findViewById(R.id.item_contact_image);
//            itemView.setOnClickListener(this);
    }
}