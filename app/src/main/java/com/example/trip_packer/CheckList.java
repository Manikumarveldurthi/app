// CheckList.java

package com.example.trip_packer;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.trip_packer.Data.AppData;
import com.example.trip_packer.Database.RoomDB;
import com.example.trip_packer.Models.Items;
import com.example.trip_packer.adapter.CheckListAdapter;
import com.example.trip_packer.constants.myconstants;

import java.util.ArrayList;
import java.util.List;

public class CheckList extends AppCompatActivity  {

    RecyclerView recyclerView;
    CheckListAdapter checkListAdapter;
    RoomDB database;
    List<Items> itemsList = new ArrayList<>();
    String header, show;
    private static final int REQUEST_CODE_VOICE_RECOGNITION = 123;
    private static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO=1;
    private MyReceiver myReceiver;
    EditText txtAdd;
    Button btnAdd;
    LinearLayout linearLayout;

    @Override
    public boolean onCreatePanelMenu(int featureid,@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_1, menu);

        if (myconstants.MY_SELECTIONS.equals(header)) {
            menu.getItem(0).setVisible(false);
            menu.getItem(2).setVisible(false);
            menu.getItem(3).setVisible(false);
        } else if (myconstants.MY_LIST_CAMEL_CASE.equals(header))
            menu.getItem(1).setVisible(false);
        menu.add(Menu.NONE, R.id.btnVoiceRecognition, Menu.NONE, "Voice Recognition")
                .setIcon(R.drawable.ic_baseline_keyboard_voice_24)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        MenuItem menuItem = menu.findItem(R.id.btnSearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Items> nFinalList = new ArrayList<>();
                for (Items items : itemsList) {
                    if (items.getItemname().toLowerCase().startsWith(newText.toLowerCase())) {
                        nFinalList.add(items);
                    }
                }
                updateRecycler(nFinalList);
                return false;
            }

        });
        MenuItem shareItem = menu.findItem(R.id.btnshare);
        shareItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d("CheckList", "Share button clicked");
                shareChecklist();
                return true;
            }
        });
        /*for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem1 = menu.getItem(i);
            if (menuItem.getActionView() != null) {
                SpannableString spannableString = new SpannableString(menuItem.getTitle());
                spannableString.setSpan(
                        new ForegroundColorSpan(getResources().getColor(R.color.menu_item_text_color)),
                        0,
                        spannableString.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
                menuItem.setTitle(spannableString);
            }
        }*/
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, CheckList.class);
        AppData appData = new AppData(database, this);
        switch (item.getItemId()) {
            case R.id.btnMySelections:
                intent.putExtra(myconstants.HEADER_SMALL, myconstants.MY_SELECTIONS);
                intent.putExtra(myconstants.SHOW_SMALL, myconstants.FALSE_STRING);
                startActivityForResult(intent, 101);
                return true;

            case R.id.btnCustomList:
                intent.putExtra(myconstants.HEADER_SMALL, myconstants.MY_LIST_CAMEL_CASE);
                intent.putExtra(myconstants.SHOW_SMALL, myconstants.TRUE_STRING);
                startActivity(intent);
                return true;

            case R.id.btnDeleteDefault:
                new AlertDialog.Builder(this)
                        .setTitle("Delete default data")
                        .setMessage("Are you sure?\n\n As this will delete the data provided by(trip packer) while installing.")
                        .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                appData.persistDataByCtaegory(header, true);
                                itemsList = database.mainDao().getAll(header);
                                updateRecycler(itemsList);
                            }
                        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).setIcon(R.drawable.ic_baseline_warning_24).show();
                return true;
            case R.id.btnReset:
                new AlertDialog.Builder(this)
                        .setTitle("Reset to default")
                        .setMessage("Are you sure?\n\nAs this will load the default data provided by (trip packer)" +
                                " and will delete the custom data you have added in (" + header + ")")
                        .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                appData.persistDataByCtaegory(header, false);
                                itemsList = database.mainDao().getAll(header);
                                updateRecycler(itemsList);
                            }
                        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).setIcon(R.drawable.ic_baseline_warning_24).show();
                return true;
            case R.id.btnAboutus:
                Intent aboutUsIntent = new Intent(this, AboutUsService.class);
                startService(aboutUsIntent);
                startActivity(new Intent(this, AboutUs.class));
            return true;
            case R.id.btnshare:

                Intent shareIntent = new Intent(this, ShareService.class);
                startService(shareIntent);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CheckList.this, "ShareService started", Toast.LENGTH_SHORT).show();
                        shareChecklist();
                    }
         },1000);
                return true;
            case R.id.btnExit:
                this.finishAffinity();
                Toast.makeText(this, "TRIP_PACKER\nExit completed", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.btnVoiceRecognition:
                startVoiceRecognition();
                startMyService();
            return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void startMyService() {
        // Start the service
        Intent serviceIntent = new Intent(this, MyService.class);
        startService(serviceIntent);
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_VOICE_RECOGNITION && resultCode == RESULT_OK && data != null) {
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            if (results != null && !results.isEmpty()) {
                String spokenText = results.get(0);
                addNewitem(spokenText);
     }
}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        header = intent.getStringExtra(myconstants.HEADER_SMALL);
        show = intent.getStringExtra(myconstants.SHOW_SMALL);
        getSupportActionBar().setTitle(header);

        txtAdd = findViewById(R.id.txtAdd);
        btnAdd = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.recyclerview);
        linearLayout = findViewById(R.id.linearlayout);
        database = RoomDB.getInstance(this);

        if (myconstants.FALSE_STRING.equals(show)) {
            linearLayout.setVisibility(View.GONE);
            itemsList = database.mainDao().getAllselected(true);
        } else {
            itemsList = database.mainDao().getAll(header);
        }
        updateRecycler(itemsList);
        Button btnVoiceRecognition = findViewById(R.id.btnVoiceRecognition);
        btnVoiceRecognition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startVoiceRecognition();
         }
});

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = txtAdd.getText().toString();
                if (itemName != null && !itemName.isEmpty()) {
                    addNewitem(itemName);
                    Toast.makeText(CheckList.this, "Item added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CheckList.this, "Empty can't be added", Toast.LENGTH_SHORT).show();
                }
            }
        });
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.trip_packer.SERVICE_STARTED");
        intentFilter.addAction("com.example.trip_packer.SERVICE_STOPPED");
        registerReceiver(myReceiver,intentFilter);
    }
    protected void onDestroy() {
        // Unregister the BroadcastReceiver when the activity is destroyed
        unregisterReceiver(myReceiver);
        Intent serviceIntent = new Intent(this, MyService.class);
        stopService(serviceIntent);
        super.onDestroy();
}

    private void addNewitem(String itemname) {
        Items item = new Items();
        item.setChecked(true);
        item.setCategory(header);
        item.setItemname(itemname);
        item.setAddedby(myconstants.USER_SMALL);
        database.mainDao().saveItem(item);
        itemsList = database.mainDao().getAll(header);
        updateRecycler(itemsList);
        recyclerView.scrollToPosition(checkListAdapter.getItemCount() - 1);
        txtAdd.setText("");
    }

    private void updateRecycler(List<Items> itemsList) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        checkListAdapter = new CheckListAdapter(CheckList.this, itemsList, database, show);
        recyclerView.setAdapter(checkListAdapter);
    }
    private void shareChecklist() {
        StringBuilder sharedText = new StringBuilder("Checklist Items:\n");

        for (Items item : itemsList) {
            sharedText.append(item.getItemname()).append("\n");
        }

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, sharedText.toString());

        startActivity(Intent.createChooser(shareIntent, "Share Checklist"));
}
    private void startVoiceRecognition() {
     /*   if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    MY_PERMISSIONS_REQUEST_RECORD_AUDIO);
        } else {*/
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now");

        try {
            startActivityForResult(intent, REQUEST_CODE_VOICE_RECOGNITION);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Voice recognition not supported on this device", Toast.LENGTH_SHORT).show();
       }
}

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
