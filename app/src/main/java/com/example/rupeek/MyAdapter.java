package com.example.rupeek;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    List<Model> modelList;
    LayoutInflater layoutInflater;
    private Context context;

    public MyAdapter(Context context , List<Model> modelList){
        this.modelList = modelList;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Model model = modelList.get(position);

        holder.name.setText(model.getName());
        holder.address.setText(model.getAddress());

        Glide.with(context)
                .load(model.getImage())
                .centerCrop()
//                .placeholder(R.drawable.loading_spinner)
                .into(holder.image);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MapsActivity.class);
                intent.putExtra("latitude",model.getLatitude().substring(0,model.getLatitude().length()-3));
                intent.putExtra("longitude",model.getLongitude().substring(0,model.getLatitude().length()-3));

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

    CardView cardView;

    TextView name, id, address;
    ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }
}
