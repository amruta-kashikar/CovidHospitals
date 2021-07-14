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
        //hospitalId = mAuth.getCurrentUser().getUid();
        recview1.setLayoutManager(new LinearLayoutManager(this));
        datalist1 = new ArrayList<>();


        adapter = new RequestsAdapter(datalist1,this);
        recview1.setAdapter(adapter);


        db = FirebaseFirestore.getInstance();
        //db.collection("hospital").document(hospitalId).collection("booking").get()
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
         /* db.collection("patient").document(dardi.getId()).get()
                .addOnSuccessListener(this, new OnSuccessListener<DocumentSnapshot>() {
                    DocumentSnapshot sn=null;
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        sn=documentSnapshot;
                        String smsBody=String.format("%s your request for bed has been approved by %s " +
                                "if you don't reach the hospital within %s" +
                                "\n your request will get cancelled \n" +
                                "\n unique code for verification at hospital is : %s" +
                                "Regards,\n %s",dardi.getName() , sn.get("name"),dardi.getTime(),dardi.getGenId(),sn.get("name"));

                        System.out.println(smsBody);
                        String dardiKaNumber=dardi.getPhone();
                        Toast.makeText(BedRequests.this, "Approved request successfully", Toast.LENGTH_LONG).show();
                        SmsHelper asyncT = new SmsHelper();
                        asyncT.execute(dardiKaNumber,smsBody);
                    }
                });*/
    }
    public void deleteRequest(String position){
        //Log.d("TAG", "deleteHospital() returned: " + position);
        //db.collection("hospital").document(detaillist.get(position).getId())
        //db.collection("hospital").document(detaillist.get(position).getId())
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