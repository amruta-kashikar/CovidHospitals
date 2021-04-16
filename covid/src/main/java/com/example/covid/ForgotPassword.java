package com.example.covid;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ForgotPassword extends AppCompatActivity {
    MaterialButton Nextbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        final EditText inputEmail = findViewById(R.id.inputEmail);
        Nextbtn = findViewById(R.id.btnNext);

        final ProgressBar progressBar = findViewById(R.id.progressBar);

        Nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputEmail.getText().toString().trim().isEmpty()){
                    Toast.makeText(ForgotPassword.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                Nextbtn.setVisibility(View.INVISIBLE);


                Intent otppage = new Intent(getApplicationContext(),Otppage.class);
                otppage.putExtra("mobile",inputEmail.getText().toString());
                startActivity(otppage);
            }
        });

    }
}