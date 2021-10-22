package com.example.covidhospitals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.covidhospitals.adapter.EditHospAdapter;
import com.example.covidhospitals.adapter.HospitalListAdapter;
import com.example.covidhospitals.model.hospitalModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class EditHospital extends AppCompatActivity {
    RecyclerView recyview;
    ArrayList<hospitalModel> hospmodel;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db;
    EditHospAdapter adapter;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hospital);
        recyview = findViewById(R.id.recyview);
        recyview.setLayoutManager(new LinearLayoutManager(this));
        hospmodel = new ArrayList<>();
        adapter = new EditHospAdapter(hospmodel,this);
        recyview.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();
        db.collection("hospital").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d:list)
                        {
                            hospitalModel obj = d.toObject(hospitalModel.class);
                            obj.setId(d.getId());
                            hospmodel.add(obj);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }




}