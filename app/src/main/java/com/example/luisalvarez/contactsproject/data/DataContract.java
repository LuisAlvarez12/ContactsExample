package com.example.luisalvarez.contactsproject.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by luisalvarez on 2/11/17.
 */

public class DataContract {

    public static final String uriAUTHORITY =
            "com.example.luisalvarez.contactsproject";

    public static final Uri BASE_CONTENT_URI =
            Uri.parse("content://" +
                    uriAUTHORITY);

    public static final String sFavoritesQ = "/favorites";
    public static final String sContactsQ = "/contacts";

    public static final class ContactsEntry implements BaseColumns {
        // Content URI represents the base location for the table
        public static final String strContent = BASE_CONTENT_URI + sContactsQ;
        public static final Uri CONTENT_URI =
                Uri.parse(strContent);

        // These are special type prefixes that specify if a URI returns a list or a specific item
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_URI + "/" + sContactsQ;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_URI + "/" + sContactsQ;

        // Define the table schema
        public static final String TABLE_NAME = "contactTable";
        public static final String COLUMN_CONTACT_NAME = "name";
        public static final String COLUMN_CONTACT_COMPANY = "company";
        public static final String COLUMN_CONTACT_FAVORITE = "favorite";
        public static final String COLUMN_CONTACT_IMAGE_SMALL = "smallImageURL";
        public static final String COLUMN_CONTACT_IMAGE_LARGE = "largeImageURL";
        public static final String COLUMN_CONTACT_EMAIL = "email";
        public static final String COLUMN_CONTACT_WEBSITE = "website";
        public static final String COLUMN_CONTACT_BIRTHDATE = "birthdate";
        public static final String COLUMN_CONTACT_PHONE_WORK = "work";
        public static final String COLUMN_CONTACT_PHONE_HOME = "home";
        public static final String COLUMN_CONTACT_PHONE_MOBILE = "mobile";
        public static final String COLUMN_CONTACT_ADDRESS_STREET = "street";
        public static final String COLUMN_CONTACT_ADDRESS_CITY = "city";
        public static final String COLUMN_CONTACT_ADDRESS_STATE = "state";
        public static final String COLUMN_CONTACT_ADDRESS_COUNTRY = "country";
        public static final String COLUMN_CONTACT_ADDRESS_ZIP = "zip";
        public static final String COLUMN_CONTACT_ADDRESS_LATITUDE = "latitude";
        public static final String COLUMN_CONTACT_ADDRESS_LONGITUDE = "longitude";


        public static String[] buildListProjectionArray() {
            String[] projection = new String[3];
            return projection;
        }


        // Define a function to build a URI to find a specific movie by it's identifier
        public static Uri buildProfileUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class FavoritesEntry implements BaseColumns {


        // Content URI represents the base location for the table
        public static final String strContent = BASE_CONTENT_URI + sFavoritesQ;
        public static final Uri CONTENT_URI =
                Uri.parse(strContent);

        // These are special type prefixes that specify if a URI returns a list or a specific item
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_URI + "/" + sFavoritesQ;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_URI + "/" + sFavoritesQ;

        // Define the table schema
        public static final String TABLE_NAME = "favoritesTable";
        public static final String COLUMN_WORKOUT_ID = "workoutID";


        public static final String[] projection = {
        };

        // Define a function to build a URI to find a specific movie by it's identifier
        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }


}
