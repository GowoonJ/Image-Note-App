package org.gowoon.mynoteapp.model;

import android.net.Uri;

import androidx.room.ColumnInfo;

import java.util.Date;

import lombok.Data;

@Data
public class NoteData {
    @ColumnInfo(name = "id")
    public int noteId;

    @ColumnInfo
    public String title;
    public String content;
    public String imageUri;
}
