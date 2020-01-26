package com.cleanup.todoc.persistence;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

@Database(entities = {Task.class, Project.class}, version = 6)
abstract class TodocDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "todoc_db";

    private static TodocDatabase instance;

    //singleton pattern
    static TodocDatabase getInstance(final Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), TodocDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().addCallback(new Callback() {
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    ProjectDao projectDao = instance.getProjectDao();
                    if (projectDao.getProjects().getValue() == null || projectDao.getProjects().getValue().size() == 0){
                    projectDao.insertProject(new Project(1L, "Projet Tartampion", 0xFFEADAD1),
                                             new Project(2L, "Projet Lucidia", 0xFFB4CDBA),
                                             new Project(3L, "Projet Circus", 0xFFA3CED2));
                    }
                }
            }).build();
        }
        return instance;
    }

    abstract TaskDao getTaskDao();
    abstract ProjectDao getProjectDao();

}
