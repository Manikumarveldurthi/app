package com.example.trip_packer;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.trip_packer.Data.AppData;
import com.example.trip_packer.Database.RoomDB;
import com.example.trip_packer.Models.Items;
import com.example.trip_packer.adapter.CheckListAdapter;
import com.example.trip_packer.constants.myconstants;

import java.util.ArrayList;
import java.util.List;

public class CheckList extends AppCompatActivity {

    RecyclerView recyclerView;
    CheckListAdapter checkListAdapter;
    RoomDB database;
    List<Items>itemsList=new ArrayList<>();
    String header,show;

    EditText txtAdd;
    Button btnAdd;
    LinearLayout linearLayout;

    @Override

    public boolean onCreatePanelMenu(int featureid, @NonNull Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_1, menu);

        if (myconstants.MY_SELECTIONS.equals(header)) {
            menu.getItem(0).setVisible(false);
            menu.getItem(2).setVisible(false);
            menu.getItem(3).setVisible(false);
        } else if (myconstants.MY_LIST_CAMEL_CASE.equals(header))
            menu.getItem(1).setVisible(false);

        MenuItem menuItem = menu.findItem(R.id.btnSearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Items> nFinallist=new ArrayList<>();
                for(Items items:itemsList)
                {
                    if(items.getItemname().toLowerCase().startsWith(newText.toLowerCase()))
                    {
                        nFinallist.add(items);
                    }
                }
                updateRecycler(nFinallist);
                return false;
            }

        });
        return true;
    }

    @Override
    public  boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        Intent intent=new Intent(this,CheckList.class);
        AppData appData=new AppData(database,this);
        switch (item.getItemId())
        {
            case R.id.btnMySelections:
                intent.putExtra(myconstants.HEADER_SMALL,myconstants.MY_SELECTIONS);
                intent.putExtra(myconstants.SHOW_SMALL,myconstants.FALSE_STRING);
                startActivityForResult(intent,101);
                return  true;

            case R.id.btnCustomList:
                intent.putExtra(myconstants.HEADER_SMALL,myconstants.MY_LIST_CAMEL_CASE);
                intent.putExtra(myconstants.SHOW_SMALL,myconstants.TRUE_STRING);
                startActivity(intent);
                return true;

            case R.id.btnDeleteDefault:
                new AlertDialog.Builder(this).
                        setTitle("Delete default data").
                        setMessage("Are you sure?\n\n As this will delete the data provided by(trip packer) while installing.").
                        setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                appData.persistDataByCtaegory(header,true);
                                itemsList=database.mainDao().getAll(header);
                                updateRecycler(itemsList);
                            }
                        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).setIcon(R.drawable.ic_baseline_warning_24).show();
                return true;
            case R.id.btnReset:
                new AlertDialog.Builder(this).
                        setTitle("Reset to default").
                        setMessage("Are you sure?\n\nAs this will load the default data provided by (trip packer)" +
                                " and will delete the custom data you have added in ("+header+")").
                        setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                appData.persistDataByCtaegory(header,false);
                                itemsList=database.mainDao().getAll(header);
                                updateRecycler(itemsList);
                            }
                        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).setIcon(R.drawable.ic_baseline_warning_24).show();
                return  true;
            case R.id.btnAboutus:
                intent =new Intent(this,AboutUs.class);
                startActivity(intent);
                return true;

            case R.id.btnExit:
                this.finishAffinity();
                Toast.makeText(this, "TRIP_PACKER\nExit completed", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

   @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101)
        {
            itemsList=database.mainDao().getAll(header);
            updateRecycler(itemsList);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent=getIntent();
        header =intent.getStringExtra(myconstants.HEADER_SMALL);
        show=intent.getStringExtra(myconstants.SHOW_SMALL);
        getSupportActionBar().setTitle(header);

        txtAdd=findViewById(R.id.txtAdd);
        btnAdd=findViewById(R.id.btnAdd);
        recyclerView=findViewById(R.id.recyclerview);
        linearLayout=findViewById(R.id.linearlayout);
        database=RoomDB.getInstance(this);
        if(myconstants.FALSE_STRING.equals(show))
        {
            linearLayout.setVisibility(View.GONE);
            itemsList=database.mainDao().getAllselected(true);
        }
        else
        {
            itemsList=database.mainDao().getAll(header);
        }
        updateRecycler(itemsList);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName=txtAdd.getText().toString();
                if(itemName != null && !itemName.isEmpty())
                {
                   addnewitem(itemName);
                    Toast.makeText(CheckList.this,"Item added",Toast.LENGTH_SHORT).show();
                }

                else
                {
                    Toast.makeText(CheckList.this,"Empty cant be added",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }





    private void addnewitem(String itemname)
    {
        Items item=new Items();
        item.setChecked(false);
        item.setCategory(header);
        item.setItemname(itemname);
        item.setAddedby(myconstants.USER_SMALL);
        database.mainDao().saveItem(item);
        itemsList=database.mainDao().getAll(header);
        updateRecycler((itemsList));
        recyclerView.scrollToPosition(checkListAdapter.getItemCount()-1);
        txtAdd.setText("");

    }


    private void updateRecycler(List<Items> itemsList)
    {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        checkListAdapter =new CheckListAdapter(CheckList.this,itemsList,database,show);
        recyclerView.setAdapter(checkListAdapter);


    }
    @Override
    public boolean onSupportNavigateUp() {


        onBackPressed();
        return true;
    }

}