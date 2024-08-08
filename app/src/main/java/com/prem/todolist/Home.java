package com.prem.todolist;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.prem.todolist.DB.Note;
import com.prem.todolist.DB.NoteDao;
import com.prem.todolist.DB.NoteDb;
import com.prem.todolist.DB.NoteRepo;
import com.prem.todolist.databinding.ActivityHomeBinding;
import com.prem.todolist.databinding.DialogBinding;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    ActivityHomeBinding binding;
    ArrayList<Card> cardList;
    NoteDao noteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        noteDao = NoteRepo.getInstance(this);


        cardList = new ArrayList<>();

        List<Note> noteLs = noteDao.getAllNote();
        if (noteLs != null){
            noteLs.forEach(note1 ->  cardList.add(new Card(note1.getId(),note1.getTitle())));
        }


        binding.addBtn.setImageResource(R.drawable.baseline_add_circle_outline_24);
        binding.addBtn.setColorFilter(0);

        CardAdapter adapter = new CardAdapter(this,cardList);
        adapter.giveAdapter(adapter);

        binding.cardRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.cardRecyclerView.setAdapter(adapter);
    }

    public void addCard(View view){
        DialogBinding dialogBinding = DialogBinding.inflate(LayoutInflater.from(this));
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogBinding.getRoot())
                .create();
        dialog.show();


        dialogBinding.saveBtn.setOnClickListener(view1 -> {
            dialog.dismiss();

            if (!dialogBinding.titleEdtv.getText().toString().isEmpty()) {
                int id = (int) (Math.random() * 1000000000);
                noteDao.insert(new Note(id,dialogBinding.titleEdtv.getText().toString()));
                cardList.add(new Card(id,dialogBinding.titleEdtv.getText().toString()));
            }
        });
    }
}