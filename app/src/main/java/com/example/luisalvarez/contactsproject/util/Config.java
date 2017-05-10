package com.example.luisalvarez.contactsproject.util;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class Config {
    public static final URL CONTACTS_URL;

    private static String TAG = Config.class.toString();

    //get url to be parsed to json, called in RemoteEndpointUtil
    static {
        URL url = null;
        try {
            //json info
            url = new URL("https://s3.amazonaws.com/technical-challenge/Contacts_v2.json" );
        } catch (MalformedURLException ignored) {
            Log.e(TAG, "Please check your internet connection.");
        }
        CONTACTS_URL = url;
    }

    //get url to be parsed to json, called in RemoteEndpointUtil

}
