package com.example.luisalvarez.contactsproject.data;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;

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

public class FetchService extends GcmTaskService {
    @Override
    public int onRunTask(TaskParams taskParams) {
        ContactsTask contactsTask = new ContactsTask(this);
        return GcmNetworkManager.RESULT_SUCCESS;
    }
}
