package com.example.covid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class pwdUpdated extends AppCompatActivity {
    MaterialButton LoginPagebtn;
    Intent login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_updated);
        LoginPagebtn = findViewById(R.id.btnLoginPage);
        login = new Intent(this,LoginHospital.class);
        LoginPagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(login);
                finish();
            }
        });
    }
}