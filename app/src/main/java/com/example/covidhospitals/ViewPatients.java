package com.example.covidhospitals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.covidhospitals.adapter.HospNameAdapter;
import com.example.covidhospitals.model.hospitalModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewPatients extends AppCompatActivity {
    RecyclerView recyviewPat;
    ArrayList<hospitalModel> nameslist;
    HospNameAdapter nameAdapter;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patients);
        recyviewPat = findViewById(R.id.recyviewPat);
        recyviewPat.setLayoutManager(new LinearLayoutManager(this));
        nameslist = new ArrayList<>();
        nameAdapter = new HospNameAdapter(nameslist,this);
        recyviewPat.setAdapter(nameAdapter);
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
                            nameslist.add(obj);
                        }
                        nameAdapter.notifyDataSetChanged();
                    }
                });

    }
}