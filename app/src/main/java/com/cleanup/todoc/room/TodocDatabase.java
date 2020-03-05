package com.cleanup.todoc.room;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class, Project.class}, version = 9)
public abstract class TodocDatabase extends RoomDatabase {

    private static final String TAG = "TodocDatabase";

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
            TaskDao taskDao = INSTANCE.getTaskDao();

                databaseWriteExecutor.execute(() -> {
                    // Populate the database in the background.
                    // If you want to start with more words, just add them.
                    List<Project> projectsSync = projectDao.getProjectsSync();
                    List<Task> tasksSync = taskDao.getTasks().getValue();

                    if (projectsSync == null || projectsSync.isEmpty()) {
                        Project[] projects = new Project[]{
                                new Project(1L, "Projet Tartampion", 0xFFEADAD1),
                                new Project(2L, "Projet Lucidia", 0xFFB4CDBA),
                                new Project(3L, "Projet Circus", 0xFFA3CED2),
                        };
                        for (Project project : projects) {
                            Log.d(TAG, "onOpen: insert project "+project.getName()+" in db.");
                            projectDao.insert(project);
                        }
                    }if ((tasksSync == null) || tasksSync.isEmpty()){

                    }
                });
        }
    };

    public abstract TaskDao getTaskDao();
    public abstract ProjectDao getProjectDao();

}
