package com.example.luisalvarez.contactsproject.fragments;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
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
import com.example.luisalvarez.contactsproject.data.ContactsTask;
import com.example.luisalvarez.contactsproject.data.DataContract;
import com.example.luisalvarez.contactsproject.data.FetchService;
import com.example.luisalvarez.contactsproject.util.Config;
import com.example.luisalvarez.contactsproject.adapter.ContactListAdapter;
import com.example.luisalvarez.contactsproject.adapter.ContactViewHolder;
import com.example.luisalvarez.contactsproject.util.DetailsTransition;
import com.example.luisalvarez.contactsproject.adapter.ListOnClickListener;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentContactsAll extends Fragment implements LoaderManager.LoaderCallbacks,ListOnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.view_recyclerview)
    RecyclerView vContactsList;
    private String mParam1;
    private String mParam2;
    private ContactListAdapter adapter;


    public FragmentContactsAll() {
        // Required empty public constructor
    }

    public static FragmentContactsAll newInstance() {
        FragmentContactsAll fragment = new FragmentContactsAll();
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
        ButterKnife.bind(this, rootView);
        startFetchTaskService();
        adapter = new ContactListAdapter(getActivity(), null,this);
        vContactsList.setAdapter(adapter);
              getLoaderManager().initLoader(0, null, this);
        return rootView;
    }

    private void startFetchTaskService() {
        PeriodicTask myTask = new PeriodicTask.Builder()
                .setService(FetchService.class)
                .setPeriod(21600L)
                .setTag("contacts_service")
                .build();
        GcmNetworkManager.getInstance(getActivity()).schedule(myTask);
    }

    @Override
    public void onContactClicked(ContactViewHolder holder, int position) {
        FragmentDetails frag2 = FragmentDetails.newInstance();

        // Note that we need the API version check here because the actual transition classes (e.g. Fade)
        // are not in the support library and are only available in API 21+. The methods we are calling on the Fragment
        // ARE available in the support library (though they don't do anything on API < 21)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            frag2.setSharedElementEnterTransition(new DetailsTransition());
            frag2.setEnterTransition(new Fade());
            setExitTransition(new Fade());
            frag2.setSharedElementReturnTransition(new DetailsTransition());
        }

        String transitionString = getResources().getString(R.string.transition_image);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addSharedElement(holder.contactPhoto,transitionString )
                .replace(R.id.fragmentHolder, frag2)
                .addToBackStack(null)
                .commit();
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
        Cursor c = (Cursor) data;
        if(c.getCount() == 0 || c == null){
            Syncr syncr = new Syncr();
            syncr.execute();
        }
        adapter.setCursor((Cursor) data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        adapter.setCursor(null);
    }

    public class Syncr extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            //check to make sure network is up
            ContactsTask task = new ContactsTask(getActivity());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }

}
