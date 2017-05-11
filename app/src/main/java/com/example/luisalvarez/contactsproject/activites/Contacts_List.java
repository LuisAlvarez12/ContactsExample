package com.example.luisalvarez.contactsproject.activites;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.luisalvarez.contactsproject.R;
import com.example.luisalvarez.contactsproject.fragments.FragmentContactsAll;

public class Contacts_List extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentHolder, FragmentContactsAll.newInstance())
                .commit();
    }

    public void switchContent(int id, Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(id, fragment, fragment.toString());
        ft.addToBackStack(null);
        ft.commit();
    }
}
