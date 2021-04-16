package com.example.covid.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid.R;
import com.example.covidhospitals.model.model.hospitalModel;

import java.util.ArrayList;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder>
{
    public Object filteredList;
    ArrayList<hospitalModel> datalist;

    public myadapter(ArrayList<hospitalModel> datalist) {
        this.datalist = datalist;
    }
    public void updateData(int position){
        Bundle bundle = new Bundle();
        bundle.putString("t5",datalist.get(position).getVacant());
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hospital_details,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.t1.setText(datalist.get(position).getName());
        holder.t2.setText(datalist.get(position).getPhone());
        holder.t3.setText(datalist.get(position).getEmail());
        holder.t4.setText(datalist.get(position).getTotal());
        holder.t5.setText(datalist.get(position).getVacant());
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public void filteredList(ArrayList<hospitalModel> filterList) {
        datalist = filterList;
        notifyDataSetChanged();
    }

    class myviewholder extends RecyclerView.ViewHolder {

        TextView t1,t2,t3,t4,t5;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.t1);
            t2 = itemView.findViewById(R.id.t2);
            t3 = itemView.findViewById(R.id.t3);
            t4 = itemView.findViewById(R.id.t4);
            t5 = itemView.findViewById(R.id.t5);
        }
    }
}
