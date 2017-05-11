package com.example.luisalvarez.contactsproject.activites;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.luisalvarez.contactsproject.R;
import com.example.luisalvarez.contactsproject.adapter.ContactListAdapter;
import com.example.luisalvarez.contactsproject.adapter.ContactViewHolder;
import com.example.luisalvarez.contactsproject.adapter.ListOnClickListener;
import com.example.luisalvarez.contactsproject.data.ContactsTask;
import com.example.luisalvarez.contactsproject.data.DataContract;
import com.example.luisalvarez.contactsproject.data.FetchService;
import com.example.luisalvarez.contactsproject.util.Config;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Contacts_List extends AppCompatActivity implements ListOnClickListener,LoaderManager.LoaderCallbacks {

    @BindView(R.id.view_recyclerview)
    RecyclerView vContactsList;
    private ContactListAdapter adapter;
    private Cursor items;

    public static final String TAG_POSITION = "position";
    public static final String TAG_URL = "url";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);
        ButterKnife.bind(this);
        startFetchTaskService();
        adapter = new ContactListAdapter(this, null,this);
        vContactsList.setAdapter(adapter);
        getSupportLoaderManager().initLoader(0, null, this);
    }

    private void startFetchTaskService() {
        PeriodicTask myTask = new PeriodicTask.Builder()
                .setService(FetchService.class)
                .setPeriod(21600L)
                .setTag("contacts_service")
                .build();
        GcmNetworkManager.getInstance(this).schedule(myTask);
    }

    @Override
    public void onContactClicked(ContactViewHolder holder, int position) {
        items.moveToPosition(position);
        Intent intent = new Intent(Contacts_List.this, DetailActivity.class);
        intent.putExtra(TAG_URL,items.getString(Config.POSITION_PROJECTION_CONTACT_IMAGE_SMALL));
        intent.putExtra(TAG_POSITION,position);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(Contacts_List.this,
                        holder.contactPhoto,
                        ViewCompat.getTransitionName(holder.contactPhoto));
            startActivity(intent, options.toBundle());
        }else{
            startActivity(intent);

        }
    }

    /**
     * Cursor Loading
     */
    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                DataContract.ContactsEntry.CONTENT_URI,
                Config.buildProjectionListArray(),
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
       items = (Cursor) data;
        if(items.getCount() == 0 || items == null){
            Syncr syncr = new Syncr();
            syncr.execute();
        }
        adapter.setCursor((Cursor) data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
            adapter.setCursor(null);
    }

    /**
     * Inital loading of items
    */
    public class Syncr extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            //check to make sure network is up
            ContactsTask task = new ContactsTask(getApplication());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }

}
