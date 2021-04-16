package com.example.covid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;

public class ResetPwd extends AppCompatActivity {
    Button Updatebtn;
    Intent updatepwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);
        Updatebtn = findViewById(R.id.btnUpdate);
        updatepwd = new Intent(this,pwdUpdated.class);
        Updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(updatepwd);
                finish();
            }
        });
    }
}