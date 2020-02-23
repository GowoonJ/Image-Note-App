package org.gowoon.mynoteapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import lombok.Data;

@Data
@Entity(tableName = "image")
public class ImageTable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "note_id")
    @ForeignKey(entity = NoteTable.class, parentColumns = "id", childColumns = "note_id")
    public long noteId;
    public String url;
}
