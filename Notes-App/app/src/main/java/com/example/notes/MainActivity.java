package com.example.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static notesViewModel myViewModel;
    FloatingActionButton floatingActionButton;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ImageView delete;
    customAdapter customadapter;
    static AlertDialog.Builder builder;
    boolean wantEdit = false;




    @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            floatingActionButton = findViewById(R.id.add_fab);
            delete = findViewById(R.id.delete);
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_container);
            builder = new AlertDialog.Builder(this);


            customadapter = new customAdapter();
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
            recyclerView.setAdapter(customadapter);


            myViewModel = new ViewModelProvider(this).get(notesViewModel.class);
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));

            myViewModel.getAllNotes().observe(this, new Observer<List<notesEn>>() {
                @Override
                public void onChanged(List<notesEn> notesEns) {
                    customadapter.setNotesData(notesEns);
                }
            });


            // start activity for result(new method)
            activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if(!wantEdit){
                    if (result.getResultCode() == RESULT_OK) {
                        String nTitle = result.getData().getStringExtra(addNotesActivity.TITLE);
                        String nText = result.getData().getStringExtra(addNotesActivity.NOTE_TEXT);
                        notesEn insertNote = new notesEn(nTitle, nText);
                        myViewModel.insert(insertNote);
                        Toast.makeText(MainActivity.this, "Note added", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                    }
                    }else{

                        if (result.getResultCode() == RESULT_OK) {
                            int id = result.getData().getIntExtra("NOTE ID",-1);
                            if(id==-1){
                                Toast.makeText(MainActivity.this, "Error while updating note. Try Again", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            String nTitle = result.getData().getStringExtra(addNotesActivity.TITLE);
                            String nText = result.getData().getStringExtra(addNotesActivity.NOTE_TEXT);
                            notesEn updateNote = new notesEn(nTitle, nText);
                            updateNote.setId(id);
                            myViewModel.update(updateNote);
                            Toast.makeText(MainActivity.this, "Note Updated.", Toast.LENGTH_SHORT).show();
                            wantEdit = false;
                        }
                    }
                }
            });


            customadapter.setOnItemClickListener(new customAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(notesEn note) {
                    wantEdit = true;
                    Intent intent  = new Intent(MainActivity.this,addNotesActivity.class);
                    intent.putExtra("NOTE ID",note.getId());
                    intent.putExtra("NOTE TITLE",note.getNotesTitle());
                    intent.putExtra("NOTE TEXT",note.getNotesText());
                    intent.putExtra("EDIT NOTE","true");
                    activityResultLauncher.launch(intent);
                }
            });


            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent1 = new Intent(MainActivity.this, addNotesActivity.class);

//                    Deprecated method
//                    startActivityForResult(intent,ACTIVITY_REQUEST_CODE);
//                    new method (activity result api)

                    activityResultLauncher.launch(intent1);
                }
            });

        }

        private void getSearchedNote(String text){

            String stext = "%"+text+"%";

            myViewModel.search(stext).observe(MainActivity.this, new Observer<List<notesEn>>() {
                @Override
                public void onChanged(List<notesEn> notesEns) {

                    if(notesEns==null) return;

                    customadapter.setNotesData(notesEns);
                }
            });



        }

        public static void deleteNote(notesEn note) {

            builder.setMessage("Do you want to delete this note ? ").setTitle("Delete Note")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            myViewModel.deleteNote(note);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){


                case R.id.delete_note:
                    Log.d("check sel","del");

                    builder.setMessage("Do you want to delete All note ? ").setTitle("Delete Note")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    myViewModel.deleteAllNotes();
                                    Toast.makeText(MainActivity.this, "Notes Deleted.  ", Toast.LENGTH_SHORT).show();

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    return true;

                case R.id.searchview:

                    final SearchView searchView = (SearchView) item.getActionView();
                    searchView.setQueryHint("Search Note by Note Title");

                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {

                            if(query.trim()!=null){
                                getSearchedNote(query.trim());
                            }

                            return true;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {

                            if(newText.trim()!=null){
                                getSearchedNote(newText.trim());
                            }

                            return true;
                        }
                    });
                    return true;

                default : return super.onOptionsItemSelected(item);

            }

    }



}