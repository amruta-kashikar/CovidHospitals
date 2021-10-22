package com.example.covidhospitals.adapter;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidhospitals.DeleteHospital;
import com.example.covidhospitals.PatientsList;
import com.example.covidhospitals.R;
import com.example.covidhospitals.model.hospitalModel;
import com.example.covidhospitals.model.model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class HospitalListAdapter extends RecyclerView.Adapter<HospitalListAdapter.myviewholder>
{
    
    FirebaseFirestore db =FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    
    DeleteHospital dh;
    ArrayList<hospitalModel> detaillist;

    public HospitalListAdapter(ArrayList<hospitalModel> detaillist, DeleteHospital delHsp) {
        this.detaillist = detaillist;
        this.dh=delHsp;
    }
    public void updateData(int position){
        Bundle bundle = new Bundle();
        bundle.putInt("t5",detaillist.get(position).getVacant());
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hospital_list,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.t1.setText(detaillist.get(position).getName());
        holder.t2.setText(detaillist.get(position).getPhone());
        holder.t3.setText(detaillist.get(position).getEmail());
        holder.t4.setText(detaillist.get(position).getTotal());
        holder.singleId=detaillist.get(position).getId();
        //Log.d("TAGr", "onBindViewHolder() returned: " + holder.singleId);
        holder.t5.setText(String.valueOf(detaillist.get(position).getVacant()));
        holder.t6.setText(detaillist.get(position).getArea());
        holder.t7.setText(String.valueOf(detaillist.get(position).getO2()));
        holder.t8.setText(String.valueOf(detaillist.get(position).getNonO2()));
        holder.t9.setText(String.valueOf(detaillist.get(position).getIcu()));
        holder.t10.setText(String.valueOf(detaillist.get(position).getVentilator()));


        holder.setListeners();
    }

    @Override
    public int getItemCount() {
        return detaillist.size();
    }

 

    class myviewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10;
        Button deleteBtn;
        private int position;
        private String singleId=null;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.t1);
            t2 = itemView.findViewById(R.id.t2);
            t3 = itemView.findViewById(R.id.t3);
            t4 = itemView.findViewById(R.id.t4);
            t5 = itemView.findViewById(R.id.t5);
            t6 = itemView.findViewById(R.id.t6);
            t7 = itemView.findViewById(R.id.t7);
            t8 = itemView.findViewById(R.id.t8);
            t9 = itemView.findViewById(R.id.t9);
            t10 = itemView.findViewById(R.id.t10);

            deleteBtn = itemView.findViewById(R.id.deleteBtn);

        }
        public void setData(model currentObject, int position) {

            this.position = position;
            this.singleId=currentObject.getId();
        }

        public void setListeners(){
            deleteBtn.setOnClickListener(myviewholder.this);

        }

        @Override
        public void onClick(View v) {
            switch(v.getId())
            {
                case R.id.deleteBtn:
                    delete(position,singleId);

                    break;
            }
        }
    }

    private void delete(int position,String idOfHosp) {
        dh.deleteHospital(idOfHosp);
        detaillist.remove(position);
        notifyDataSetChanged();
    }
}

