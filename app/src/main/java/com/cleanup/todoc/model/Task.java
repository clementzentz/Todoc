package com.cleanup.todoc.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Comparator;

/**
 * <p>Model for the tasks of the application.</p>
 *
 * @author Gaëtan HERFRAY
 */
@Entity(tableName = "task_table", foreignKeys = @ForeignKey(entity = Project.class,
        parentColumns = "project_id",
        childColumns = "taskProject_id"))
public class Task {
    /**
     * The unique identifier of the task
     */
    @PrimaryKey(autoGenerate = true)
    private long task_id;

    /**
     * The unique identifier of the project associated to the task
     */
    @ColumnInfo(name = "taskProject_id", index = true)
    private long taskProjectId;

    /**
     * The name of the task
     */
    // Suppress warning because setName is called in constructor
    @SuppressWarnings("NullableProblems")
    @NonNull
    @ColumnInfo(name = "task_name")
    private String name;

    /**
     * The timestamp when the task has been created
     */
    @ColumnInfo(name = "timestamp")
    public long creationTimestamp;

    /**
     * Instantiates a new Task.
     *
     * @param taskProjectId     the unique identifier of the project associated to the task to set
     * @param name              the name of the task to set
     * @param creationTimestamp the timestamp when the task has been created to set
     */
    public Task(long taskProjectId, @NonNull String name, long creationTimestamp) {
        this.setTaskProjectId(taskProjectId);
        this.setName(name);
        this.setCreationTimestamp(creationTimestamp);
    }

    /**
     * Returns the unique identifier of the task.
     *
     * @return the unique identifier of the task
     */
    public long getTask_id() {
        return task_id;
    }

    /**
     * Sets the unique identifier of the task.
     *
     * @param id the unique idenifier of the task to set
     */
    public void setTask_id(long id) {
        this.task_id = id;
    }

    public long getTaskProjectId(){
        return taskProjectId;
    }

    /**
     * Sets the unique identifier of the project associated to the task.
     *
     * @param projectId the unique identifier of the project associated to the task to set
     */
    public void setTaskProjectId(long projectId) {
        this.taskProjectId = projectId;
    }

//    /**
//     * Returns the project associated to the task.
//     *
//     * @return the project associated to the task
//     */
//    @Nullable
//    public Project callGetProjectById() {
//        return Project.getTaskProjectId(taskProjectId);
//    }

    /**
     * Returns the name of the task.
     *
     * @return the name of the task
     */
    @NonNull
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the task.
     *
     * @param name the name of the task to set
     */
    public void setName(@NonNull String name) {
        this.name = name;
    }

    /**
     * Sets the timestamp when the task has been created.
     *
     * @param creationTimestamp the timestamp when the task has been created to set
     */
    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    /**
     * Comparator to sort task from A to Z
     */
    public static class TaskAZComparator implements Comparator<TaskAndProject> {
        @Override
        public int compare(TaskAndProject left, TaskAndProject right) {
            return left.getTask().getName().compareTo(right.getTask().getName());
        }
    }

    /**
     * Comparator to sort task from Z to A
     */
    public static class TaskZAComparator implements Comparator<TaskAndProject> {
        @Override
        public int compare(TaskAndProject left, TaskAndProject right) {
            return right.getTask().getName().compareTo(left.getTask().getName());
        }
    }

    /**
     * Comparator to sort task from last created to first created
     */
    public static class TaskRecentComparator implements Comparator<TaskAndProject> {
        @Override
        public int compare(TaskAndProject left, TaskAndProject right) {
            return (int) (right.getTask().creationTimestamp - left.getTask().creationTimestamp);
        }
    }

    /**
     * Comparator to sort task from first created to last created
     */
    public static class TaskOldComparator implements Comparator<TaskAndProject> {
        @Override
        public int compare(TaskAndProject left, TaskAndProject right) {
            return (int) (left.getTask().creationTimestamp - right.getTask().creationTimestamp);
        }
    }
}
