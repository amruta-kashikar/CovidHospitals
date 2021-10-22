package com.example.covidhospitals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.covidhospitals.adapter.DetailsPatAdapter;
import com.example.covidhospitals.adapter.HospNameAdapter;
import com.example.covidhospitals.adapter.RecyclerViewAdapter;
import com.example.covidhospitals.model.model;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DetailsPatient extends AppCompatActivity {

    RecyclerView recview;
    ArrayList<model> datalist;
    FirebaseFirestore db;
    DetailsPatAdapter adapter;
    ImageView i1, i2;
    String hospitalId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_patient);
        recview = (RecyclerView) findViewById(R.id.recview);
        i1 = findViewById(R.id.i1);
        i2 = findViewById(R.id.i2);

        hospitalId = getIntent().getStringExtra("id");
        recview.setLayoutManager(new LinearLayoutManager(this));
        datalist = new ArrayList<>();

        adapter = new DetailsPatAdapter(datalist, this);
        recview.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        db.collection("hospital").document(hospitalId).collection("admtpatient").get()
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
}