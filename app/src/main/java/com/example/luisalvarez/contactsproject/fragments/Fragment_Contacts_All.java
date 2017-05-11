package com.example.luisalvarez.contactsproject.fragments;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.luisalvarez.contactsproject.R;
import com.example.luisalvarez.contactsproject.data.FetchService;
import com.example.luisalvarez.contactsproject.data.DataContract;
import com.example.luisalvarez.contactsproject.util.Config;
import com.example.luisalvarez.contactsproject.util.ContactListAdapter;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Fragment_Contacts_All extends Fragment implements LoaderManager.LoaderCallbacks {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ContactListAdapter adapter;
    private GcmNetworkManager mGcmNetworkManager;


    @BindView(R.id.view_recyclerview)
    RecyclerView vContactsList;


    public Fragment_Contacts_All() {
        // Required empty public constructor
    }

    public static Fragment_Contacts_All newInstance(String param1, String param2) {
        Fragment_Contacts_All fragment = new Fragment_Contacts_All();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contacts_all, container, false);
        //Bind views to fragment
        ButterKnife.bind(this,rootView);
        Log.d("test", "async");
        Syncr syncr = new Syncr();
        syncr.execute();
        Log.d("test", "fetch task");
        startFetchTaskService();
        adapter = new ContactListAdapter(getActivity(),null);
        vContactsList.setAdapter(adapter);
        getLoaderManager().initLoader(0,null,this);
        return rootView;
    }

    private void startFetchTaskService() {
        PeriodicTask myTask = new PeriodicTask.Builder()
                .setService(FetchService.class)
                .setPeriod(30L)
                .setTag("contacts_service")
                .build();
        Log.d("gcm","started");
        mGcmNetworkManager.getInstance(getActivity()).schedule(myTask);
    }

    public class Syncr extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            //check to make sure network is up


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }


    /**
     * Cursor Loading
     */
    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),
                DataContract.ContactsEntry.CONTENT_URI,
                Config.buildProjectionListArray(),
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        Log.d("test", "cursor set");
        adapter.setCursor((Cursor)data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        adapter.setCursor(null);
    }
}
