package org.gowoon.mynoteapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import org.gowoon.mynoteapp.helper.Converters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {NoteTable.class, ImageTable.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class NoteDB extends RoomDatabase {
    public abstract NoteDao noteDao();

    private static volatile NoteDB INSTANCE;
    private static final int NUM_OF_THREADS = 4;
    static final ExecutorService databaseWriteExcutor = Executors.newFixedThreadPool(NUM_OF_THREADS);

    public static NoteDB getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (NoteDB.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext()
                            ,NoteDB.class, "note_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
