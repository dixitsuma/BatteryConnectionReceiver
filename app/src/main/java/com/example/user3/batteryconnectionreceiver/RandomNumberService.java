package com.example.user3.batteryconnectionreceiver;

import android.bluetooth.BluetoothClass;
import android.content.Intent;

import java.security.Provider;
import android.app.Service;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by user3 on 17/5/18.
 */

public class RandomNumberService extends Service {
    private boolean isStart;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("SERVICE","service started...");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {

      Log.i("SERVICE","service destroyed...");
        super.onDestroy();
        isStart=false;
    }
}
