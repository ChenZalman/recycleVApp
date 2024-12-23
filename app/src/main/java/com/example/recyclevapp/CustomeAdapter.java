package com.example.recyclevapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


import android.graphics.Color;

public class CustomeAdapter extends RecyclerView.Adapter<CustomeAdapter.MyViewHolder> {

    private ArrayList<DataModel> dataSet;
    private RecyclerViewListener listener;
    public CustomeAdapter(ArrayList<DataModel> dataSet, RecyclerViewListener listener) {
        this.dataSet = dataSet;
        this.listener = listener;
    }

    public interface RecyclerViewListener{
        void onClick(View  view, int position);
        boolean onLongClick(View view, int position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewVersion;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textView);
            textViewVersion = itemView.findViewById(R.id.textView2);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    public void filterList(ArrayList<DataModel> filterList) {
        // below line is to add our filtered
        // list in our course array list.
        this.dataSet = filterList;
        // below line is to notify our adapter
        // as change in recycler view data.
//        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardrow, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomeAdapter.MyViewHolder holder, int position) {

        holder.textViewName.setText(dataSet.get(position).getName());
        holder.textViewVersion.setText(dataSet.get(position).getVersion());
        holder.imageView.setImageResource(dataSet.get(position).getImage());

        holder.itemView.setOnClickListener(v -> listener.onClick(v, position));
        holder.itemView.setOnLongClickListener(v -> {
            listener.onLongClick(v, position);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}