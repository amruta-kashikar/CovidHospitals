package com.example.covidhospitals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.covidhospitals.adapter.HospitalListAdapter;
import com.example.covidhospitals.model.hospitalModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DeleteHospital extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<hospitalModel> detaillist;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db;
    HospitalListAdapter adapter;
    String hospitalId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_hospital);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        detaillist = new ArrayList<>();
        adapter = new HospitalListAdapter(detaillist,this);

        recyclerView.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();
        db.collection("hospital").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list)
                        {
                            hospitalModel obj = d.toObject(hospitalModel.class);
                            obj.setId(d.getId());
                            detaillist.add(obj);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

    }

    public void deleteHospital(String position){
        
        db.collection("hospital").document(position)
                .delete()
                .addOnCompleteListener(task -> Toast.makeText(DeleteHospital.this, "Deleted Hospital Successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(DeleteHospital.this, "Failed to delete hospital", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}