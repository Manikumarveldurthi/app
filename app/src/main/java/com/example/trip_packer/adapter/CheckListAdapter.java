package com.example.trip_packer.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trip_packer.Database.RoomDB;
import com.example.trip_packer.Models.Items;
import com.example.trip_packer.R;
import com.example.trip_packer.constants.myconstants;

import java.util.List;

public class CheckListAdapter extends RecyclerView.Adapter<ChecklistViewHolder> {

    private Context context;
    private List<Items> itemsList;
    private RoomDB database;
    private String show;
    private OnItemAddedListener onItemAddedListener;
    private ChecklistViewHolder holder;
    private int position;
    public CheckListAdapter()
    {

    }

    public CheckListAdapter(Context context, List<Items> itemsList, RoomDB database, String show) {
        this.context = context;
        this.itemsList = itemsList;
        this.database = database;
        this.show = show;
        this.onItemAddedListener = onItemAddedListener;
        if (itemsList.size() == 0)
            Toast.makeText(context.getApplicationContext(), "NOTHING to show", Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public ChecklistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChecklistViewHolder(LayoutInflater.from(context).inflate(R.layout.check_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChecklistViewHolder holder, @SuppressLint("RecyclerView") int position) {
        this.holder = holder;
        this.position = position;
        holder.checkBox.setText(itemsList.get(position).getItemname());
        holder.checkBox.setChecked(itemsList.get(position).getChecked());

        if (myconstants.FALSE_STRING.equals(show)) {
            holder.btnDelete.setVisibility(View.GONE);
            holder.layout.setBackgroundDrawable(context.getResources().getDrawable((R.drawable.border_1)));
        } else {
            if (itemsList.get(position).getChecked()) {
                holder.layout.setBackgroundColor(Color.parseColor("#8e546f"));
            } else
                holder.layout.setBackgroundDrawable(context.getResources().getDrawable((R.drawable.border_1)));
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean check = holder.checkBox.isChecked();
                database.mainDao().checkuncheck(itemsList.get(position).getID(), check);
                if (myconstants.FALSE_STRING.equals(show)) {
                    itemsList = database.mainDao().getAllselected(true);
                    notifyDataSetChanged();
                } else {
                    itemsList.get(position).setChecked(check);
                    notifyDataSetChanged();
                    Toast toastmessage = null;
                    if (toastmessage != null) {
                        toastmessage.cancel();
                    }
                    if (itemsList.get(position).getChecked()) {
                        toastmessage = Toast.makeText(context, "(" + holder.checkBox.getText() + "packed", Toast.LENGTH_SHORT);
                    } else {
                        toastmessage = Toast.makeText(context, "(" + holder.checkBox.getText() + "un-packed", Toast.LENGTH_SHORT);
                    }
                    toastmessage.show();
                }
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete (" + itemsList.get(position).getItemname() + ")")
                        .setMessage("ARE YOU SURE?")
                        .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                database.mainDao().delete(itemsList.get(position));
                                itemsList.remove(itemsList.get(position));
                                notifyDataSetChanged();
                            }
                        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(context, "canceled", Toast.LENGTH_SHORT).show();
                            }
                        }).setIcon(R.drawable.ic_baseline_delete_forever_24).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    // Interface for item added event
    public interface OnItemAddedListener {
        void onItemAdded(Items item);
    }

    // Getter for OnItemAddedListener
    public OnItemAddedListener getOnItemAddedListener() {
        return onItemAddedListener;
    }
}

class ChecklistViewHolder extends RecyclerView.ViewHolder {
    LinearLayout layout;
    CheckBox checkBox;
    Button btnDelete;

    public ChecklistViewHolder(View itemview) {
        super(itemview);
        layout = itemview.findViewById(R.id.linearlayout);
        checkBox = itemview.findViewById(R.id.checkbox);
        btnDelete = itemview.findViewById(R.id.btnDelete);
    }
}
