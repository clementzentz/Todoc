package com.cleanup.todoc.persistence;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.asyncProject.DeleteAsyncProject;
import com.cleanup.todoc.asyncProject.InsertAsyncProject;
import com.cleanup.todoc.asyncProject.UpdateAsyncProject;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectRepository {

    private TodocDatabase mTodocDatabase;

    public ProjectRepository(Context context) {
        mTodocDatabase = TodocDatabase.getInstance(context);
    }

    public void insertProject(Project project){
        new InsertAsyncProject(mTodocDatabase.getProjectDao()).execute(project);
    }

    public void updateProject(Project project){
        new UpdateAsyncProject(mTodocDatabase.getProjectDao()).execute(project);
    }

    public LiveData<List<Project>> retrieveProject(){
        return mTodocDatabase.getProjectDao().getProjects();
    }

    public void deleteProject(Project project){
        new DeleteAsyncProject(mTodocDatabase.getProjectDao()).execute(project);
    }
}
