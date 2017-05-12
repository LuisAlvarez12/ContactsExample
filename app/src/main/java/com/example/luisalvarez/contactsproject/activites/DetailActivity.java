package com.example.luisalvarez.contactsproject.activites;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luisalvarez.contactsproject.R;
import com.example.luisalvarez.contactsproject.adapter.ContactViewHolder;
import com.example.luisalvarez.contactsproject.adapter.DetailsListAdapter;
import com.example.luisalvarez.contactsproject.adapter.ListOnClickListener;
import com.example.luisalvarez.contactsproject.data.DataContract;
import com.example.luisalvarez.contactsproject.util.Config;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements ListOnClickListener, LoaderManager.LoaderCallbacks {

    @BindView(R.id.recycler_contact_details)
    RecyclerView recycler_details;
    @BindView(R.id.tv_contact_name)
    TextView contactName;
    @BindView(R.id.detail_call)
    LinearLayout call_button;
    private Cursor mContactData;
    private DetailsListAdapter adapter;
    private String index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        ImageView imageView = (ImageView) findViewById(R.id.item_contact_image);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            SharedElementTransisition(imageView);
        }
        index = getIntent().getStringExtra(Contacts_List.TAG_POSITION);
        contactName.setText(index);
        adapter = new DetailsListAdapter(this, null, this);
        recycler_details.setAdapter(adapter);
        recycler_details.setLayoutManager(new LinearLayoutManager(this));
        getSupportLoaderManager().initLoader(0, null, this);
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


    /**
     *request a number to dial using the call action
     *
     */
    @Override
    public void ondialClick(String num) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + num.replaceAll("-", "")));
        ActivityCompat.requestPermissions(DetailActivity.this,
                new String[]{Manifest.permission.CALL_PHONE},
                1);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }

    /**
     *accepts a website to access
     */
    @Override
    public void onWebsiteClick(String site) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(site));
        startActivity(i);
    }

    /**
     *permission results for using "dangerous" intent
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(DetailActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    /**
     *loader access
     */
    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                DataContract.ContactsEntry.CONTENT_URI,
                Config.buildProjectionArray(),
                DataContract.ContactsEntry.COLUMN_CONTACT_NAME + "=?",
                new String[]{index},
                null);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        mContactData = (Cursor) data;
        if (mContactData.getCount() == 1 || mContactData != null) {
            call_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   ondialClick(mContactData.getString(Config.POSITION_LIST_CONTACT_PHONE_MOBILE));
                }
            });
        }
        adapter.setCursor(mContactData);

    }

    @Override
    public void onContactClicked(ContactViewHolder holder, int position) {

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
