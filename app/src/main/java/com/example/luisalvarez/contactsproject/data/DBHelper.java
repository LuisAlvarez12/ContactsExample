package com.example.luisalvarez.contactsproject.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by luisalvarez on 2/11/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contacts.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_FAVORITES_TABLE = "CREATE TABLE " + DataContract.FavoritesEntry.TABLE_NAME + " (" +
                DataContract.FavoritesEntry.COLUMN_WORKOUT_ID + " TEXT NOT NULL, " +
                ");";

        final String CREATE_CONTACTS_TABLE = "CREATE TABLE " + DataContract.ContactsEntry.TABLE_NAME + " (" +
                DataContract.ContactsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DataContract.ContactsEntry.COLUMN_CONTACT_NAME + " TEXT NOT NULL, " +
                DataContract.ContactsEntry.COLUMN_CONTACT_COMPANY + " TEXT NOT NULL, " +
                //0 = false, 1 = true
                DataContract.ContactsEntry.COLUMN_CONTACT_FAVORITE + " BIT NULL DEFAULT 0, " +
                DataContract.ContactsEntry.COLUMN_CONTACT_IMAGE_SMALL + " TEXT NOT NULL, " +
                DataContract.ContactsEntry.COLUMN_CONTACT_IMAGE_LARGE + " TEXT NOT NULL, " +
                DataContract.ContactsEntry.COLUMN_CONTACT_EMAIL + " TEXT NOT NULL, " +
                DataContract.ContactsEntry.COLUMN_CONTACT_WEBSITE + " TEXT NOT NULL, " +
                DataContract.ContactsEntry.COLUMN_CONTACT_BIRTHDATE + " TEXT NOT NULL, " +
                DataContract.ContactsEntry.COLUMN_CONTACT_PHONE_WORK + " TEXT NOT NULL, " +
                DataContract.ContactsEntry.COLUMN_CONTACT_PHONE_HOME + " TEXT NOT NULL, " +
                DataContract.ContactsEntry.COLUMN_CONTACT_PHONE_MOBILE + " TEXT NOT NULL, " +
                DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_STREET + " TEXT NOT NULL, " +
                DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_CITY + " TEXT NOT NULL, " +
                DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_STATE + " TEXT NOT NULL, " +
                DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_COUNTRY + " TEXT NOT NULL, " +
                DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_ZIP + " TEXT NOT NULL, " +
                DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_LATITUDE + " DOUBLE, " +
                DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_LONGITUDE + " DOUBLE" +
                ");";

        db.execSQL(CREATE_FAVORITES_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DataContract.ContactsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataContract.FavoritesEntry.TABLE_NAME);
        onCreate(db);
    }
}
