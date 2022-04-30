package com.example.dhktpm15a_nhom20_toan_tai_trong.app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dhktpm15a_nhom20_toan_tai_trong.R;
import com.example.dhktpm15a_nhom20_toan_tai_trong.dao.NoteDAO;
import com.example.dhktpm15a_nhom20_toan_tai_trong.dao.UserDAO;
import com.example.dhktpm15a_nhom20_toan_tai_trong.database.NoteDatabase;
import com.example.dhktpm15a_nhom20_toan_tai_trong.entity.Note;
import com.google.gson.Gson;

import java.util.List;

public class NoteAdapter extends BaseAdapter {

    private Context context;
    private List<Note> lsNote;
    private int idLayout;



    private NoteDAO noteDAO;
    private UserDAO userDAO;


    public NoteAdapter(Context context, List<Note> lsNote, int idLayout) {
        this.context = context;
        this.lsNote = lsNote;
        this.idLayout = idLayout;

        noteDAO = NoteDatabase.getInstance(context).getNoteDAO();
        userDAO = NoteDatabase.getInstance(context).getUserDAO();


    }

    @Override
    public int getCount() {
        if(lsNote.size() != 0 && !lsNote.isEmpty() )
            return lsNote.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            view = (View) LayoutInflater.from(viewGroup.getContext()).inflate(idLayout,viewGroup,false);
        }

        TextView tvName = (TextView) view.findViewById(R.id.tvItemName);
        TextView tvDes = (TextView) view.findViewById(R.id.tvItemDescription);
        TextView tvlogo = (TextView) view.findViewById(R.id.tvItemLogo);

        Note note = lsNote.get(i);
        if(lsNote != null && !lsNote.isEmpty()){
            tvName.setText(note.getName());
            tvlogo.setText(note.getName().substring(0,1));
            tvDes.setText(note.getContent().substring(0,15));
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickNote(note);
            }


        });



        return view;
    }

    public void clickNote(Note note) {
        Intent intent = new Intent(context, ChiTietNote.class);
        Gson gson = new Gson();

        String StringNote = gson.toJson(note);

        intent.putExtra("idUser",StringNote);

        context.startActivity(intent);



    }
}
