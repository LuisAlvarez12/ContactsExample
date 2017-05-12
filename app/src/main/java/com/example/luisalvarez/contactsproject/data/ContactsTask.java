package com.example.luisalvarez.contactsproject.data;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;

import com.example.luisalvarez.contactsproject.util.RemoteEndpointUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by luisalvarez on 5/10/17.
 */

public class ContactsTask {

    public ContactsTask(Context c) {
        Log.d("gcm", "yay!!!!");
        //check to make sure network is up
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkUp = cm.getActiveNetworkInfo();
        if (networkUp == null || !networkUp.isConnected()) {
            return;
        }

        ArrayList<ContentProviderOperation> cpo = new ArrayList<>();
        Uri dirUri = DataContract.ContactsEntry.CONTENT_URI;
        //delete all entries
        cpo.add(ContentProviderOperation.newDelete(dirUri).build());

        try {
            JSONArray array = RemoteEndpointUtil.fetchJsonArray();
            if (array == null) {
                throw new JSONException("Invalid parsed item array");
            }

            //array names within object
            final String JSONOBJECT_PHONE = "phone";
            final String JSONOBJECT_ADDRESS = "address";

            for (int i = 0; i < array.length(); i++) {
                ContentValues values = new ContentValues();
                JSONObject object = array.getJSONObject(i);
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_NAME, object.getString(DataContract.ContactsEntry.COLUMN_CONTACT_NAME));
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_COMPANY, object.optString(DataContract.ContactsEntry.COLUMN_CONTACT_COMPANY));
                Object favObject = object.get(DataContract.ContactsEntry.COLUMN_CONTACT_FAVORITE);
                if (favObject instanceof Integer) {
                    if ((int) favObject == 1) {
                        values.put(DataContract.ContactsEntry.COLUMN_CONTACT_FAVORITE, 1);
                    } else {
                        values.put(DataContract.ContactsEntry.COLUMN_CONTACT_FAVORITE, 0);
                    }
                }
                if (favObject instanceof Boolean) {
                    if ((boolean) favObject) {
                        values.put(DataContract.ContactsEntry.COLUMN_CONTACT_FAVORITE, 1);
                    } else {
                        values.put(DataContract.ContactsEntry.COLUMN_CONTACT_FAVORITE, 0);
                    }
                }

                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_IMAGE_SMALL, object.optString(DataContract.ContactsEntry.COLUMN_CONTACT_IMAGE_SMALL));
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_IMAGE_LARGE, object.optString(DataContract.ContactsEntry.COLUMN_CONTACT_IMAGE_LARGE));
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_EMAIL, object.optString(DataContract.ContactsEntry.COLUMN_CONTACT_EMAIL));
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_WEBSITE, object.optString(DataContract.ContactsEntry.COLUMN_CONTACT_WEBSITE));
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_BIRTHDATE, object.optString(DataContract.ContactsEntry.COLUMN_CONTACT_BIRTHDATE));
                JSONObject phoneObject = object.getJSONObject(JSONOBJECT_PHONE);
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_PHONE_WORK, phoneObject.optString(DataContract.ContactsEntry.COLUMN_CONTACT_PHONE_WORK));
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_PHONE_HOME, phoneObject.optString(DataContract.ContactsEntry.COLUMN_CONTACT_PHONE_HOME));
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_PHONE_MOBILE, phoneObject.optString(DataContract.ContactsEntry.COLUMN_CONTACT_PHONE_MOBILE));
                JSONObject addressObject = object.getJSONObject(JSONOBJECT_ADDRESS);
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_STREET, addressObject.optString(DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_STREET));
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_CITY, addressObject.optString(DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_CITY));
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_STATE, addressObject.optString(DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_STATE));
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_COUNTRY, addressObject.optString(DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_COUNTRY));
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_ZIP, addressObject.optString(DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_ZIP));
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_LATITUDE, addressObject.optString(DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_LATITUDE));
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_LONGITUDE, addressObject.optString(DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_LONGITUDE));
                //ContentProviderOperation allows a batch insert for data integrity reasons
                cpo.add(ContentProviderOperation.newInsert(dirUri).withValues(values).build());
            }
            c.getContentResolver().applyBatch(DataContract.uriAUTHORITY, cpo);

        } catch (JSONException | RemoteException | OperationApplicationException e) {

        }
    }

}
