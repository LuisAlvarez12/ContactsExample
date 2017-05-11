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
                DataContract.FavoritesEntry.COLUMN_WORKOUT_ID + " TEXT NOT NULL " +
                ");";

        final String CREATE_CONTACTS_TABLE = "CREATE TABLE " + DataContract.ContactsEntry.TABLE_NAME + " (" +
                DataContract.ContactsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DataContract.ContactsEntry.COLUMN_CONTACT_NAME + " TEXT , " +
                DataContract.ContactsEntry.COLUMN_CONTACT_COMPANY + " TEXT , " +
                //0 = false, 1 = true
                DataContract.ContactsEntry.COLUMN_CONTACT_FAVORITE + " INTEGER, " +
                DataContract.ContactsEntry.COLUMN_CONTACT_IMAGE_SMALL + " TEXT , " +
                DataContract.ContactsEntry.COLUMN_CONTACT_IMAGE_LARGE + " TEXT , " +
                DataContract.ContactsEntry.COLUMN_CONTACT_EMAIL + " TEXT , " +
                DataContract.ContactsEntry.COLUMN_CONTACT_WEBSITE + " TEXT , " +
                DataContract.ContactsEntry.COLUMN_CONTACT_BIRTHDATE + " TEXT , " +
                DataContract.ContactsEntry.COLUMN_CONTACT_PHONE_WORK + " TEXT , " +
                DataContract.ContactsEntry.COLUMN_CONTACT_PHONE_HOME + " TEXT , " +
                DataContract.ContactsEntry.COLUMN_CONTACT_PHONE_MOBILE + " TEXT , " +
                DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_STREET + " TEXT , " +
                DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_CITY + " TEXT , " +
                DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_STATE + " TEXT , " +
                DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_COUNTRY + " TEXT , " +
                DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_ZIP + " TEXT, " +
                DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_LATITUDE + " DOUBLE, " +
                DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_LONGITUDE + " DOUBLE" +
                ");";

        // db.execSQL(CREATE_FAVORITES_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DataContract.ContactsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataContract.FavoritesEntry.TABLE_NAME);
        onCreate(db);
    }
}
