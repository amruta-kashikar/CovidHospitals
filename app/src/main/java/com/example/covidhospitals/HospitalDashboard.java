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

import com.example.covidhospitals.model.hospitalModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HospitalDashboard extends AppCompatActivity {
    EditText vacantBeds;
    TextView name;
    Button bedRequests,updateBtn,logoutBtn;
    Intent requests,mainActivity;
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
        logoutBtn = findViewById(R.id.logoutBtn);
        name = findViewById(R.id.hospitalName);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        hospitalId = mAuth.getCurrentUser().getUid();

        requests = new Intent(this,PatientsList.class);

        mainActivity = new Intent(this, MainActivity.class);


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
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                //finish();
                startActivity(new Intent(HospitalDashboard.this,MainActivity.class));
            }
        });

        db.collection("hospital").document(hospitalId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult().exists()){
                                String userName = task.getResult().getString("name");
                                name.setText(userName);

                            }
                        }
                    }
                });
    }

    private void updateValue() {
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

    private void clearData() {
        vacantBeds.getText().clear();
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