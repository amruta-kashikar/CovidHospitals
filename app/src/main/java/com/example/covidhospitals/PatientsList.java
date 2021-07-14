package com.example.covidhospitals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.covidhospitals.adapter.RecyclerViewAdapter;
import com.example.covidhospitals.model.model;
import com.example.covidhospitals.smsIntegration.SmsHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.getLong;


public class PatientsList extends AppCompatActivity {
    RecyclerView recview;
    ArrayList<model> datalist;
    FirebaseFirestore db;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    RecyclerViewAdapter adapter;
    ImageView i1, i2;
    String hospitalId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_list);
        recview = (RecyclerView) findViewById(R.id.recview);
        i1 = findViewById(R.id.i1);
        i2 = findViewById(R.id.i2);
        hospitalId = mAuth.getCurrentUser().getUid();
        recview.setLayoutManager(new LinearLayoutManager(this));
        datalist = new ArrayList<>();

        adapter = new RecyclerViewAdapter(datalist, this, this);
        recview.setAdapter(adapter);


        db = FirebaseFirestore.getInstance();
        db.collection("hospital").document(hospitalId).collection("booking").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            model obj = d.toObject(model.class);
                            obj.setId(d.getId());
                            datalist.add(obj);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

//    public void deleteData(int position) {
//
//        db.collection("hospital").document(hospitalId).collection("booking").document(datalist.get(position).getId())
//                .delete()
//                .addOnCompleteListener(task -> Toast.makeText(PatientsList.this, "Rejected Request Successfully", Toast.LENGTH_LONG).show())
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(PatientsList.this, "Failed to reject request", Toast.LENGTH_LONG).show();
//                    }
//                });
//    }


    public void updateBedData(model dardi) {
        Log.d("TAG", "approveBtn() returned: " +dardi.getBedType() );
        DocumentReference onetime = db.collection("hospital").document(hospitalId);
        onetime.get()
                .addOnSuccessListener(this, new OnSuccessListener<DocumentSnapshot>() {
                    DocumentSnapshot sn=null;
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        sn=documentSnapshot;
                        if(dardi.getBedType().equals("O2"))
                        {
                            int val = documentSnapshot.getLong("o2").intValue()-1;
                            int vacantval=documentSnapshot.getLong("vacant").intValue()-1;
                            onetime.update(
                                    "o2",val,
                                    "vacant",vacantval
                            ).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    String smsBody=String.format("%s your request for bed has been approved by %s " +
                                            "if you don't reach the hospital within %s" +
                                            "\n your request will get cancelled \n" +
                                            "\n unique code for verification at hospital is : %s" +
                                            "\n bed allocated to you is %s bed " +
                                            "Regards,\n %s",dardi.getName() , sn.get("name"),dardi.getTime(),dardi.getGenId(),dardi.getBedType(),sn.get("name"));
                                    String dardiKaNumber=dardi.getPhone();
                                    Toast.makeText(PatientsList.this, "Approved request successfully", Toast.LENGTH_LONG).show();
                                    SmsHelper asyncT = new SmsHelper();
                                    asyncT.execute(dardiKaNumber,smsBody);
                                }
                            });
                        }
                        if(dardi.getBedType().equals("Non-O2"))
                        {
                            int val = documentSnapshot.getLong("nonO2").intValue()-1;
                            int vacantval=documentSnapshot.getLong("vacant").intValue()-1;
                            onetime.update(
                                    "nonO2",val,
                                    "vacant",vacantval
                            ).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    Toast.makeText(PatientsList.this, "Updated value of Non-O2 bed", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        if(dardi.getBedType().equals("ICU"))
                        {
                            int val = documentSnapshot.getLong("icu").intValue()-1;
                            int vacantval=documentSnapshot.getLong("vacant").intValue()-1;
                            onetime.update(
                                    "icu",val,
                                    "vacant",vacantval
                            ).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    String smsBody=String.format("%s your request for bed has been approved by %s " +
                                            "if you don't reach the hospital within %s" +
                                            "\n your request will get cancelled \n" +
                                            "\n unique code for verification at hospital is : %s" +
                                            "\n bed allocated to you is %s bed " +
                                            "Regards,\n %s",dardi.getName() , sn.get("name"),dardi.getTime(),dardi.getGenId(),dardi.getBedType(),sn.get("name"));
                                    String dardiKaNumber=dardi.getPhone();
                                    Toast.makeText(PatientsList.this, "Approved request successfully", Toast.LENGTH_LONG).show();
                                    SmsHelper asyncT = new SmsHelper();
                                    asyncT.execute(dardiKaNumber,smsBody);
                                }
                            });
                        }

