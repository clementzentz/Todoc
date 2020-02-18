package com.cleanup.todoc.model;

import androidx.room.Embedded;

public class TaskAndProject {

    @Embedded
    private Task task;

    @Embedded
    private Project project;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
