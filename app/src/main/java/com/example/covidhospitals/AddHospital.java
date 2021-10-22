package com.example.covidhospitals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class AddHospital extends AppCompatActivity {
    EditText nameHospital, areaHospital,phoneHospital, pwdHospital, emailHospital, totalBeds, vacantBeds,o2Beds,nonO2Beds,icuBeds,ventilatorBeds;
    Intent linklogin, hospitaldash;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    private Button btnAdd;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hospital);

        nameHospital = findViewById(R.id.nameHospital);
        areaHospital = findViewById(R.id.areaHospital);
        phoneHospital = findViewById(R.id.phoneHospital);
        pwdHospital = findViewById(R.id.pwdHospital);
        emailHospital = findViewById(R.id.emailHospital);
        totalBeds = findViewById(R.id.totalBeds);
        vacantBeds = findViewById(R.id.vacantBeds);
        o2Beds = findViewById(R.id.o2Beds);
        nonO2Beds = findViewById(R.id.nonO2Beds);
        icuBeds = findViewById(R.id.icuBeds);
        ventilatorBeds = findViewById(R.id.ventilatorBeds);
        btnAdd = findViewById(R.id.btnAdd);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               
                authenticateUser();
            }
        });

    }

    private void registerUser() {
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String name = nameHospital.getText().toString().trim();
        String area = areaHospital.getText().toString().trim();
        String phone = phoneHospital.getText().toString().trim();
        String email = emailHospital.getText().toString().trim();
        String pwd = pwdHospital.getText().toString().trim();
        String total = totalBeds.getText().toString().trim();
        int vacant =0;
        int o2 = 0;
        int nonO2=0;
        int icu=0;
        int ventilator = 0;
        if(vacantBeds.getText().toString().isEmpty()){
            vacantBeds.setError("Enter vacant beds");
            vacantBeds.requestFocus();
            return;
        }else{
            vacant= Integer.parseInt(vacantBeds.getText().toString());
        }
        // for O2 beds
        if(o2Beds.getText().toString().isEmpty()){
            o2Beds.setError("Enter O2 beds");
            o2Beds.requestFocus();
            return;
        }
        else
        {
            o2 = Integer.parseInt(o2Beds.getText().toString());
        }
        // for Non-O2 Beds
        if(nonO2Beds.getText().toString().isEmpty()){
            nonO2Beds.setError("Enter Non-O2 beds");
            nonO2Beds.requestFocus();
            return;
        }
        else
        {
            nonO2 = Integer.parseInt(nonO2Beds.getText().toString());
        }
        // For ICU Beds
        if(icuBeds.getText().toString().isEmpty()){
            icuBeds.setError("Enter ICU beds");
            icuBeds.requestFocus();
            return;
        }
        else
        {
            icu = Integer.parseInt(o2Beds.getText().toString());
        }
        // For Ventilator Beds
        if(ventilatorBeds.getText().toString().isEmpty()){
            ventilatorBeds.setError("Enter Ventilator beds");
            ventilatorBeds.requestFocus();
            return;
        }
        else
        {
            ventilator = Integer.parseInt(o2Beds.getText().toString());
        }
        if (name.isEmpty()) {
            nameHospital.setError("Enter name");
            nameHospital.requestFocus();
            return;
        }
        if (area.isEmpty()) {
            areaHospital.setError("Enter area");
            areaHospital.requestFocus();
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
        hospital.put("area",area);
        hospital.put("phone", phone);
        hospital.put("email", email);
        hospital.put("password", pwd);
        hospital.put("total", total);
//      hospital.put("vacant", vacant);
        hospital.put("vacant", vacant);
        hospital.put("o2",o2);
        hospital.put("nonO2",nonO2);
        hospital.put("icu",icu);
        hospital.put("ventilator",ventilator);
        db.collection("hospital").document(uid)
                .set(hospital);



    }

    private void authenticateUser() {
        String name = nameHospital.getText().toString().trim();
        String area = areaHospital.getText().toString().trim();
        String phone = phoneHospital.getText().toString().trim();
        String email = emailHospital.getText().toString().trim();
        String pwd = pwdHospital.getText().toString().trim();
        String total = totalBeds.getText().toString().trim();
        int vacant =0;
        int o2 = 0;
        int nonO2=0;
        int icu=0;
        int ventilator = 0;
        if(vacantBeds.getText().toString().isEmpty()){
            vacantBeds.setError("Enter vacant beds");
            vacantBeds.requestFocus();
            return;
        }else{
            vacant= Integer.parseInt(vacantBeds.getText().toString());
        }
        if(o2Beds.getText().toString().isEmpty()){
            o2Beds.setError("Enter O2 beds");
            o2Beds.requestFocus();
            return;
        }
        else
        {
            o2 = Integer.parseInt(o2Beds.getText().toString());
        }
        //
        if(nonO2Beds.getText().toString().isEmpty()){
            nonO2Beds.setError("Enter Non-O2 beds");
            nonO2Beds.requestFocus();
            return;
        }
        else
        {
            nonO2 = Integer.parseInt(nonO2Beds.getText().toString());
        }
        //
        if(icuBeds.getText().toString().isEmpty()){
            icuBeds.setError("Enter ICU beds");
            icuBeds.requestFocus();
            return;
        }
        else
        {
            icu = Integer.parseInt(o2Beds.getText().toString());
        }
        //
        if(ventilatorBeds.getText().toString().isEmpty()){
            ventilatorBeds.setError("Enter Ventilator beds");
            ventilatorBeds.requestFocus();
            return;
        }
        else
        {
            ventilator = Integer.parseInt(o2Beds.getText().toString());
        }

        if (name.isEmpty()) {
            nameHospital.setError("Enter name");
            nameHospital.requestFocus();
            return;
        }
        if (area.isEmpty()) {
            areaHospital.setError("Enter area");
            areaHospital.requestFocus();
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
        mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<SignInMethodQueryResult> task) {
                        boolean check = !task.getResult().getSignInMethods().isEmpty();
                        if(!check)
                        {
                            mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        registerUser();
                                        Toast.makeText(AddHospital.this, "Hospital registered successfully", Toast.LENGTH_SHORT).show();
                                        //startActivity(hospitaldash);
                                    } else {
                                        Toast.makeText(AddHospital.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(AddHospital.this, "Email already exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}