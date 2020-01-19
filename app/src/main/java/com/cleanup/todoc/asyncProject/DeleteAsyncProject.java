package com.cleanup.todoc.asyncProject;

import android.os.AsyncTask;
import android.util.Log;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.persistence.ProjectDao;

public class DeleteAsyncProject extends AsyncTask<Project, Void, Void> {

    private static final String TAG = "InsertAsyncProject";

    private ProjectDao mProjectDao;

    public DeleteAsyncProject(ProjectDao dao) {
        mProjectDao = dao;
    }

    @Override
    protected Void doInBackground(Project... projects) {
        Log.d(TAG, "doInBackground: thread: "+Thread.currentThread().getName());
        mProjectDao.deleteProject(projects);
        return null;
    }
}
