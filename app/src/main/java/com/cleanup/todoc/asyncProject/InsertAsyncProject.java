package com.cleanup.todoc.asyncProject;

import android.os.AsyncTask;
import android.util.Log;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.persistence.ProjectDao;
import com.cleanup.todoc.persistence.TaskDao;

public class InsertAsyncProject extends AsyncTask<Project, Void, Void> {

    private static final String TAG = "InsertAsyncProject";

    private ProjectDao mProjectDao;

    public InsertAsyncProject(ProjectDao dao) {
        mProjectDao = dao;
    }

    @Override
    protected Void doInBackground(Project... projects) {
        Log.d(TAG, "doInBackground: thread: "+Thread.currentThread().getName());
        mProjectDao.insertProject(projects);
        return null;
    }
}
