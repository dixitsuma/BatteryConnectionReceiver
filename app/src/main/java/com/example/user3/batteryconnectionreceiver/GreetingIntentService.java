package com.example.user3.batteryconnectionreceiver;

/**
 * Created by user3 on 18/5/18.
 */
import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

public class GreetingIntentService extends IntentService {
    private int count=1;
    public GreetingIntentService() {
        super("GreetingIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String name= intent.getStringExtra("name");

        Intent sending_msg=new Intent();
        sending_msg.setAction("com.example.usr3.GREETING_MSG");
        sending_msg.putExtra("welcomeMessage","Welcome from intent Service "+name);
        Log.i("SERVICE MSG","Welcome from intent Service "+name);

        sendBroadcast(sending_msg);

    }
}
