package com.example.gnryurdurezervasyon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity22 extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home22);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            String userEmail = user.getEmail();
            String username = userEmail != null ? userEmail.split("@")[0] : "";
            Toast.makeText(getApplicationContext(), "Welcome " + username, Toast.LENGTH_SHORT).show();
        }

        ImageButton exit = findViewById(R.id.imageButtonExit);
        ImageButton rezervasyonlarButton = findViewById(R.id.imageButtonRezervasyonlar);
        ImageButton imageButtonKantin = findViewById(R.id.imageButtonKantin);
        ImageButton imageButtonTalimati = findViewById(R.id.imageButtonTalimati);
        ImageButton imageButtonKayitRezervasyonlar = findViewById(R.id.imageButtonKayitRezervasyonlar);


        exit.setOnClickListener(view -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            startActivity(new Intent(HomeActivity22.this, Login.class));
        });

        rezervasyonlarButton.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity22.this, Rezervasyonlar.class));
        });

        imageButtonKantin.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity22.this, Kantin.class));
        });

        imageButtonTalimati.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity22.this, TalimatiActivity.class));
        });

        imageButtonKayitRezervasyonlar.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity22.this, KayitRezervasyonlar.class));
        });

        exit.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity22.this, Login.class));
        });
    }


}
