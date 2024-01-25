package com.example.gnryurdurezervasyon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class OpeningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


                    startActivity(new Intent(OpeningActivity.this,CreateAccount.class));

                finish();
            }
        },2000);
    }
}