package org.gowoon.mynoteapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import lombok.Data;

@Data
@Entity(tableName = "note")
public class NoteTable {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String title;
    public String content;
    public Date date;
}
