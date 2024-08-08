package com.prem.todolist.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note")
    List<Note> getAllNote();

    @Query("SELECT description FROM note where id=:id")
    String getNoteText(int id);

    @Insert
    void insert(Note note);

    @Query("DELETE FROM note where id=:id")
    void delete(int id);

    @Query("UPDATE note SET description = :text where id=:id")
    void update(int id, String text);
}

