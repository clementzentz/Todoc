package com.cleanup.todoc.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

@Database(entities = {Task.class, Project.class}, version = 3)
abstract class TodocDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "todoc_db";

    private static TodocDatabase instance;

    //singleton pattern
    static TodocDatabase getInstance(final Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), TodocDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    abstract TaskDao getTaskDao();
    abstract ProjectDao getProjectDao();

}
