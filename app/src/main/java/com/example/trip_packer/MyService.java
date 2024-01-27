package com.example.trip_packer;


import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class MyService extends IntentService {

    public MyService() {
        super("MyService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("MyService", "Service started");

        // Perform your background task here

        // Notify that the service has started
        sendBroadcast(new Intent("com.example.trip_packer.SERVICE_STARTED"));

        // Simulate some work
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d("MyService", "Service stopped");

        // Notify that the service has stopped
        sendBroadcast(new Intent("com.example.trip_packer.SERVICE_STOPPED"));
}
}