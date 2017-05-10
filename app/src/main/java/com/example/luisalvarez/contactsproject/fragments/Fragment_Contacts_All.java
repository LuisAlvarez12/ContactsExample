package com.example.luisalvarez.contactsproject.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.luisalvarez.contactsproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Fragment_Contacts_All extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

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




        return rootView;
    }

}
