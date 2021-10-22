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
import com.example.covidhospitals.adapter.RequestsAdapter;
import com.example.covidhospitals.model.model;
import com.example.covidhospitals.smsIntegration.SmsHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class BedRequests extends AppCompatActivity {

    RecyclerView recview1;
    ArrayList<model> datalist1;
    FirebaseFirestore db;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    RequestsAdapter adapter;
    ImageView i1, i2;
    //String hospitalId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bed_requests);
        recview1 = (RecyclerView) findViewById(R.id.recview1);
        i1 = findViewById(R.id.i1);
        i2 = findViewById(R.id.i2);
        
        recview1.setLayoutManager(new LinearLayoutManager(this));
        datalist1 = new ArrayList<>();


        adapter = new RequestsAdapter(datalist1,this);
        recview1.setAdapter(adapter);


        db = FirebaseFirestore.getInstance();
        
        db.collection("patient").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            model obj = d.toObject(model.class);
                            Log.d("TAGr", "onSuccess() returned: " +d.getString("genId"));
                            Log.d("TAGr", "onSuccess() returned 2: " +d.getString("name"));
                            obj.setId(d.getId());
                            datalist1.add(obj);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
    public void updateBedData(model dardi) {
        Log.d("TAG", "updateBedData() returned: " +dardi.getBedType() );
        return;
         
    }
    public void deleteRequest(String position){
        
        db.collection("patient").document(position)
                .delete()
                .addOnCompleteListener(task -> Toast.makeText(BedRequests.this, "Rejected Request Successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(BedRequests.this, "Failed to reject request", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}