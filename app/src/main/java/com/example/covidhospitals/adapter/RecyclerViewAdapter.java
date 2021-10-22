package com.example.covidhospitals.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.covidhospitals.PatientsList;
import com.example.covidhospitals.R;
import com.example.covidhospitals.model.model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.myviewholder>
{
    FirebaseFirestore db =FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Button approveBtn,rejectBtn;
    //PatientsList patientsLi
    PatientsList p;
    ArrayList<model> datalist;

    Context context;

    public RecyclerViewAdapter(ArrayList<model> datalist,Context context,PatientsList p) {
        this.datalist = datalist;
        this.p=p;
        this.context = context;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_details,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.t1.setText(datalist.get(position).getName());
        holder.t2.setText(datalist.get(position).getAge());
        holder.t3.setText(datalist.get(position).getGender());
        holder.t4.setText(datalist.get(position).getSymptoms());
        holder.t5.setText(datalist.get(position).getTime());
        holder.t6.setText(datalist.get(position).getPhone());
        holder.t7.setText(datalist.get(position).getGenId());
        holder.t8.setText(datalist.get(position).getBedType());

        ArrayList<String> paths = datalist.get(position).getImages();
        if(paths!=null){
            if(paths.size()==1) {
                Glide.with(context)
                        .load(paths.get(0))
                        .into(holder.i1);
            }
            else if(paths.size()==2){
                Glide.with(context)
                        .load(paths.get(0))
                        .into(holder.i1);
                Glide.with(context)
                        .load(paths.get(1))
                        .into(holder.i2);
            }
        }
        model current = datalist.get(position);
        holder.setData(current,position);
        holder.setListeners();

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myviewholder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView t1,t2,t3,t4,t5,t6,t7,t8;
        ImageView i1,i2;
        private int position;

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
            i1 = itemView.findViewById(R.id.i1);
            i2 = itemView.findViewById(R.id.i2);
            approveBtn = itemView.findViewById(R.id.approveBtn);
            rejectBtn = itemView.findViewById(R.id.rejectBtn);
        }
        public void setData(model currentObject, int position) {
            
            this.position = position;
            
        }


        public void setListeners() {
                approveBtn.setOnClickListener(myviewholder.this);
                
            }
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.approveBtn:
                   
                    model dardi=datalist.get(position);
                    updateBeds(dardi);
                    removeItem(position);
                    break;
//                case R.id.rejectBtn:
//                    deleteItem(position);
//                    break;
            }
        }

    }

    public void removeItem(int position){
        p.removeData(position);
        datalist.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, datalist.size());

    }
    private void updateBeds(model dardi) {
        p.updateBedData(dardi);
    };

}
