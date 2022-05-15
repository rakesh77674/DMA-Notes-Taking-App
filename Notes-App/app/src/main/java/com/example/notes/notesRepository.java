package com.example.notes;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class notesRepository {

    private notesDao myNotesDao;
    private LiveData<List<notesEn>> myNotes;


    notesRepository(Application application){

        notesDatabase db = notesDatabase.getDatabase(application);
        myNotesDao = db.notesdao();
        myNotes = myNotesDao.getAllNotes();

    }


    LiveData<List<notesEn>> getAllNotes(){
     return myNotes;
    }


    void insert(notesEn noteToBeInserted){

        notesDatabase.databaseWriteExecutor.execute(()-> {
                myNotesDao.insert(noteToBeInserted);

        });

    }

    void deleteAllNotes(){

        notesDatabase.databaseWriteExecutor.execute(() -> {
            myNotesDao.deleteAll();

        });

    }

    void deleteNote(notesEn noteToBeDeleted){

        notesDatabase.databaseWriteExecutor.execute(() -> {
            myNotesDao.delete(noteToBeDeleted);

        });

    }

    void updateNote(notesEn noteToBeUpdated){

        notesDatabase.databaseWriteExecutor.execute(() -> {
            myNotesDao.update(noteToBeUpdated);

        });

    }

    LiveData<List<notesEn>> searchNote(String searchText){

//        notesDatabase.databaseWriteExecutor.execute(() -> {
//
//
//        });

        return myNotesDao.search(searchText);
    }

}
