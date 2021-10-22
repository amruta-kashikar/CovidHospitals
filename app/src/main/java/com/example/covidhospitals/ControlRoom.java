package com.example.covidhospitals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.covidhospitals.model.model;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ControlRoom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_room);

        Button addHospital,editHospital,deleteHospital,requestBed,viewPatBtn;
        addHospital = findViewById(R.id.addHospital);
        editHospital = findViewById(R.id.editHospital);
        deleteHospital = findViewById(R.id.deleteHospital);
        requestBed = findViewById(R.id.requestBed);
        viewPatBtn = findViewById(R.id.viewPatBtn);


        
        addHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(ControlRoom.this,AddHospital.class);
                startActivity(add);
            }
        });
        deleteHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent delete = new Intent(ControlRoom.this,DeleteHospital.class);
                startActivity(delete);
            }
        });
        editHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit = new Intent(ControlRoom.this,EditHospital.class);
                startActivity(edit);
            }
        });
        requestBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent request = new Intent(ControlRoom.this,BedRequests.class);
                startActivity(request);
            }
        });

        viewPatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent view = new Intent(ControlRoom.this,ViewPatients.class);
                startActivity(view);
            }
        });

    }
}