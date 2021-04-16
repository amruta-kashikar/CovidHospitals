package com.example.covid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.jetbrains.annotations.NotNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SigninHospital extends AppCompatActivity {
    EditText nameHospital,phoneHospital,pwdHospital,emailHospital;
    Intent linklogin,patientdash;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    private Button btnSignIn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_hospital);
        nameHospital = findViewById(R.id.nameHospital);
        phoneHospital = findViewById(R.id.phoneHospital);
        pwdHospital = findViewById(R.id.pwdHospital);
        emailHospital = findViewById(R.id.emailHospital);
        btnSignIn = findViewById(R.id.btnSignIn);
        linklogin = new Intent(this,LoginHospital.class);
        patientdash = new Intent(this,PatientDashboard.class);
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
                //finish();
            }
        });
    }

    public void goto_login(View v)
    {
        TextView Linklogin = (TextView) findViewById(R.id.loginlink);
        startActivity(linklogin);
        finish();

    }
    private void registerUser()
    {
        String name = nameHospital.getText().toString().trim();
        String phone = phoneHospital.getText().toString().trim();
        String email = emailHospital.getText().toString().trim();
        String pwd = pwdHospital.getText().toString().trim();

        if(name.isEmpty()){
            nameHospital.setError("Enter name");
            nameHospital.requestFocus();
            return;
        }
        if(phone.isEmpty()){
            phoneHospital.setError("Enter phone");
            phoneHospital.requestFocus();
            return;
        }
        if((phone.length()<11) || (phone.length()>11)){
            phoneHospital.setError("Minimum phone number length should be 11 characters");
            phoneHospital.requestFocus();
            return;
        }
        if(email.isEmpty()){
            emailHospital.setError("Enter email");
            emailHospital.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailHospital.setError("Please enter a valid email");
            emailHospital.requestFocus();
            return;
        }
        if(pwd.isEmpty()){
            pwdHospital.setError("Enter password");
            pwdHospital.requestFocus();
            return;
        }
        if(pwd.length()<6)
        {
            pwdHospital.setError("Minimum password length should be 6 characters");
            pwdHospital.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(SigninHospital.this, "Hospital registered successfully", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(SigninHospital.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        //startActivity(patientdash);



    }
}
