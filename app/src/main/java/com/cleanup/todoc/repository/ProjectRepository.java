package com.cleanup.todoc.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.room.ProjectDao;
import com.cleanup.todoc.room.TodocDatabase;

import java.util.List;

public class ProjectRepository {

    private ProjectDao mProjectDao;
    private LiveData<List<Project>> mAllProjects;
    private Project mProjectById;

    public ProjectRepository(Application application) {
        TodocDatabase  db = TodocDatabase.getInstance(application);
        mProjectDao = db.getProjectDao();
        mAllProjects = mProjectDao.getProjects();
    }

    public LiveData<List<Project>> getAllProjects(){
        return mAllProjects;
    }

    public void insertProject(Project project){
        TodocDatabase.databaseWriteExecutor.execute(() -> mProjectDao.insert(project));
    }

    public void deleteProject(Project project){
        TodocDatabase.databaseWriteExecutor.execute(() -> mProjectDao.delete(project));
    }
}
