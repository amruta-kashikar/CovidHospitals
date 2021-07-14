package com.example.covidhospitals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidhospitals.adapter.HospitalListAdapter;
import com.example.covidhospitals.model.hospitalModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HospitalDashboard extends AppCompatActivity {
    EditText vacantBeds,o2Beds,nonO2Beds,icuBeds,ventilatorBeds;
    TextView name,vacbed;
    Button bedRequests, logoutBtn,manageBedsBtn, addPatientBtn; //updateBtn, updateO2Btn, updateNonO2Btn, updateIcuBtn, updateVentilatorBtn;
    Intent requests, mainActivity;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String hospitalId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_dashboard);
        bedRequests = findViewById(R.id.bedRequests);
        vacantBeds = findViewById(R.id.vacantBeds);
        o2Beds = findViewById(R.id.o2Beds);
        nonO2Beds = findViewById(R.id.nonO2Beds);
        icuBeds = findViewById(R.id.icuBeds);
        ventilatorBeds = findViewById(R.id.ventilatorBeds);
//        updateBtn = findViewById(R.id.updateBtn);
//        updateO2Btn = findViewById(R.id.updateO2Btn);
//        updateNonO2Btn = findViewById(R.id.updateNonO2Btn);
//        updateIcuBtn = findViewById(R.id.updateIcuBtn);
//        updateVentilatorBtn = findViewById(R.id.updateVentilatorBtn);
        manageBedsBtn = findViewById(R.id.manageBedsBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        addPatientBtn = findViewById(R.id.addPatientBtn);
        name = findViewById(R.id.hospitalName);
        //vacbed = findViewById(R.id.vacbed);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        hospitalId = mAuth.getCurrentUser().getUid();
        //vacantBeds.setText(String.valueOf(hospitalModel.getVacant()));

        requests = new Intent(this, PatientsList.class);

        mainActivity = new Intent(this, MainActivity.class);
        //updateBed = new Intent(this, UpdateBedData.class);


        bedRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(requests);
            }
        });
        manageBedsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent manage = new Intent(HospitalDashboard.this,UpdateBedData.class);
                startActivity(manage);
            }
        });
//        updateBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateValue();
//            }
//        });
//
        addPatientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(HospitalDashboard.this,AddPatient.class);
                startActivity(add);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                //finish();
                startActivity(new Intent(HospitalDashboard.this, MainActivity.class));
            }
        });

        db.collection("hospital").document(hospitalId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                String userName = task.getResult().getString("name");
//                                String vacBed = String.valueOf(task.getResult().getLong("vacant"));
//                                String o2Bed = String.valueOf(task.getResult().getLong("o2"));
//                                String nonO2Bed = String.valueOf(task.getResult().getLong("nonO2"));
//                                String icuBed = String.valueOf(task.getResult().getLong("icu"));
//                                String ventilatorBed = String.valueOf(task.getResult().getLong("ventilator"));
                                name.setText(userName);
//                                vacbed.setText(vacBed);
//                                o2Beds.setText(o2Bed);
//                                nonO2Beds.setText(nonO2Bed);
//                                icuBeds.setText(icuBed);
//                                ventilatorBeds.setText(ventilatorBed);
                            }
                        }
                    }
                });
    }

/*    private void updateValue() {
        int vacant = 0;
        if(vacantBeds.getText().toString().isEmpty()){
            vacantBeds.setError("Enter vacant beds");
            vacantBeds.requestFocus();
            return;
        }else{
            vacant= Integer.parseInt(vacantBeds.getText().toString());
            hospitalModel m = new hospitalModel(
                    vacant
            );
            db.collection("hospital").document(hospitalId)
                    .update(
                            "vacant",m.getVacant()
                    )
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(HospitalDashboard.this, "Vacant bed count updated successfully", Toast.LENGTH_LONG).show();
                        }
                    });
            clearData();
        }

 */
        //String vacant = vacantBeds.getText().toString().trim();
/*        if(!hasValidationErrors(vacant))
        {
            hospitalModel m = new hospitalModel(
                    vacant
            );
            db.collection("hospital").document(hospitalId)
                    .update(
                            "vacant",m.getVacant()
                    )
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(HospitalDashboard.this, "Bed count updated successfully", Toast.LENGTH_LONG).show();
                        }
                    });
        }
 */
    }

//    private void clearData() {
//        vacantBeds.getText().clear();
//    }

//    private boolean hasValidationErrors(String vacant) {
//        if(vacant.isEmpty()){
//            vacantBeds.setError("Enter vacant beds");
//            vacantBeds.requestFocus();
//            return true;
//        }
//        return false;
//    }

