package com.cleanup.todoc.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.TaskAndProject;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task_table INNER JOIN PROJECT_TABLE ON task_table.taskProject_id = project_table.project_id")
    LiveData<List<TaskAndProject>> getTasksWithProject();

    @Insert
    long insert(Task task);

    @Delete
    int delete(Task task);

    @Update
    int update(Task task);
}
