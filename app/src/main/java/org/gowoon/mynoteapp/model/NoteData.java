package org.gowoon.mynoteapp.model;

import androidx.room.ColumnInfo;

import java.util.Date;

import lombok.Data;

@Data
public class NoteData {
    @ColumnInfo(name = "note_id")
    public int noteId;

    @ColumnInfo
    public Date date;
    public String title;
    public String content;
}
