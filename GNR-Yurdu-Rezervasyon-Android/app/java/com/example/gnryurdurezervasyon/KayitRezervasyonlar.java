    package com.example.gnryurdurezervasyon;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.MenuItem;
    import android.widget.Button;
    import android.widget.ImageButton;
    import android.widget.PopupMenu;

    import com.firebase.ui.firestore.FirestoreRecyclerOptions;
    import com.google.android.material.floatingactionbutton.FloatingActionButton;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.firestore.Query;

    public class KayitRezervasyonlar extends AppCompatActivity {

        FloatingActionButton addNoteBtn;
        RecyclerView recyclerView;
        ImageButton menuBtn;
        Button buttonGeriDonme;

        NoteAdapter noteAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_kayit_rezervasyonlar);

            addNoteBtn = findViewById(R.id.add_note_btn);
            recyclerView = findViewById(R.id.recyler_view);
            menuBtn = findViewById(R.id.menu_btn);


            addNoteBtn.setOnClickListener((v)-> startActivity(new Intent(KayitRezervasyonlar.this,RezervasyonCamasir.class)) );
            menuBtn.setOnClickListener((v)->showMenu() );
            setupRecyclerView();
        }

        void showMenu(){
            PopupMenu popupMenu  = new PopupMenu(KayitRezervasyonlar.this,menuBtn);
            popupMenu.getMenu().add("Çıkıs");
            popupMenu.show();
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if(menuItem.getTitle()=="Çıkıs"){
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(KayitRezervasyonlar.this,Login.class));
                        finish();
                        return true;
                    }
                    return false;
                }
            });

        }

        void setupRecyclerView(){
            Query query  = Utility.getCollectionReferenceForNotes().orderBy("tarih",Query.Direction.DESCENDING);
            FirestoreRecyclerOptions<NoteCamasir> options = new FirestoreRecyclerOptions.Builder<NoteCamasir>()
                    .setQuery(query,NoteCamasir.class).build();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            noteAdapter = new NoteAdapter(options,this);
            recyclerView.setAdapter(noteAdapter);
        }

        @Override
        protected void onStart() {
            super.onStart();
            noteAdapter.startListening();
        }

        @Override
        protected void onStop() {
            super.onStop();
            noteAdapter.stopListening();
        }

        @Override
        protected void onResume() {
            super.onResume();
            noteAdapter.notifyDataSetChanged();
        }


    }