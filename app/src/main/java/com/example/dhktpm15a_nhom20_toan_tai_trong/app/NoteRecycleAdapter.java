package com.example.dhktpm15a_nhom20_toan_tai_trong.app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dhktpm15a_nhom20_toan_tai_trong.R;
import com.example.dhktpm15a_nhom20_toan_tai_trong.entity.Note;
import com.google.gson.Gson;

import java.util.List;
import java.util.Locale;

public class NoteRecycleAdapter extends RecyclerView.Adapter<NoteRecycleAdapter.ViewHolder> {

    private Context context;
    private List<Note> lsNote;
    private int idLayout;

    public NoteRecycleAdapter(Context context, List<Note> lsNote, int idLayout) {
        this.context = context;
        this.lsNote = lsNote;
        this.idLayout = idLayout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = (View) LayoutInflater.from(parent.getContext()).inflate(idLayout,parent ,false);
       ViewHolder holder = new ViewHolder(view);



        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = lsNote.get(position);

            holder.tvName.setText(note.getName());
            holder.tvlogo.setText(note.getName().substring(0,1).toUpperCase(Locale.ROOT));
            if(note.getContent().length() < 20)
                holder.tvDes.setText(note.getContent());
            else
                holder.tvDes.setText(note.getContent().substring(0,20));




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickNote(note);
            }


        });
    }



    @Override
    public int getItemCount() {
        return lsNote.size();
    }


    public void clickNote(Note note) {
        Intent intent = new Intent(context, ChiTietNote.class);
        Gson gson = new Gson();

        String StringNote = gson.toJson(note);

        intent.putExtra("note",StringNote);

        context.startActivity(intent);



    }



    //class
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvDes, tvlogo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            tvName = (TextView) itemView.findViewById(R.id.tvItemName);
            tvDes = (TextView) itemView.findViewById(R.id.tvItemDescription);
            tvlogo = (TextView) itemView.findViewById(R.id.tvItemLogo);
        }
    }
}
