package com.example.covidhospitals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button Signbtn,Loginbtn,dashboard;
    Intent signinIntent,loginIntent,dashIntent;
    //FirebaseAuth mAuth;
    //String hospitalId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Signbtn = findViewById(R.id.signinbtn);
        Loginbtn = findViewById(R.id.loginbtn);
        dashboard = findViewById(R.id.dashboard);
      //  mAuth = FirebaseAuth.getInstance();
       // hospitalId = mAuth.getCurrentUser().getUid();
        signinIntent = new Intent(this,SigninHospital.class);
        loginIntent = new Intent(this,LoginHospital.class);
        dashIntent = new Intent(this,HospitalDashboard.class);

        Signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(signinIntent);
                //finish();
            }
        });
        Loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(loginIntent);
            }
        });
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
   /*            if(hospitalId!=null)
                {
                    startActivity(dashIntent);
                    //Toast.makeText(MainActivity.this, "Please create a account or login to access dashboard ", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please create a account or login to access dashboard ", Toast.LENGTH_LONG).show();
                    //startActivity(dashIntent);
                }


    */
                startActivity(dashIntent);
            }
        });
    }
}