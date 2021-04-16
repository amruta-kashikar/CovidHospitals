package com.example.covid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginHospital extends AppCompatActivity {
    EditText nameHospital,phoneHospital,pwdHospital,emailHospital;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    private Button btnLogin;
    //ProgressBar progressBar;
    TextView forgotpwd;
    Intent register,hospitaldash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_hospital);
        nameHospital = findViewById(R.id.nameHospital);
        phoneHospital = findViewById(R.id.phoneHospital);
        pwdHospital = findViewById(R.id.pwdHospital);
        emailHospital = findViewById(R.id.emailHospital);
        btnLogin = findViewById(R.id.btnLogin);
        mAuth = FirebaseAuth.getInstance();
        forgotpwd = findViewById(R.id.forgotPassword);
        register = new Intent(this,SigninHospital.class);
        hospitaldash = new Intent(this,HospitalDashboard.class);
        
        
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
        forgotpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText resetMail = new EditText(v.getContext());
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter Your Email To Receive Reset Link.");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // extract the email and send reset link
                        String mail = resetMail.getText().toString();
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(LoginHospital.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginHospital.this, "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close the dialog
                    }
                });

                passwordResetDialog.create().show();

            }
        });
    }

    private void loginUser() {
        String email = emailHospital.getText().toString().trim();
        String pwd = pwdHospital.getText().toString().trim();
        
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
        mAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(LoginHospital.this, "Logged in successfully", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(LoginHospital.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        startActivity(hospitaldash);
    }




//    public void perform_action(View v)
//    {
//        TextView Forgotpwd = (TextView) findViewById(R.id.forgotPassword);
//
//        startActivity(forgotpwd);
//    }
    public void register_user(View v)
    {
        TextView Userregister = (TextView) findViewById(R.id.newUser);
        startActivity(register);
    }
}
