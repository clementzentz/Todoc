package com.cleanup.todoc.asyncTask;

import android.os.AsyncTask;
import android.util.Log;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.persistence.TaskDao;

public class InsertAsyncTask extends AsyncTask<Task, Void, Void> {

    private static final String TAG = "InsertAsyncProject";

    private TaskDao mTaskDao;

    public InsertAsyncTask(TaskDao dao) {
        mTaskDao = dao;
    }

    @Override
    protected Void doInBackground(Task... tasks) {
        Log.d(TAG, "doInBackground: thread: "+Thread.currentThread().getName());
        try {
            mTaskDao.insertTask(tasks);
        }catch (NullPointerException e){
            Log.d(TAG, "doInBackground: "+e.getMessage());
        }
        return null;
    }
}
