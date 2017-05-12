package com.example.luisalvarez.contactsproject.util;

import android.util.Log;

import com.example.luisalvarez.contactsproject.data.DataContract;

import java.net.MalformedURLException;
import java.net.URL;

public class Config {
    public static final URL CONTACTS_URL;

    public static final int POSITION_LIST_CONTACT_NAME = 0;
    public static final int POSITION_LIST_CONTACT_COMPANY = 1;
    public static final int POSITION_LIST_CONTACT_FAVORITE = 2;
    public static final int POSITION_LIST_CONTACT_IMAGE_SMALL = 3;
    public static final int POSITION_LIST_CONTACT_IMAGE_LARGE = 4;
    public static final int POSITION_LIST_CONTACT_EMAIL = 5;
    public static final int POSITION_LIST_CONTACT_WEBSITE = 6;
    public static final int POSITION_LIST_CONTACT_BIRTHDATE = 7;
    public static final int POSITION_LIST_CONTACT_PHONE_WORK = 8;
    public static final int POSITION_LIST_CONTACT_PHONE_HOME = 9;
    public static final int POSITION_LIST_CONTACT_PHONE_MOBILE = 10;
    public static final int POSITION_LIST_CONTACT_ADDRESS_STREET = 11;
    public static final int POSITION_LIST_CONTACT_ADDRESS_CITY = 12;
    public static final int POSITION_LIST_CONTACT_ADDRESS_STATE = 13;
    public static final int POSITION_LIST_CONTACT_ADDRESS_COUNTRY = 14;
    public static final int POSITION_LIST_CONTACT_ADDRESS_ZIP = 15;
    public static final int POSITION_LIST_CONTACT_ADDRESS_LATITUDE = 16;
    public static final int POSITION_LIST_CONTACT_ADDRESS_LONGITUDE = 17;

    public static final int POSITION_PROJECTION_CONTACT_NAME = 0;
    public static final int POSITION_PROJECTION_CONTACT_PHONE = 1;
    public static final int POSITION_PROJECTION_CONTACT_IMAGE_SMALL = 2;
    public static final int POSITION_PROJECTION_CONTACT_IMAGE_LARGE = 3;
    public static final int POSITION_PROJECTION_CONTACT_ID = 4;



    private static String TAG = Config.class.toString();

    //get url to be parsed to json, called in RemoteEndpointUtil
    static {
        URL url = null;
        try {
            //json info
            url = new URL("https://s3.amazonaws.com/technical-challenge/Contacts_v2.json");
        } catch (MalformedURLException ignored) {
            Log.e(TAG, "Please check your internet connection.");
        }
        CONTACTS_URL = url;
    }

    /**
     * @return full projection of a contact entry
     */
    public static String[] buildProjectionArray() {
        String[] projection = new String[18];
        projection[0] = DataContract.ContactsEntry.COLUMN_CONTACT_NAME;
        projection[1] = DataContract.ContactsEntry.COLUMN_CONTACT_COMPANY;
        projection[2] = DataContract.ContactsEntry.COLUMN_CONTACT_FAVORITE;
        projection[3] = DataContract.ContactsEntry.COLUMN_CONTACT_IMAGE_SMALL;
        projection[4] = DataContract.ContactsEntry.COLUMN_CONTACT_IMAGE_LARGE;
        projection[5] = DataContract.ContactsEntry.COLUMN_CONTACT_EMAIL;
        projection[6] = DataContract.ContactsEntry.COLUMN_CONTACT_WEBSITE;
        projection[7] = DataContract.ContactsEntry.COLUMN_CONTACT_BIRTHDATE;
        projection[8] = DataContract.ContactsEntry.COLUMN_CONTACT_PHONE_WORK;
        projection[9] = DataContract.ContactsEntry.COLUMN_CONTACT_PHONE_HOME;
        projection[10] = DataContract.ContactsEntry.COLUMN_CONTACT_PHONE_MOBILE;
        projection[11] = DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_STREET;
        projection[12] = DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_CITY;
        projection[13] = DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_STATE;
        projection[14] = DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_COUNTRY;
        projection[15] = DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_ZIP;
        projection[16] = DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_LATITUDE;
        projection[17] = DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_LONGITUDE;
        return projection;
    }

    ;


    public static String[] buildProjectionListArray() {
        String[] projection = new String[5];
        projection[0] = DataContract.ContactsEntry.COLUMN_CONTACT_NAME;
        projection[1] = DataContract.ContactsEntry.COLUMN_CONTACT_PHONE_MOBILE;
        projection[2] = DataContract.ContactsEntry.COLUMN_CONTACT_IMAGE_SMALL;
        projection[3] = DataContract.ContactsEntry.COLUMN_CONTACT_IMAGE_LARGE;
        projection[4] = DataContract.ContactsEntry._ID;
        return projection;
    }

    ;


}
