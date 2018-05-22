package com.example.user3.batteryconnectionreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.PendingIntent;
import android.app.AlarmManager;

import java.util.Calendar;

public class SecondActivity extends AppCompatActivity {

    Button enter;
    EditText etName;
    TextView etMessage;
    WelcomeMessageBroadcast welcomeMessageBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

         etName=findViewById(R.id.et_name);
         etMessage=findViewById(R.id.tv_msg);
         enter=findViewById(R.id.btn_enter);
        welcomeMessageBroadcast=new WelcomeMessageBroadcast();


        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                Intent intent=new Intent(SecondActivity.this,GreetingIntentService.class);
                intent.putExtra("name",etName.getText().toString());
                startService(intent);   */

                /*String name=etName.getText().toString();
                Log.i("NAME= ",name);
                Calendar calendar=Calendar.getInstance();
                AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(SecondActivity.this, GreetingIntentService.class);
                intent.putExtra("name",name);
                PendingIntent pintent = PendingIntent.getService(SecondActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarm.cancel(pintent);
                Log.i("Strating",String.valueOf(calendar.getTimeInMillis()));
                alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()+5000, pintent);
                Log.i("ending",String.valueOf(calendar.getTimeInMillis())); */
                int notificationId=(int)(Math.random()*1000);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(),"first");
                mBuilder.setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle("Welcome message" )
                        .setContentText("Welcome...")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(getApplicationContext());
                mNotificationManager.notify(notificationId,mBuilder.build());

            }
        });
    }




    @Override
    protected void onStart() {
        registerReceiver(welcomeMessageBroadcast,new IntentFilter("com.example.usr3.GREETING_MSG"));
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(welcomeMessageBroadcast);
        super.onStop();
    }

    private class WelcomeMessageBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String welcomeMessage = intent.getStringExtra("welcomeMessage");
            etMessage.setText(welcomeMessage.toUpperCase());

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(),"first");
            mBuilder.setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle("Welcome message" )
                    .setContentText(welcomeMessage)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(getApplicationContext());
            mNotificationManager.notify(1,mBuilder.build());

            etName.setText("");

        }
    }


}
