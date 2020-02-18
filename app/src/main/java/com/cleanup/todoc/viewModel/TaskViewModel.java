package com.cleanup.todoc.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.TaskAndProject;
import com.cleanup.todoc.repository.TaskRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository mTaskRepository;

    private LiveData<List<TaskAndProject>> mAllTasks;


    public TaskViewModel(@NonNull Application application) {
        super(application);
        mTaskRepository = new TaskRepository(application);
        mAllTasks = mTaskRepository.getTasksWithProject();
    }

    public LiveData<List<TaskAndProject>> getAllTasks(){
        return mAllTasks;
    }

    public void insert(Task task){
        mTaskRepository.insert(task);
    }

    public void delete(Task task){
        mTaskRepository.deleteTask(task);
    }

    public void update(Task task){
        mTaskRepository.updateTask(task);
    }
}
