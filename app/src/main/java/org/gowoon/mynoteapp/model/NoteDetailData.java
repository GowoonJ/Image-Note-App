package org.gowoon.mynoteapp.model;

import androidx.room.ColumnInfo;

public class NoteDetailData {
    @ColumnInfo(name = "id")
    public int noteId;

    @ColumnInfo
    public String title;
    public String content;
//
//    @Relation(parentColumn = "id", entityColumn = "note")
//    public List<ImageTable> imageUri;
}
