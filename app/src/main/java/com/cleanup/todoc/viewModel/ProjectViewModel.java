package com.cleanup.todoc.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.repository.ProjectRepository;

import java.util.List;

public class ProjectViewModel extends AndroidViewModel {

    private ProjectRepository mProjectRepository;

    private LiveData<List<Project>> mAllProjects;

    public ProjectViewModel(@NonNull Application application) {
        super(application);
        mProjectRepository = new ProjectRepository(application);
        mAllProjects = mProjectRepository.getAllProjects();
    }

    public LiveData<List<Project>> getAllProjects(){
        return mAllProjects;
    }

    public void insert(Project project){
        mProjectRepository.insertProject(project);
    }

    public void delete(Project task){
        mProjectRepository.updateProject(task);
    }

    public void update(Project task){
        mProjectRepository.updateProject(task);
    }
}
