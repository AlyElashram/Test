package com.alyhatem.craver;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
private ArrayList<CardItem> mCardlist;

    public RecyclerAdapter(ArrayList<CardItem> Cardlist){
    mCardlist=Cardlist;
    }
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
        RecyclerViewHolder rvh=new RecyclerViewHolder(v);
                return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
    CardItem current=mCardlist.get(position);
    holder.imageView.setImageResource(current.getImageresource());
    holder.text1.setText(current.getText1());
    holder.text2.setText(current.getText2());

    }

    @Override
    public int getItemCount() {
        return mCardlist.size();
    }

    public static class RecyclerViewHolder     extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView text1,text2;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.ImageView);
            text1=itemView.findViewById(R.id.CardText);
            text2=itemView.findViewById(R.id.CardText1);


        }
    }
}
