package com.example.trip_packer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Carousel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.trip_packer.Data.AppData;
import com.example.trip_packer.Database.RoomDB;
import com.example.trip_packer.adapter.MyAdapter;
import com.example.trip_packer.constants.myconstants;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<String> titles;
    List<Integer> images;
    Adapter adapter;
    RoomDB database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        addAddTitles();
        addAllImages();
        persistAppData();
        database =RoomDB.getInstance(this);
        System.out.println(".................................>"+database.mainDao().getAllselected(false).get(0).getItemname());

        recyclerView = findViewById(R.id.recyclerview);
        MyAdapter myAdapter = new MyAdapter(this, titles, images, MainActivity.this);

        recyclerView.setAdapter(myAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(myAdapter);

    }

    private void addAddTitles() {
        titles = new ArrayList<>();
        titles.add(myconstants.BASIC_NEEDS_CAMEL_CASE);
        titles.add(myconstants.CLOTHING_CAMEL_CASE);
        titles.add(myconstants.PERSONAL_CARE_CAMEL_CASE);
        titles.add(myconstants.BABY_NEEDS_CAMEL_CASE);
        titles.add(myconstants.HEALTH_CAMEL_CASE);
        titles.add(myconstants.TECHNOLOGY_CAMEL_CASE);
        titles.add(myconstants.FOOD_CAMEL_CASE);
        titles.add(myconstants.BEACH_SUPPLIES_CAMEL_CASE);
        titles.add(myconstants.CAR_SUPPLIES_CAMEL_CASE);
        titles.add(myconstants.NEEDS_CAMEL_CASE);
        titles.add(myconstants.MY_LIST_CAMEL_CASE);
        titles.add(myconstants.MY_SELECTIONS_CAMEL_CASE);

    }

    private static final int TIME_INTERVAL = 2000;

    private long mBackPressed;

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
super.onBackPressed();
return;
        }
        else
        {
            Toast.makeText(this,"Tap back button in order to exit.",Toast.LENGTH_SHORT).show();
        }
        mBackPressed=System.currentTimeMillis();
    }

   private void persistAppData()
   {
       SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
       SharedPreferences.Editor editor= prefs.edit();
       database=RoomDB.getInstance(this);
       AppData appData=new AppData(database);
       int last=prefs.getInt(AppData.LAST_VERSION,0);
       if(!prefs.getBoolean(myconstants.FIRST_TIME_CAMEL_CASE ,false)){

           appData.persistAllData();
           editor.putBoolean(myconstants.FIRST_TIME_CAMEL_CASE,true);
           editor.commit();
       }
       else if(last<AppData.NEW_VERSION)
       {
          database.mainDao().deleteAllSystemItems(myconstants.SYSTEM_SMALL);
          appData.persistAllData();
          editor.putInt(AppData.LAST_VERSION,AppData.NEW_VERSION);
          editor.commit();
       }

   }

    private void addAllImages()
    {
        images =new ArrayList<>();
        images.add(R.drawable.pi1);
        images.add(R.drawable.pi2);
        images.add(R.drawable.pi3);
        images.add(R.drawable.pi4);
        images.add(R.drawable.pi5);
        images.add(R.drawable.pi6);
        images.add(R.drawable.pi7);
        images.add(R.drawable.pi8);
        images.add(R.drawable.pi9);
        images.add(R.drawable.pi10);
        images.add(R.drawable.pi11);
        images.add(R.drawable.pi12);


    }
}



