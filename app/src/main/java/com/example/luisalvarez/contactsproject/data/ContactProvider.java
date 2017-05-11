package com.example.luisalvarez.contactsproject.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by luisalvarez on 2/11/17.
 */


public class ContactProvider extends ContentProvider {

    private static final int FAVORITE_LIST = 100;
    private static final int FAVORITE_ID = 101;
    private static final int CONTACT_LIST = 106;
    private static final int CONTACT_ID = 107;
    private static final UriMatcher sUriMatcher = createUriBuilder();
    private DBHelper mOpenHelper;

    public static UriMatcher createUriBuilder() {
        String content = DataContract.uriAUTHORITY;

        // All paths to the UriMatcher have a corresponding code to return
        // when a match is found (the ints above).
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(content, DataContract.sFavoritesQ, FAVORITE_LIST);
        matcher.addURI(content, DataContract.sFavoritesQ + "/#", FAVORITE_ID);
        matcher.addURI(content, DataContract.sContactsQ, CONTACT_LIST);
        matcher.addURI(content, DataContract.sContactsQ + "/#", CONTACT_ID);


//        matcher.addURI(content, DataContract.PATH_MOVIE, MOVIE);
//        matcher.addURI(content, DataContract.PATH_MOVIE + "/#", MOVIE_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Cursor retCursor;
        long _id;
        switch (sUriMatcher.match(uri)) {
            case CONTACT_LIST:
                retCursor = db.query(
                        DataContract.ContactsEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case CONTACT_ID:
                _id = ContentUris.parseId(uri);
                retCursor = db.query(
                        DataContract.ContactsEntry.TABLE_NAME,
                        projection,
                        DataContract.ContactsEntry._ID + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            case FAVORITE_LIST:
                retCursor = db.query(
                        DataContract.FavoritesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case FAVORITE_ID:
                _id = ContentUris.parseId(uri);
                retCursor = db.query(
                        DataContract.FavoritesEntry.TABLE_NAME,
                        projection,
                        DataContract.FavoritesEntry._ID + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case CONTACT_LIST:
                return DataContract.ContactsEntry.CONTENT_TYPE;
            case CONTACT_ID:
                return DataContract.ContactsEntry.CONTENT_ITEM_TYPE;
            case FAVORITE_LIST:
                return DataContract.FavoritesEntry.CONTENT_TYPE;
            case FAVORITE_ID:
                return DataContract.FavoritesEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long _id;
        Uri returnUri;
        switch (sUriMatcher.match(uri)) {
            case CONTACT_LIST:
                _id = db.insert(DataContract.ContactsEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = DataContract.ContactsEntry.buildProfileUri(_id);
                    getContext().getContentResolver().notifyChange(uri, null);
                    return returnUri;
                } else {
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
            case CONTACT_ID:
                _id = db.insert(DataContract.ContactsEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = DataContract.ContactsEntry.buildProfileUri(_id);
                    getContext().getContentResolver().notifyChange(uri, null);
                    return returnUri;
                } else {
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
            case FAVORITE_LIST:
                _id = db.insert(DataContract.FavoritesEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = DataContract.FavoritesEntry.buildMovieUri(_id);
                    getContext().getContentResolver().notifyChange(uri, null);
                    return returnUri;
                } else {
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
            case FAVORITE_ID:
                _id = db.insert(DataContract.FavoritesEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = DataContract.FavoritesEntry.buildMovieUri(_id);
                    getContext().getContentResolver().notifyChange(uri, null);
                    return returnUri;
                } else {
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rows = -1;
        switch (sUriMatcher.match(uri)) {
            case CONTACT_LIST:
                rows = db.delete(DataContract.ContactsEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case CONTACT_ID:
                rows = db.delete(DataContract.ContactsEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case FAVORITE_ID:
                rows = db.delete(DataContract.FavoritesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case FAVORITE_LIST:
                rows = db.delete(DataContract.FavoritesEntry.TABLE_NAME, selection, selectionArgs);
                break;
        }
        // Because null could delete all rows:
//        if(selection == null || rows != 0){
//            getContext().getContentResolver().notifyChange(uri, null);
//        }
        return rows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rows = -1;
        switch (sUriMatcher.match(uri)) {
            case CONTACT_LIST:
                rows = db.update(DataContract.ContactsEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case CONTACT_ID:
                rows = db.update(DataContract.ContactsEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case FAVORITE_LIST:
                rows = db.update(DataContract.FavoritesEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case FAVORITE_ID:
                rows = db.update(DataContract.FavoritesEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
        }
        // Because null could delete all rows:
//        if(selection == null || rows != 0){
//            getContext().getContentResolver().notifyChange(uri, null);
//        }
        return rows;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int returnCount;
        switch (match) {
            case CONTACT_LIST:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(DataContract.ContactsEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case FAVORITE_LIST:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(DataContract.FavoritesEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
