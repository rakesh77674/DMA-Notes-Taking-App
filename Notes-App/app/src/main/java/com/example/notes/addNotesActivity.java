package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addNotesActivity extends AppCompatActivity {


    public static final String TITLE = "com.example.notes.title";
    public static final String NOTE_TEXT = "com.example.notes.note";


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.add_notes_activity);
            EditText noteTitle = findViewById(R.id.title);
            EditText noteText = findViewById(R.id.noteDesc);
            Button addBtn = findViewById(R.id.addNote);
            noteTitle.requestFocus();


            Intent editIntent = getIntent();
            if(editIntent.hasExtra("EDIT NOTE")){
                setTitle("EDIT NOTE");
                noteTitle.setText(editIntent.getStringExtra("NOTE TITLE"));
                noteText.setText(editIntent.getStringExtra("NOTE TEXT"));

            }else{
                setTitle("ADD NOTE");
            }

            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nTitle = noteTitle.getText().toString().trim();
                    String nText = noteText.getText().toString().trim();
                    Intent intent = new Intent();

                    if(!nText.isEmpty()){

                        int id = getIntent().getIntExtra("NOTE ID",-1);
                        if(id!=-1) intent.putExtra("NOTE ID",id);
                        intent.putExtra(TITLE,nTitle);
                        intent.putExtra(NOTE_TEXT,nText);
                        setResult(RESULT_OK, intent);
                        finish();
                    }else{
                        Toast.makeText(addNotesActivity.this, "Enter note..", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
}