package com.example.dhktpm15a_nhom20_toan_tai_trong.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.dhktpm15a_nhom20_toan_tai_trong.R;
import com.example.dhktpm15a_nhom20_toan_tai_trong.dao.NoteDAO;
import com.example.dhktpm15a_nhom20_toan_tai_trong.dao.UserDAO;
import com.example.dhktpm15a_nhom20_toan_tai_trong.database.NoteDatabase;
import com.example.dhktpm15a_nhom20_toan_tai_trong.entity.Note;
import com.example.dhktpm15a_nhom20_toan_tai_trong.realtimeDAO.userDAO.RealtimeNote;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.List;
import java.util.Locale;

public class NoteAdapter extends BaseAdapter {

    private Context context;
    private List<Note> lsNote;
    private int idLayout;

    private NoteAdapter.ResetList reset;



    private NoteDAO noteDAO;
    private UserDAO userDAO;


    public NoteAdapter(Context context, List<Note> lsNote, int idLayout,NoteAdapter.ResetList reset) {
        this.context = context;
        this.lsNote = lsNote;
        this.idLayout = idLayout;
        this.reset = reset;

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

        Button btnDelete = view.findViewById(R.id.btnDeleteNote);


        Note note = lsNote.get(i);
        if(lsNote != null && !lsNote.isEmpty()){
            tvName.setText(note.getName());
            tvlogo.setText(note.getName().substring(0,1).toUpperCase(Locale.ROOT));
            if(note.getContent().length() < 20)
                tvDes.setText(note.getContent());
            else
                tvDes.setText(note.getContent().substring(0,20));
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickNote(note);
            }


        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickDeleteNote(note);
            }
        });



        return view;
    }

    public void clickNote(Note note) {
        Intent intent = new Intent(context, ChiTietNote.class);
        Gson gson = new Gson();

        String StringNote = gson.toJson(note);

        intent.putExtra("note",StringNote);

        context.startActivity(intent);







    }

    public void  clickDeleteNote(Note note){
        new AlertDialog.Builder(context)
                .setTitle("Xác nhận")
                .setMessage("Bạn có chắc muốn xoá ghi chú này?")
                .setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        noteDAO.deleteNote(note);
                        handleRealtimeDelete(note);

                        if(reset!=null)
                            reset.resetList();
                    }
                })
                .setNegativeButton("Huỷ",null)
                .show();





    }

    public void handleRealtimeDelete(Note note){
        RealtimeNote realtimeNote = new RealtimeNote(context);
        realtimeNote.getNoteById(note.getIdNote()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot s : snapshot.getChildren()){
                    Note n = s.getValue(Note.class);
                    if(n!= null){
                        realtimeNote.deleteNoteById(note.getIdNote());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public interface ResetList{
        public void resetList();
    }


}
