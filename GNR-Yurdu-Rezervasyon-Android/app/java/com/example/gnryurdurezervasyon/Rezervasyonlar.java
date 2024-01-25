package com.example.gnryurdurezervasyon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Rezervasyonlar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezervasyonlar);

        Button buttonGeriDonme = findViewById(R.id.buttonGeriDonme);
        buttonGeriDonme.setOnClickListener(view -> {
            startActivity(new Intent(Rezervasyonlar.this, HomeActivity22.class));
        });

        Button buttonYikama = findViewById(R.id.buttonYikama);
        buttonYikama.setOnClickListener(view -> {

            startActivity(new Intent(Rezervasyonlar.this, RezervasyonCamasir.class));
        });

        Button buttonOynamalar = findViewById(R.id.buttonOynamalar);
        buttonOynamalar.setOnClickListener(view -> {

            startActivity(new Intent(Rezervasyonlar.this, OynamalarRezervasyonu.class));
        });
    }
}