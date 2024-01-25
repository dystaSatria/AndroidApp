package com.example.gnryurdurezervasyon;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class OynamalarRezervasyonu extends AppCompatActivity {

    private EditText editTextAdSoyad, editTextTelefon, editTextOyunTuru, editTextSaat, editTextTarih;
    private Button dateButton, timeButton, buttonRezervasyonYap;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezervasyon_camasir);

        editTextAdSoyad = findViewById(R.id.editTextAdSoyad);
        editTextTelefon = findViewById(R.id.editTextTelefon);
        editTextOyunTuru = findViewById(R.id.editTextOyunTuru);
        editTextSaat = findViewById(R.id.editTextSaat);
        editTextTarih = findViewById(R.id.editTextTarih);
        buttonRezervasyonYap = findViewById(R.id.buttonRezervasyonYap);
        dateButton = findViewById(R.id.buttonAppDate);
        timeButton = findViewById(R.id.buttonAppTime);

        dateButton.setOnClickListener(v -> datePickerDialog.show());
        timeButton.setOnClickListener(v -> timePickerDialog.show());
        buttonRezervasyonYap.setOnClickListener(v -> saveNote());

        initDatePicker();
        initTimePicker();

        Button buttonGeriDonme = findViewById(R.id.buttonGeriDonme);
        buttonGeriDonme.setOnClickListener(view -> {
            startActivity(new Intent(OynamalarRezervasyonu.this, Rezervasyonlar.class));
        });
    }



    void saveNote(){
        String adSoyad = editTextAdSoyad.getText().toString().trim();
        String telefon = editTextTelefon.getText().toString().trim();
        String oyunTuru = editTextOyunTuru.getText().toString().trim();
        String saat = editTextSaat.getText().toString().trim();
        String tarih = editTextTarih.getText().toString().trim();


        if(adSoyad.isEmpty() || telefon.isEmpty() || oyunTuru.isEmpty() || saat.isEmpty() || tarih.isEmpty()){
            editTextAdSoyad.setError("Doldurun");
            editTextTelefon.setError("Doldurun");
            editTextOyunTuru.setError("Doldurun");
            editTextSaat.setError("Doldurun");
            editTextTarih.setError("Doldurun");
            return;
        }
        NoteOyunlar noteOyunlar = new NoteOyunlar();
        noteOyunlar.setAdSoyad(adSoyad);
        noteOyunlar.setTelefon(telefon);
        noteOyunlar.setOyunTuru(oyunTuru);
        noteOyunlar.setSaat(saat);
        noteOyunlar.setTarih(tarih);

        saveNoteToFirebase(noteOyunlar);

    }

    void saveNoteToFirebase(NoteOyunlar noteOyunlar){
        final DocumentReference[] documentReference = new DocumentReference[1];

        CollectionReference collectionReference = UtilityOyunlar.getCollectionReferenceForNotes();
        Query query = collectionReference
                .whereEqualTo("adSoyad", noteOyunlar.getAdSoyad())
                .whereEqualTo("oyunTuru", noteOyunlar.getOyunTuru())
                .whereEqualTo("saat", noteOyunlar.getSaat())
                ;

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (!task.getResult().isEmpty()) {

                        Toast.makeText(OynamalarRezervasyonu.this, "Rezervasyon aynı veriyla yapılamaz", Toast.LENGTH_SHORT).show();
                    } else {
                        documentReference[0] = collectionReference.document();
                        documentReference[0].set(noteOyunlar).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(OynamalarRezervasyonu.this, "Rezervasyon Yapıldı", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(OynamalarRezervasyonu.this, "Rezervasyon Yapılamadı", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(OynamalarRezervasyonu.this, "Başarısız oldu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private boolean isValidInput(String adSoyad, String telefon, String camasirMakinesi, String saat, String tarih) {
        if (adSoyad.isEmpty() || telefon.isEmpty() || camasirMakinesi.isEmpty() || saat.isEmpty() || tarih.isEmpty()) {
            Toast.makeText(this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            int adjustedMonth = month + 1;
            String selectedDate = day + "/" + adjustedMonth + "/" + year;

            dateButton.setText(selectedDate);
            editTextTarih.setText(selectedDate);
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis() + 86400000);
    }

    private void initTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, hourOfDay, minute) -> {
            String formattedHour = String.format("%02d", hourOfDay);
            String formattedMinute = String.format("%02d", minute);
            String selectedTime = formattedHour + ":" + formattedMinute;

            timeButton.setText(selectedTime);
            editTextSaat.setText(selectedTime);
        };

        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        int style = AlertDialog.THEME_HOLO_DARK;
        timePickerDialog = new TimePickerDialog(this, style, timeSetListener, hour, minute, true);
    }
}
