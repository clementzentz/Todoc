package com.cleanup.todoc.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class, Project.class}, version = 8)
public abstract class TodocDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "todoc_db";

    private static volatile TodocDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //singleton pattern
    public static TodocDatabase getInstance(final Context context){
        if (INSTANCE == null){
            synchronized (TodocDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodocDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            ProjectDao projectDao = INSTANCE.getProjectDao();
            if (projectDao.getProjects() == null){
                databaseWriteExecutor.execute(() -> {
                    // Populate the database in the background.
                    // If you want to start with more words, just add them.
                    Project[] projects = new Project[]{
                            new Project(1L, "Projet Tartampion", 0xFFEADAD1),
                            new Project(2L, "Projet Lucidia", 0xFFB4CDBA),
                            new Project(3L, "Projet Circus", 0xFFA3CED2),
                    };
                    for (Project project : projects){
                        projectDao.insert(project);
                    }
                });
            }
        }
    };

    public abstract TaskDao getTaskDao();
    public abstract ProjectDao getProjectDao();

}
