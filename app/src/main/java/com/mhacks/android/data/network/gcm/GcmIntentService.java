package com.mhacks.android.data.network.gcm;

/**
 * Created by Riyu on 2/13/16.
 */

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.mhacks.android.ui.MainActivity;
import com.mhacks.android.data.network.gcm.GcmBroadcastReceiver;

public class GcmIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;

    private Handler handler;
    String mes;
    public GcmIntentService() {
        super("GcmMessageHandler");
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        handler = new Handler();
    }



    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle extras = intent.getExtras();

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);

        //mes = extras.getString("title");
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(com.google.android.gms.gcm.R.drawable.common_plus_signin_btn_icon_dark)
                        .setContentTitle("title")
                        .setContentText("text");

        Log.i("GCMInfo",  "IN INTENT");
        //Log.i("GCMtitle",  mes);

        int mNotificationId = 001;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());

        GcmBroadcastReceiver.completeWakefulIntent(intent);

    }
}