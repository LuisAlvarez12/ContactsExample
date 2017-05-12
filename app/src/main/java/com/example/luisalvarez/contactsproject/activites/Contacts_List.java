package com.example.luisalvarez.contactsproject.activites;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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

import static android.R.attr.id;

public class Contacts_List extends AppCompatActivity implements ListOnClickListener, LoaderManager.LoaderCallbacks {


    @BindView(R.id.view_recyclerview)
    RecyclerView vContactsList;

    private ContactListAdapter adapter;
    private Cursor items;
    private Menu mMenu;

    public static final String TAG_POSITION = "position";
    public static final String TAG_URL = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contacts");
        startFetchTaskService();
        adapter = new ContactListAdapter(this, null, this,R.layout.item_list_med_contact);
        vContactsList.setAdapter(adapter);
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.d("menu","menu prepared");

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.mMenu = menu;
        getMenuInflater().inflate(R.menu.menu_grid,menu);
        MenuItem item = menu.findItem(R.id.menu_med);
        item.setVisible(false);
        item = menu.findItem(R.id.menu_small);
        item.setVisible(false);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        MenuItem i;
        switch (item.getItemId()) {
            case R.id.menu_grid:
                item.setVisible(false);
                i = mMenu.findItem(R.id.menu_med);
                i.setVisible(true);
                return true;
            case R.id.menu_med:
                item.setVisible(false);
                i = mMenu.findItem(R.id.menu_small);
                i.setVisible(true);
                adapter = new ContactListAdapter(this,null,this,R.layout.item_list_small_contact);
                vContactsList.setAdapter(adapter);
                getSupportLoaderManager().initLoader(0,null,this);
                return true;
            case R.id.menu_small:
                item.setVisible(false);
                i = mMenu.findItem(R.id.menu_grid);
                i.setVisible(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Start service to update the contacts list via
     * the URL every 6 hours
     */
    private void startFetchTaskService() {
        PeriodicTask myTask = new PeriodicTask.Builder()
                .setService(FetchService.class)
                .setPeriod(21600L)
                .setTag("contacts_service")
                .build();
        GcmNetworkManager.getInstance(this).schedule(myTask);
    }

    /**
     * Intent to detailActivity
     */
    @Override
    public void onContactClicked(ContactViewHolder holder, int position) {
        items.moveToPosition(position);
        Intent intent = new Intent(Contacts_List.this, DetailActivity.class);
        intent.putExtra(TAG_URL, items.getString(Config.POSITION_PROJECTION_CONTACT_IMAGE_LARGE));
        intent.putExtra(TAG_POSITION, items.getInt(Config.POSITION_PROJECTION_CONTACT_ID));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(Contacts_List.this,
                            holder.contactPhoto,
                            ViewCompat.getTransitionName(holder.contactPhoto));
            startActivity(intent, options.toBundle());
        } else {
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
                DataContract.ContactsEntry.COLUMN_CONTACT_NAME);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        items = (Cursor) data;
        if (items.getCount() == 0 || items == null) {
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
