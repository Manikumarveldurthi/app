package com.example.trip_packer.adapter;

        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.trip_packer.CheckList;
        import com.example.trip_packer.R;
        import com.example.trip_packer.constants.myconstants;

        import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


     List<String> titles;
     List<Integer> images;
    LayoutInflater inflater;
    Activity activity;

    public MyAdapter(Context context, List<String> titles, List<Integer> images,Activity activity) {

        this.activity=activity;
        this.titles = titles;
        this.images = images;
        this.inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.main_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(titles.get(position));
        holder.img.setImageResource(images.get(position));
        holder.linearLayout.setAlpha(0.8F);
        holder.linearLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
              //  Toast.makeText(activity,"clicked on card.",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(view.getContext(), CheckList.class);
                intent.putExtra(myconstants.HEADER_SMALL,titles.get(position));
                if(myconstants.MY_SELECTIONS.equals(titles.get(position)))
                {
                    intent.putExtra(myconstants.SHOW_SMALL,myconstants.FALSE_STRING);
                }
                else
                {
                    intent.putExtra(myconstants.SHOW_SMALL,myconstants.TRUE_STRING);
                }
                view.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView img;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            img=itemView.findViewById(R.id.img);
            linearLayout =itemView.findViewById(R.id.linearlayout);

        }
    }
}
