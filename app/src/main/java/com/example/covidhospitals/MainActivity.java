package com.example.covidhospitals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button Signbtn,dashboard;
    Intent signinIntent,dashIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Signbtn = findViewById(R.id.signinbtn);
        dashboard = findViewById(R.id.dashboard);
        signinIntent = new Intent(this,SigninHospital.class);
        dashIntent = new Intent(this,HospitalDashboard.class);

        Signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(signinIntent);
                //finish();
            }
        });
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(dashIntent);
            }
        });
    }
}