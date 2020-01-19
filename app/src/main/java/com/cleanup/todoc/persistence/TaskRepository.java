package com.cleanup.todoc.persistence;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.asyncTask.DeleteAsyncTask;
import com.cleanup.todoc.asyncTask.InsertAsyncTask;
import com.cleanup.todoc.asyncTask.UpdateAsyncTask;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskRepository {

    private TodocDatabase mTodocDatabase;

    public TaskRepository(Context context) {
        mTodocDatabase = TodocDatabase.getInstance(context);
    }

    public void insertTask(Task task){
        new InsertAsyncTask(mTodocDatabase.getTaskDao()).execute(task);
    }

    public void updateTask(Task task){
        new UpdateAsyncTask(mTodocDatabase.getTaskDao()).execute(task);
    }

    public LiveData<List<Task>> retrieveTask(){
        return mTodocDatabase.getTaskDao().getTasks();
    }

    public void deleteTask(Task task){
        new DeleteAsyncTask(mTodocDatabase.getTaskDao()).execute(task);
    }
}
