package com.example.covidhospitals;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class SigninHospital extends AppCompatActivity {
    EditText nameHospital, phoneHospital, pwdHospital, emailHospital, totalBeds, vacantBeds;
    Intent linklogin, hospitaldash;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    private Button btnSignIn;
    String uid;
    //ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_hospital);
        nameHospital = findViewById(R.id.nameHospital);
        phoneHospital = findViewById(R.id.phoneHospital);
        pwdHospital = findViewById(R.id.pwdHospital);
        emailHospital = findViewById(R.id.emailHospital);
        totalBeds = findViewById(R.id.totalBeds);
        vacantBeds = findViewById(R.id.vacantBeds);
        btnSignIn = findViewById(R.id.btnSignIn);
        uid = mAuth.getInstance().getCurrentUser().getUid();
        linklogin = new Intent(this, LoginHospital.class);
        //patientdash = new Intent(this,PatientDashboard.class);
        hospitaldash = new Intent(this,HospitalDashboard.class);
        //progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //registerUser();
                //finish();
                authenticateUser();
            }
        });
    }

    private void authenticateUser() {
        String email = emailHospital.getText().toString().trim();
        String pwd = pwdHospital.getText().toString().trim();
        if (email.isEmpty()) {
            emailHospital.setError("Enter email");
            emailHospital.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailHospital.setError("Please enter a valid email");
            emailHospital.requestFocus();
            return;
        }
        if (pwd.isEmpty()) {
            pwdHospital.setError("Enter password");
            pwdHospital.requestFocus();
            return;
        }
        if (pwd.length() < 6) {
            pwdHospital.setError("Minimum password length should be 6 characters");
            pwdHospital.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    registerUser();
                    Toast.makeText(SigninHospital.this, "Hospital registered successfully", Toast.LENGTH_SHORT).show();
                    startActivity(hospitaldash);
                } else {
                    Toast.makeText(SigninHospital.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        //clearData();
    }

/*    private void clearData() {
        nameHospital.getText().clear();
        phoneHospital.getText().clear();
        pwdHospital.getText().clear();
        emailHospital.getText().clear();
        totalBeds.getText().clear();
        vacantBeds.getText().clear();
    }
 */

    public void goto_login(View v) {
        TextView Linklogin = (TextView) findViewById(R.id.loginlink);
        startActivity(linklogin);
        finish();

    }

    private void registerUser() {
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String name = nameHospital.getText().toString().trim();
        String phone = phoneHospital.getText().toString().trim();
        String email = emailHospital.getText().toString().trim();
        String pwd = pwdHospital.getText().toString().trim();
        String total = totalBeds.getText().toString().trim();
        int vacant =0;
        if(vacantBeds.getText().toString().isEmpty()){
            vacantBeds.setError("Enter vacant beds");
            vacantBeds.requestFocus();
            return;
        }else{
            vacant= Integer.parseInt(vacantBeds.getText().toString());
        }
        if (name.isEmpty()) {
            nameHospital.setError("Enter name");
            nameHospital.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            phoneHospital.setError("Enter phone");
            phoneHospital.requestFocus();
            return;
        }
        if ((phone.length() < 11) || (phone.length() > 11)) {
            phoneHospital.setError("Minimum phone number length should be 11 characters");
            phoneHospital.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            emailHospital.setError("Enter email");
            emailHospital.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailHospital.setError("Please enter a valid email");
            emailHospital.requestFocus();
            return;
        }
        if (pwd.isEmpty()) {
            pwdHospital.setError("Enter password");
            pwdHospital.requestFocus();
            return;
        }
        if (pwd.length() < 6) {
            pwdHospital.setError("Minimum password length should be 6 characters");
            pwdHospital.requestFocus();
            return;
        }
        if (total.isEmpty()) {
            totalBeds.setError("Enter total beds");
            totalBeds.requestFocus();
            return;
        }
/*
        if (vacant)) {
            vacantBeds.setError("Enter vacant beds");
            vacantBeds.requestFocus();
            return;
        }
*/
        Map<String, Object> hospital = new HashMap<>();
        hospital.put("name", name);
        hospital.put("phone", phone);
        hospital.put("email", email);
        hospital.put("password", pwd);
        hospital.put("total", total);
//      hospital.put("vacant", vacant);
        hospital.put("vacant", vacant);
        db.collection("hospital").document(uid)
                .set(hospital);
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Toast.makeText(SigninHospital.this,"",Toast.LENGTH_SHORT).show();
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull @NotNull Exception e) {
//                Toast.makeText(SigninHospital.this,"",Toast.LENGTH_SHORT).show();
//            }
//        });


        //startActivity(patientdash);


    }
}
