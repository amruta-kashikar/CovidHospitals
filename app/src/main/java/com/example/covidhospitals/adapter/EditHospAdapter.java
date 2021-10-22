package com.example.covidhospitals.adapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidhospitals.R;
import com.example.covidhospitals.model.hospitalModel;
import com.example.covidhospitals.model.model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class EditHospAdapter extends RecyclerView.Adapter<EditHospAdapter.myviewholder>{
    ArrayList<hospitalModel> hospmodel;
    private Context contextBack;
    TextView t3;


    public EditHospAdapter(ArrayList<hospitalModel> hospmodel,Context cntx) {
//        ArrayList<hospitalModel> temp= new ArrayList<hospitalModel>();
//        temp.add(hospmodel.get(0));
        this.hospmodel =hospmodel;
        this.contextBack=cntx;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hospital_edit,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull EditHospAdapter.myviewholder holder, int position) {
        holder.t1.setText(hospmodel.get(position).getName());
        holder.t6.setText(hospmodel.get(position).getArea());
        holder.t2.setText(hospmodel.get(position).getPhone());
        holder.t3.setText(hospmodel.get(position).getEmail());
        holder.t4.setText(hospmodel.get(position).getTotal());
        holder.t5.setText(""+hospmodel.get(position).getVacant());
        holder.t7.setText(""+hospmodel.get(position).getO2());
        holder.t8.setText(""+hospmodel.get(position).getNonO2());
        holder.t9.setText(""+hospmodel.get(position).getIcu());
        holder.t10.setText(""+hospmodel.get(position).getVentilator());


        holder.editHosp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  final DialogPlus dialogPlus = DialogPlus.newDialog(contextBack)
                        .setContentHolder(new ViewHolder(R.layout.updatehosp))
                        .create();
                dialogPlus.show();
                View myview = dialogPlus.getHolderView();
                final EditText name = myview.findViewById(R.id.nameHospital);
                final EditText area = myview.findViewById(R.id.areaHospital);
                final EditText phone = myview.findViewById(R.id.phoneHospital);
                final EditText email = myview.findViewById(R.id.emailHospital);
                //final EditText password = myview.findViewById(R.id.pwdHospital);
                final EditText total = myview.findViewById(R.id.totalBeds);
                final EditText vacantB = myview.findViewById(R.id.vacantBeds);
                final EditText o2B = myview.findViewById(R.id.o2Beds);
                final EditText nonO2B = myview.findViewById(R.id.nonO2Beds);
                final EditText icuB = myview.findViewById(R.id.icuBeds);
                final EditText ventilatorB = myview.findViewById(R.id.ventilatorBeds);
                // Log.d("TAGe","email value : " + email.getText().toString());
                Button update = myview.findViewById(R.id.btnUpdate);
                //Log.d("TAG", "onClick() returned: " + hospmodel.get(position).getVacant());
                name.setText(hospmodel.get(position).getName());
                area.setText(hospmodel.get(position).getArea());
                phone.setText(hospmodel.get(position).getPhone());
                email.setText(hospmodel.get(position).getEmail());
                total.setText(hospmodel.get(position).getTotal());
                vacantB.setText(""+hospmodel.get(position).getVacant());

                 vacantB.setText(""+hospmodel.get(position).getVacant());
                o2B.setText(""+hospmodel.get(position).getO2());
                nonO2B.setText(""+hospmodel.get(position).getNonO2());
                icuB.setText(""+hospmodel.get(position).getIcu());
                ventilatorB.setText(""+hospmodel.get(position).getVentilator());
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //int position = 0;
                        int vacant = Integer.parseInt(vacantB.getText().toString());
                        int o2= Integer.parseInt(o2B.getText().toString());
                        int nonO2=Integer.parseInt(nonO2B.getText().toString());
                        int icu= Integer.parseInt(icuB.getText().toString());
                        int ventilator = Integer.parseInt(ventilatorB.getText().toString());


                        Map<String, Object> map = new HashMap<>();
                        map.put("name",name.getText().toString());
                        map.put("area",area.getText().toString());
                        map.put("phone",phone.getText().toString());
                        map.put("email",email.getText().toString());
                        //map.put("password",password.getText().toString());
                        map.put("total",total.getText().toString());
                        map.put("vacant",vacant);
                        map.put("o2",o2);
                        map.put("nonO2",nonO2);
                        map.put("icu",icu);
                        map.put("ventilator",ventilator);
                        FirebaseFirestore.getInstance().collection("hospital").document(hospmodel.get(position).getId()).update(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        dialogPlus.dismiss();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                dialogPlus.dismiss();

                            }
                        });

                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return hospmodel.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder{
        TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10;
        ImageButton editHosp;
        private  int position;
        private String singleId=null;
        public myviewholder(@NonNull @NotNull View itemView) {
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

            editHosp = itemView.findViewById(R.id.editHosp);
        }
        public void setData(model currentObject, int position) {
            this.position = position;
            this.singleId=currentObject.getId();
        }

        public void setListeners(){
            editHosp.setOnClickListener((View.OnClickListener) myviewholder.this);

        }
    }
}
