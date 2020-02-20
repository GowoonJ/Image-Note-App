package org.gowoon.mynoteapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("select * from note inner join image on note.id = image.note_id")
    List<NoteTable> getAll();

    @Query("select * from note inner join image on note.id = image.note_id where id = :id")
    NoteTable getNoteDetail(int id);

    @Query("select * from image")
    List<ImageTable> getAllImg();

    @Insert
    void insertNote(NoteTable note);
    @Insert
    void insertImg(List<ImageTable> img);

    @Update
    void updateNote(NoteTable note);
    @Update
    void updateImg(List<ImageTable> img);

    @Query("delete from note where id = :id")
    void deleteNote(int id);

    @Query("delete from image where note_id = :id")
    void deleteNoteImg(int id);
}
