package com.example.covidhospitals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.covidhospitals.model.hospitalModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class HospitalDashboard extends AppCompatActivity {
    EditText vacantBeds;
    Button bedRequests,updateBtn;
    Intent requests;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String hospitalId;
    //private hospitalModel bed;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_dashboard);
        bedRequests = findViewById(R.id.bedRequests);
        vacantBeds = findViewById(R.id.vacantBeds);
        updateBtn = findViewById(R.id.updateBtn);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        hospitalId = mAuth.getCurrentUser().getUid();
        requests = new Intent(this,PatientsList.class);
        bedRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(requests);
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateValue();
            }
        });
    }

    private void updateValue() {
        String vacant = vacantBeds.getText().toString().trim();
        if(!hasValidationErrors(vacant))
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
    }

    private boolean hasValidationErrors(String vacant) {
        if(vacant.isEmpty()){
            vacantBeds.setError("Enter vacant beds");
            vacantBeds.requestFocus();
            return true;
        }
        return false;
    }
}