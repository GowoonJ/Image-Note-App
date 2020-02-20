package org.gowoon.mynoteapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note")
public class NoteTable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String content;
}