//                        if(dardi.getBedType().equals("ICU"))
//                        {
//                            int val = documentSnapshot.getLong("icu").intValue()-1;
//                            int vacantval=documentSnapshot.getLong("vacant").intValue()-1;
//                            onetime.update(
//                                    "icu",val,
//                                    "vacant",vacantval
//                            ).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull @NotNull Task<Void> task) {
//                                    String smsBody=String.format("%s your request for bed has been approved by %s " +
//                                            "if you don't reach the hospital within %s" +
//                                            "\n your request will get cancelled \n" +
//                                            "\n unique code for verification at hospital is : %s" +
//                                            "\n bed allocated to you is %s bed " +
//                                            "Regards,\n %s",dardi.getName() , sn.get("name"),dardi.getTime(),dardi.getGenId(),dardi.getBedType(),sn.get("name"));
//                                    String dardiKaNumber=dardi.getPhone();
//                                    Toast.makeText(PatientsList.this, "Approved request successfully", Toast.LENGTH_LONG).show();
//                                    SmsHelper asyncT = new SmsHelper();
//                                    asyncT.execute(dardiKaNumber,smsBody);
//                                }
//                            });
//                        }
                        if(dardi.getBedType().equals("Ventilator"))
                        {
                            int val = documentSnapshot.getLong("ventilator").intValue()-1;
                            int vacantval=documentSnapshot.getLong("vacant").intValue()-1;
                            onetime.update(
                                    "ventilator",val,
                                    "vacant",vacantval
                            ).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    String smsBody=String.format("%s your request for bed has been approved by %s " +
                                            "if you don't reach the hospital within %s" +
                                            "\n your request will get cancelled \n" +
                                            "\n unique code for verification at hospital is : %s" +
                                            "\n bed allocated to you is %s bed " +
                                            "Regards,\n %s",dardi.getName() , sn.get("name"),dardi.getTime(),dardi.getGenId(),dardi.getBedType(),sn.get("name"));
                                    String dardiKaNumber=dardi.getPhone();
                                    Toast.makeText(PatientsList.this, "Approved request successfully", Toast.LENGTH_LONG).show();
                                    SmsHelper asyncT = new SmsHelper();
                                    asyncT.execute(dardiKaNumber,smsBody);
                                }
                            });
                        }

                       /* int val=documentSnapshot.getLong("vacant").intValue()-1;
                        onetime.update(
                                "vacant",val
                                ).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {*/
//                        String smsBody=String.format("%s your request for bed has been approved by %s " +
//                                "if you don't reach the hospital within %s" +
//                                "\n your request will get cancelled \n" +
//                                "\n unique code for verification at hospital is : %s" +
//                                "\n bed allocated to you is %s bed " +
//                                "Regards,\n %s",dardi.getName() , sn.get("name"),dardi.getTime(),dardi.getGenId(),dardi.getBedType(),sn.get("name"));
//                        String dardiKaNumber=dardi.getPhone();
//                        Toast.makeText(PatientsList.this, "Approved request successfully", Toast.LENGTH_LONG).show();
//                                                SmsHelper asyncT = new SmsHelper();
//                                                      asyncT.execute(dardiKaNumber,smsBody);
                        /*    }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(PatientsList.this, "Failed to approve request", Toast.LENGTH_SHORT).show();
                            }*/
                        //
                        // });

                    }
                });
    }
    public void removeData(int position) {

        db.collection("hospital").document(hospitalId).collection("booking").document(datalist.get(position).getId())
                .delete();
    }
}
