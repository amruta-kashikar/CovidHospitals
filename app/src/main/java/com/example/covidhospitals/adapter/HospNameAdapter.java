package com.example.covidhospitals.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidhospitals.DetailsPatient;
import com.example.covidhospitals.R;
import com.example.covidhospitals.model.hospitalModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class HospNameAdapter extends RecyclerView.Adapter<HospNameAdapter.myviewholder>
{
    public static ArrayList<hospitalModel> nameslist;

    Context context;
    public HospNameAdapter(ArrayList<hospitalModel> nameslist,Context context) {
        this.nameslist = nameslist;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public HospNameAdapter.myviewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HospNameAdapter.myviewholder holder, int position) {
        holder.hospName.setText(nameslist.get(position).getName());
        Log.d("idss","hosp id : "+nameslist.get(position).getId());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("click","clicked id: "+nameslist.get(position).getId());
                Intent viewPat = new Intent(context.getApplicationContext(),DetailsPatient.class);
                viewPat.putExtra("id",nameslist.get(position).getId());
                context.startActivity(viewPat);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nameslist.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        CardView parentLayout;
        TextView hospName;
        public myviewholder(View itemView) {
            super(itemView);
            hospName = itemView.findViewById(R.id.hospName);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
