package com.example.luisalvarez.contactsproject.data;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.RemoteException;

import com.example.luisalvarez.contactsproject.util.RemoteEndpointUtil;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by luisalvarez on 5/10/17.
 */

public class ContactsFetchTask extends GcmTaskService {
    @Override
    public int onRunTask(TaskParams taskParams) {

        //check to make sure network is up
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkUp = cm.getActiveNetworkInfo();
        if (networkUp == null || !networkUp.isConnected()) {
            return 0;
        }

        ArrayList<ContentProviderOperation> cpo = new ArrayList<ContentProviderOperation>();
        Uri dirUri = DataContract.ContactsEntry.CONTENT_URI;
        //delete all entries
        cpo.add(ContentProviderOperation.newDelete(dirUri).build());

        try {
            JSONArray array = RemoteEndpointUtil.fetchJsonArray();
            if (array == null) {
                throw new JSONException("Invalid parsed item array" );
            }

            //array names within object
            final String JSONOBJECT_PHONE = "phone";
            final String JSONOBJECT_ADDRESS = "address";

            for (int i = 0; i < array.length(); i++) {
                ContentValues values = new ContentValues();
                JSONObject object = array.getJSONObject(i);
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_NAME, object.getString(DataContract.ContactsEntry.COLUMN_CONTACT_NAME));
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_COMPANY, object.getString(DataContract.ContactsEntry.COLUMN_CONTACT_COMPANY));
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_FAVORITE, object.getBoolean(DataContract.ContactsEntry.COLUMN_CONTACT_FAVORITE));
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_IMAGE_SMALL, object.getString(DataContract.ContactsEntry.COLUMN_CONTACT_IMAGE_SMALL));
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_IMAGE_LARGE, object.getString(DataContract.ContactsEntry.COLUMN_CONTACT_IMAGE_LARGE));
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_EMAIL, object.getString(DataContract.ContactsEntry.COLUMN_CONTACT_EMAIL));
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_WEBSITE, object.getString(DataContract.ContactsEntry.COLUMN_CONTACT_WEBSITE));
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_BIRTHDATE, object.getString(DataContract.ContactsEntry.COLUMN_CONTACT_BIRTHDATE));
                JSONArray phoneArray = object.getJSONArray(JSONOBJECT_PHONE);
                JSONObject phoneObject = phoneArray.getJSONObject(0);
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_PHONE_WORK, phoneObject.getString(DataContract.ContactsEntry.COLUMN_CONTACT_PHONE_WORK));
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_PHONE_HOME, phoneObject.getString(DataContract.ContactsEntry.COLUMN_CONTACT_PHONE_HOME));
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_PHONE_MOBILE, phoneObject.getString(DataContract.ContactsEntry.COLUMN_CONTACT_PHONE_MOBILE));
                JSONArray addressArray = object.getJSONArray(JSONOBJECT_ADDRESS);
                JSONObject addressObject = addressArray.getJSONObject(0);
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_STREET, addressObject.getString(DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_STREET));
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_CITY, addressObject.getString(DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_CITY));
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_STATE, addressObject.getString(DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_STATE));
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_COUNTRY, addressObject.getString(DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_COUNTRY));
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_ZIP, addressObject.getString(DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_ZIP));
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_LATITUDE, addressObject.getString(DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_LATITUDE));
                values.put(DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_LONGITUDE, addressObject.getString(DataContract.ContactsEntry.COLUMN_CONTACT_ADDRESS_LONGITUDE));
                //ContentProviderOperation allows a batch insert for data integrity reasons
                cpo.add(ContentProviderOperation.newInsert(dirUri).withValues(values).build());
            }
            //itemsProvider
            getContentResolver().applyBatch(DataContract.uriAUTHORITY, cpo);

        } catch (JSONException | RemoteException | OperationApplicationException e) {

        }

        return GcmNetworkManager.RESULT_SUCCESS;
    }
}
