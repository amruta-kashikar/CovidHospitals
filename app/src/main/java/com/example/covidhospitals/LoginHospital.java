package com.example.covidhospitals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidhospitals.model.ControlRoomModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import org.jetbrains.annotations.NotNull;

public class LoginHospital extends AppCompatActivity {
    EditText nameHospital,phoneHospital,pwdHospital,emailHospital;
    FirebaseAuth mAuth;
    private FirebaseRemoteConfig firebaseRemoteConfig;
    FirebaseFirestore db;
    private Button btnLogin;
    Spinner spinnerUser;
    //ProgressBar progressBar;
    TextView forgotpwd;
    Intent register,hospitaldash;
//    String controlId = mAuth.getCurrentUser().getUid();
    ControlRoomModel controlRoomModel;

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
        db = FirebaseFirestore.getInstance();
        forgotpwd = findViewById(R.id.forgotPassword);
        register = new Intent(this,SigninHospital.class);
        hospitaldash = new Intent(this,HospitalDashboard.class);
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings firebaseRemoteConfigSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(60)
                .build();
        firebaseRemoteConfig.setConfigSettingsAsync(firebaseRemoteConfigSettings);
        firebaseRemoteConfig.setDefaultsAsync(R.xml.email_default_values);
        getValueFromFireBaseCOnfig();
        spinnerUser =findViewById(R.id.spinnerUser);
        ArrayAdapter<CharSequence> useradapter = ArrayAdapter.createFromResource(this,R.array.user_type,android.R.layout.simple_spinner_item);
        useradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUser.setAdapter(useradapter);


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
    private void getValueFromFireBaseCOnfig() {
        firebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if(task.isSuccessful())
                        {
                            Log.i("LoginHospital", String.valueOf(task.getResult()));
                            String cemail = firebaseRemoteConfig.getString("control_email");
/*
                            ControlRoomModel controlmodel = new ControlRoomModel(
                                    cemail
                            );
                            db.collection("controlroom").document("cwT1fIEwnrCI8ukuDTaM")
                                    .update(
                                           cemail,controlmodel.getEmail()
                                    )
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(LoginHospital.this, "Email updated successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    });
*/
                        }
                        else{
                            Toast.makeText(LoginHospital.this, "Fetch Failed", Toast.LENGTH_SHORT).show();
                        }
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

        String item = spinnerUser.getSelectedItem().toString();
        String enteredEmail= emailHospital.getText().toString();
        String enteredPass= pwdHospital.getText().toString();
        String controlRoomEmailFromDb=firebaseRemoteConfig.getString("control_email");
        String docId = firebaseRemoteConfig.getString("doc_id");
//        enteredPass.equals(controlroomPassFromDb) &&
//                enteredEmail.equals(controlRoomEmailFromDb)
        db.collection("controlroom").document(docId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        if(task.getResult().exists()){
                            String enterPwd = task.getResult().getString("pwd");
                            Log.d("tag","pwd : "+ enterPwd);
                            Log.d("tag","entermail : "+ enteredEmail);
                            Log.d("tag","cntrlemail : "+ controlRoomEmailFromDb);
                            Log.d("tag","entered pwd : "+ enteredPass);
                            Log.d("tag","doc id "+docId);
                            if( item.equals("Control Room") && (enteredEmail.equals(controlRoomEmailFromDb)) && (enteredPass.equals(enterPwd)))
                            {
                                Intent intent = new Intent(LoginHospital.this,ControlRoom.class);
                                startActivity(intent);
                            }
                            else if(item.equals("Hospital"))
                            {
                                mAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(LoginHospital.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(hospitaldash);

                                        }else{
                                            Toast.makeText(LoginHospital.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    }
                });


/*        if( item.equals("Control Room") && (enteredEmail==controlRoomEmailFromDb) && (enteredPass==enterPwd))
        {   //agar control rum hua to apko entered id and password ko database se pehl
            Intent intent = new Intent(LoginHospital.this,ControlRoom.class);
            startActivity(intent);
        }
        else if(item.equals("Hospital"))
        {
            mAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginHospital.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(hospitaldash);

                    }else{
                        Toast.makeText(LoginHospital.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

 */
    }

//    public void register_user(View v)
//    {
//        TextView Userregister = (TextView) findViewById(R.id.newUser);
//        startActivity(register);
//    }
}
