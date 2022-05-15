package com.example.notes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class notesViewModel extends AndroidViewModel {

    private notesRepository myRepo;
    private final LiveData<List<notesEn>> allNotes;

    public notesViewModel(@NonNull Application application) {
        super(application);
        myRepo = new notesRepository(application);
        allNotes = myRepo.getAllNotes();
    }

    LiveData<List<notesEn>> getAllNotes(){
        return allNotes;
    }

    public void insert(notesEn note){

        myRepo.insert(note);

    }

    public void deleteAllNotes(){

        myRepo.deleteAllNotes();

    }

    public void deleteNote(notesEn noteToBeDeleted){

        myRepo.deleteNote(noteToBeDeleted);

    }

    public void update(notesEn noteToBeUpdated){

        myRepo.updateNote(noteToBeUpdated);

    }

    public  LiveData<List<notesEn>> search(String searchText){

        return myRepo.searchNote(searchText);
    }

}
