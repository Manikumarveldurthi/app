package com.example.trip_packer;


import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

public class AboutUsService extends IntentService {

    public AboutUsService() {
        super("AboutUsService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Display a toast message when the service is started
        Toast.makeText(this, "About Us Service Started", Toast.LENGTH_SHORT).show();
}
}