package org.gowoon.mynoteapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.gowoon.mynoteapp.model.NoteData;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT id, title, content FROM note INNER JOIN image ON note.id = image.note_id ORDER BY date DESC LIMIT 1")
    List<NoteData> getAllNote();

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
