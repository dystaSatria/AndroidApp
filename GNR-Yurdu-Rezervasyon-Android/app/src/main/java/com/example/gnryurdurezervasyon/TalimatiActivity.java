package com.example.gnryurdurezervasyon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class TalimatiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talimati);

        Button buttonGeriDonme = findViewById(R.id.buttonGeriDonme);
        buttonGeriDonme.setOnClickListener(view -> {
            startActivity(new Intent(TalimatiActivity.this, HomeActivity22.class));
        });
    }
}