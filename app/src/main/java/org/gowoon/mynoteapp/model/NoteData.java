package org.gowoon.mynoteapp.model;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import lombok.Data;

@Data
public class NoteData {
    @ColumnInfo(name = "id")
    public int noteId;

    @ColumnInfo
    public String title;
    public String content;

//    @Relation(parentColumn = "id", entityColumn = "note")
    @Ignore
    public Uri imageUri;
}
