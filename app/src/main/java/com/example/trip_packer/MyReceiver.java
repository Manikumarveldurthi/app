package com.example.trip_packer;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case "com.example.trip_packer.SERVICE_STARTED":
                    Toast.makeText(context, "Service started", Toast.LENGTH_SHORT).show();
                    break;

                case "com.example.trip_packer.SERVICE_STOPPED":
                    Toast.makeText(context, "Service stopped", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
}}