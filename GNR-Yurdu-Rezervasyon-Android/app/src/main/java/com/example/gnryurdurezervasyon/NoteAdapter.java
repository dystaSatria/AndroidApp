package com.example.gnryurdurezervasyon;

import static com.example.gnryurdurezervasyon.NoteAdapter.*;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NoteAdapter extends FirestoreRecyclerAdapter<NoteCamasir, NoteViewHolder> {
    Context context;

    public NoteAdapter(@NonNull FirestoreRecyclerOptions<NoteCamasir> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull NoteCamasir noteCamasir) {
        holder.contentTextView1.setText(noteCamasir.adSoyad);
        holder.contentTextView2.setText(noteCamasir.telefon);
        holder.contentTextView3.setText(noteCamasir.camasirMakinesi);
        holder.contentTextView4.setText(noteCamasir.saat);
        holder.contentTextView5.setText(noteCamasir.tarih);


        holder.itemView.setOnClickListener((v)->{
            Intent intent = new Intent(context,KayitRezervasyonlar.class);
            intent.putExtra("ad Soyad:",noteCamasir.adSoyad);
            intent.putExtra("telefon: ",noteCamasir.telefon);
            intent.putExtra("Camasir Makinesi:",noteCamasir.camasirMakinesi);
            intent.putExtra("saat: ",noteCamasir.saat);
            intent.putExtra("tarih",noteCamasir.tarih);
            String docId = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId",docId);
            context.startActivity(intent);
        });

    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_note_item,parent,false);
        return new NoteViewHolder(view);
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder{
        TextView contentTextView1,
                contentTextView2,
                contentTextView3,
                contentTextView4,
                contentTextView5;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            contentTextView1 = itemView.findViewById(R.id.note_content_text_view1);
            contentTextView2 = itemView.findViewById(R.id.note_content_text_view2);
            contentTextView3 = itemView.findViewById(R.id.note_content_text_view3);
            contentTextView4 = itemView.findViewById(R.id.note_content_text_view4);
            contentTextView5 = itemView.findViewById(R.id.note_content_text_view5);
        }
    }
}
