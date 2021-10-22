package com.example.covidhospitals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidhospitals.model.hospitalModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateBedData extends AppCompatActivity {
    EditText vacantBeds,o2Beds,nonO2Beds,icuBeds,ventilatorBeds;
    TextView vacbed,o2bed,nono2bed,icubed,ventbed;
    Button updateBtn, updateO2Btn, updateNonO2Btn, updateIcuBtn, updateVentilatorBtn;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String hospitalId;
    hospitalModel hospModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_bed_data);

        vacantBeds = findViewById(R.id.vacantBeds);
        o2Beds = findViewById(R.id.o2Beds);
        nonO2Beds = findViewById(R.id.nonO2Beds);
        icuBeds = findViewById(R.id.icuBeds);
        ventilatorBeds = findViewById(R.id.ventilatorBeds);
        updateBtn = findViewById(R.id.updateBtn);
        vacbed = findViewById(R.id.vacbed);
        o2bed = findViewById(R.id.o2bed);
        nono2bed = findViewById(R.id.nono2bed);
        icubed = findViewById(R.id.icubed);
        ventbed = findViewById(R.id.ventbed);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        hospitalId = mAuth.getCurrentUser().getUid();

        db.collection("hospital").document(hospitalId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult().exists()){
                                String vacBeds = String.valueOf(task.getResult().getLong("vacant"));
                                String o2Bed = String.valueOf(task.getResult().getLong("o2"));
                                String nonO2Bed = String.valueOf(task.getResult().getLong("nonO2"));
                                String icuBed = String.valueOf(task.getResult().getLong("icu"));
                                String ventilatorBed = String.valueOf(task.getResult().getLong("ventilator"));
                                vacantBeds.setText(vacBeds);
                                vacbed.setText(vacBeds);
                                o2bed.setText(o2Bed);
                                o2Beds.setText(o2Bed);
                                nono2bed.setText(nonO2Bed);
                                nonO2Beds.setText(nonO2Bed);
                                icubed.setText(icuBed);
                                icuBeds.setText(icuBed);
                                ventbed.setText(ventilatorBed);
                                ventilatorBeds.setText(ventilatorBed);
                            }
                        }
                    }
                });


        // for updating vacant beds
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateValue();
            }
        });
    }


    private void updateValue() {
        int vacant = 0;
        int o2=0;
        int nonO2 = 0;
        int icu = 0;
        int ventilator = 0;
        if (!hasValidationErrors(vacant,o2,nonO2,icu,ventilator)) {
            vacant = Integer.parseInt(vacantBeds.getText().toString());
            o2 = Integer.parseInt(o2Beds.getText().toString());
            nonO2 = Integer.parseInt(nonO2Beds.getText().toString());
            icu = Integer.parseInt(icuBeds.getText().toString());
            ventilator = Integer.parseInt(ventilatorBeds.getText().toString());
            hospitalModel m = new hospitalModel(
                    vacant, o2, nonO2, icu, ventilator
            );
            db.collection("hospital").document(hospitalId)
                    .update(
                            "vacant", m.getVacant(),
                            "o2",m.getO2(),
                            "nonO2",m.getNonO2(),
                            "icu",m.getIcu(),
                            "ventilator",m.getVentilator()
                    )
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateBedData.this, "Bed count updated successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }


    private boolean hasValidationErrors(int vacant,int o2,int nonO2,int icu,int ventilator) {
        if (vacantBeds.getText().toString().isEmpty()) {
            vacantBeds.setError("Enter vacant beds");
            vacantBeds.requestFocus();
            return true;
        }
        if(o2Beds.getText().toString().isEmpty()){
            o2Beds.setError("Enter O2 beds");
            o2Beds.requestFocus();
            return true;
        }
        if(nonO2Beds.getText().toString().isEmpty()){
            nonO2Beds.setError("Enter Non-O2 beds");
            nonO2Beds.requestFocus();
            return true;
        }
        if(icuBeds.getText().toString().isEmpty()){
            icuBeds.setError("Enter ICU beds");
            icuBeds.requestFocus();
            return true;
        }
        if(ventilatorBeds.getText().toString().isEmpty()){
            ventilatorBeds.setError("Enter Ventilator beds");
            ventilatorBeds.requestFocus();
            return true;
        }
        return false;
    }

}

