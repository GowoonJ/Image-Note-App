package org.gowoon.mynoteapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.gowoon.mynoteapp.model.ImageData;
import org.gowoon.mynoteapp.model.NoteData;
import org.gowoon.mynoteapp.model.NoteDetailData;

import java.util.Date;
import java.util.List;

@Dao
public interface NoteDao {
//    @Query("SELECT note_id, title, content, url FROM note INNER JOIN image ON note.id = image.note_id " +
//            "ORDER BY id DESC")
//    List<NoteData> getAllNote();

    @Query("SELECT id, title, content FROM note ORDER BY id DESC")
    List<NoteData> getNoteList();

    @Query("SELECT note.id, content, title FROM image INNER JOIN note ON note.id = image.note_id WHERE note.id = :id")
    NoteDetailData getNoteDetail(int id);

    @Query("SELECT url FROM image WHERE note_id = :id ORDER BY id LIMIT 1 ")
    ImageData getImg(int id);

    @Query("SELECT url FROM image WHERE note_id = :id ORDER BY id")
    List<ImageData> getImgList(int id);
    @Insert
    long insertNote(NoteTable note);
    @Insert
    void insertImg(List<ImageTable> img);

    @Query("UPDATE note SET title = :title, content = :content, date = :date WHERE id = :id")
    void updateNote(long id, String title, String content, Date date);
    @Update
    void updateImg(List<ImageTable> img);

    @Query("DELETE FROM note WHERE id = :id")
    void DeleteNote(int id);

    @Query("DELETE FROM image WHERE note_id = :id")
    void DeleteNoteImg(int id);
}
