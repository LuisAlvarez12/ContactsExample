package com.example.luisalvarez.contactsproject.data;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

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
