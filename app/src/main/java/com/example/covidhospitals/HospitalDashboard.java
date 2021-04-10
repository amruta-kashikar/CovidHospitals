package com.example.covidhospitals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HospitalDashboard extends AppCompatActivity {
    Button bedRequests;
    Intent requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_dashboard);
        bedRequests = findViewById(R.id.bedRequests);
        requests = new Intent(this,PatientsList.class);
        bedRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(requests);
            }
        });
    }
}