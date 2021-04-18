package com.example.covidhospitals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.covidhospitals.adapter.RecyclerViewAdapter;
import com.example.covidhospitals.model.model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

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
                            //Log.e("a", "i have id or what?" + d.getId());
                            datalist.add(obj);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    public void deleteData(int position) {

        db.collection("hospital").document(hospitalId).collection("booking").document(datalist.get(position).getId())
                .delete()
                .addOnCompleteListener(task -> Toast.makeText(PatientsList.this, "Rejected Request Successfully", Toast.LENGTH_LONG).show())
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PatientsList.this, "Failed to reject request", Toast.LENGTH_LONG).show();
                    }
                });
    }


    public void updateBedData() {

        DocumentReference onetime = db.collection("hospital").document(hospitalId);
        onetime.get()
                .addOnSuccessListener(this, new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        //Log.e("a","plzz Bhagwann.."+documentSnapshot.get("vacant"));

                        int val=documentSnapshot.getLong("vacant").intValue()-1;
                        onetime.update(
                                "vacant",val
                                ).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(PatientsList.this, "Approved request successfully", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(PatientsList.this, "Failed to approve request", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
    }
    public void removeData(int position) {

        db.collection("hospital").document(hospitalId).collection("booking").document(datalist.get(position).getId())
                .delete();
    }
}
