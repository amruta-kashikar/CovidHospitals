package com.example.covid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.covidhospitals.model.model.hospitalModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button Requestbtn;
    TextView coronaDashboard;
    Intent requestbedIntent,dashIntent;
    TextView totalCount, vacantCount,occupiedCount;
    CollectionReference refer;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Requestbtn = findViewById(R.id.btnRequest);

        //db=FirebaseFirestore.getInstance();

        //Signbtn = findViewById(R.id.signinbtn);
        //Dashbtn = findViewById(R.id.dashbtn);
        coronaDashboard = (TextView) findViewById(R.id.coronaDashboard);
        requestbedIntent = new Intent(this, RequestBed.class);
        //signinIntent = new Intent(this,SigninHospital.class);
        dashIntent = new Intent(this,PatientDashboard.class);
        totalCount = findViewById(R.id.totalCount);
        vacantCount = findViewById(R.id.vacantCount);
        occupiedCount = findViewById(R.id.occupiedCount);
        db = FirebaseFirestore.getInstance();
        refer = db.collection("hospital");
        Requestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(requestbedIntent);
                finish();
            }
        });
//        Signbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(signinIntent);
//                finish();
//            }
//        });
//        Dashbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(dashIntent);
//            }
//        });
        /*
        refer.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        int totalsum=0;
                        List<DocumentSnapshot> list1 = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot ds : list1)
                        {
                            String total= ds.getString("total");
                            totalsum+=Integer.valueOf(total);
                        }
                        totalCount.setText(""+totalsum);
                    }
                });
        refer.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int vacantsum=0;
                List<DocumentSnapshot> list1 = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot ds : list1)
                {
                    String vacant= ds.getString("vacant");
                    vacantsum+=Integer.valueOf(vacant);
                }
                vacantCount.setText(""+vacantsum);
            }
        }); */
        refer.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int vacantsum=0,totalsum=0,occupiedsum=0;
                List<DocumentSnapshot> list1 = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot ds : list1)
                {
                    String total = ds.getString("total");
                    totalsum += Integer.valueOf(total);
                    String vacant= ds.getString("vacant");
                    vacantsum+=Integer.valueOf(vacant);
                    occupiedsum = Integer.valueOf(totalsum) - Integer.valueOf(vacantsum);
                }
                totalCount.setText(""+totalsum);
                occupiedCount.setText(""+occupiedsum);
                vacantCount.setText(""+vacantsum);
            }
        });



        
        coronaDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patientDashboard();
            }
        });
    }

    private void patientDashboard() {
        startActivity(dashIntent);
    }
}