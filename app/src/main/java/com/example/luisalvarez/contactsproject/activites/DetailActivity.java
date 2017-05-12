package com.example.luisalvarez.contactsproject.activites;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.luisalvarez.contactsproject.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private Cursor mContactData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ImageView imageView = (ImageView) findViewById(R.id.item_contact_image);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            SharedElementTransisition(imageView);
        }
    }

    /**
     * Shared Element Transisition for jelly bean and greater
     */
    private void SharedElementTransisition(ImageView imageView) {
        supportPostponeEnterTransition();
        String url = getIntent().getStringExtra(Contacts_List.TAG_URL);
        Picasso.with(this).load(url).fit().noFade().centerCrop()
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        supportStartPostponedEnterTransition();
                    }

                    @Override
                    public void onError() {
                        supportStartPostponedEnterTransition();
                    }
                });
    }
}
