package com.cleanup.todoc.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.room.TaskDao;
import com.cleanup.todoc.room.TodocDatabase;

import java.util.List;

public class TaskRepository {

    private TaskDao mTaskDao;
    private LiveData<List<Task>> mAllTasks;

    public TaskRepository(Application application) {
        TodocDatabase  db = TodocDatabase.getInstance(application);
        mTaskDao = db.getTaskDao();
        mAllTasks = mTaskDao.getTasks();
    }

    public LiveData<List<Task>> getAllTasks(){
        return mAllTasks;
    }

    public void insert(Task task){
        TodocDatabase.databaseWriteExecutor.execute(() -> {
            mTaskDao.insert(task);
        });
    }

    public void updateTask(Task task){
        TodocDatabase.databaseWriteExecutor.execute(() -> {
            mTaskDao.update(task);
        });
    }

    public void deleteTask(Task task){
        TodocDatabase.databaseWriteExecutor.execute(() -> {
            mTaskDao.delete(task);
        });
    }
}
