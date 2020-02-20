package org.gowoon.mynoteapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note INNER JOIN image ON note.id = image.note_id ORDER BY date DESC")
    List<NoteTable> getAll();

    @Query("SELECT * FROM note INNER JOIN image ON note.id = image.note_id WHERE id = :id")
    NoteTable getNoteDetail(int id);

    @Query("SELECT * FROM image ORDER BY id")
    List<ImageTable> getAllImg();

    @Insert
    void insertNote(NoteTable note);
    @Insert
    void insertImg(List<ImageTable> img);

    @Update
    void updateNote(NoteTable note);
    @Update
    void updateImg(List<ImageTable> img);

    @Query("DELETE FROM note WHERE id = :id")
    void DELETENote(int id);

    @Query("DELETE FROM image WHERE note_id = :id")
    void DELETENoteImg(int id);
}
