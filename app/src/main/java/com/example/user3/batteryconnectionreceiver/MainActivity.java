package com.example.user3.batteryconnectionreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView battery_level,battery_status,broadcast_msg;
    ProgressBar progressBar;
    ImageView flight_mode;
    Button send_broadcast;
    EditText entered_msg;
    ImageView iNext;

    AeroplaneModeBroadcastReceiver aeroplaneModeBroadcastReceiver;
    BatteryBroacastReceiver batteryBroacastReceiver;
    CustomBroadcast customBroadcast;
    Runnable sendMessage;

    private final Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        battery_level=findViewById(R.id.tv_battery_level);
        battery_status=findViewById(R.id.tv_battery_status);
        progressBar=findViewById(R.id.progressBar);
        flight_mode=findViewById(R.id.iv_flight);
        send_broadcast=findViewById(R.id.btn_send_broadcast);
        broadcast_msg=findViewById(R.id.tv_broadcast_msg);
        entered_msg=findViewById(R.id.et_entered_message);
        iNext=findViewById(R.id.iv_next);

        aeroplaneModeBroadcastReceiver=new AeroplaneModeBroadcastReceiver();
        batteryBroacastReceiver=new BatteryBroacastReceiver();
        customBroadcast=new CustomBroadcast();
        send_broadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent=new Intent();
                intent.setAction("action.CUSTOM_BROADCAST");
                String user_msg=entered_msg.getText().toString();
                intent.putExtra("message",user_msg);
                sendBroadcast(intent);
                entered_msg.setText("");
            }
        });


        iNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        handler.postDelayed(sendMessage,1000);

        registerReceiver(batteryBroacastReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        registerReceiver(aeroplaneModeBroadcastReceiver,new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED));
        registerReceiver(customBroadcast,new IntentFilter("action.CUSTOM_BROADCAST"));
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(batteryBroacastReceiver);
        unregisterReceiver(aeroplaneModeBroadcastReceiver);
        unregisterReceiver(customBroadcast);
        super.onStop();
    }

    private class BatteryBroacastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            battery_level.setText(level+" %");
            progressBar.setProgress(level);


            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;


            if(status==BatteryManager.BATTERY_STATUS_FULL)
            {
                battery_status.setText("Charged");
            }
           else {
                battery_status.setText("Charging");
            }
            if(!isCharging)
            {
                battery_status.setText("");
            }
            Log.d("BATTERY STATUS ","received broadcast");

        }
    }

    private class AeroplaneModeBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isAeroplaneModeOn=intent.getBooleanExtra("state",false);
            if(isAeroplaneModeOn)
            {
                flight_mode.setImageResource(R.drawable.ic_flight_mode);
            }
            else {
                flight_mode.setImageResource(0);
            }

        }
    }

    private class CustomBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, final Intent intent) {


                  Thread display_msg=  new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            String msg=intent.getExtras().getString("message");
                            broadcast_msg.setText(msg.toUpperCase());
                           
                        }
                    });

            handler.postDelayed(display_msg,5000);
        }


    }


    public void startService(View view)
    {
        Intent intent=new Intent(this,RandomNumberService.class);
        startService(intent);
    }
    public void stopService(View view)
    {
        Intent intent=new Intent(this,RandomNumberService.class);
        stopService(intent);
    }

}
