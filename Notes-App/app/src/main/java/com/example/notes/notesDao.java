package com.example.notes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface notesDao {


    @Insert
    void insert(notesEn noteToBeInserted);


    @Delete
    void delete(notesEn noteToBeDeleted);

    @Query("DELETE FROM notes_table")
    void deleteAll();


    @Query("SELECT * FROM notes_table")
    LiveData<List<notesEn>> getAllNotes();

    @Update
    void update(notesEn note);

    @Query("SELECT * FROM notes_table WHERE notesTitle LIKE :searchText")
    LiveData<List<notesEn>> search(String searchText);

}
