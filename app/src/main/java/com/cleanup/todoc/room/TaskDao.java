package com.cleanup.todoc.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insert(Task task);

    @Query("SELECT * FROM task_table")
    LiveData<List<Task>> getTasks();

//    @Query("SELECT * FROM Task WHERE id LIKE :id")
//    List<Task> getTaskWithCustomQuery(int id);

    @Query("DELETE FROM task_table")
    void deleteAll();

    @Delete
    int delete(Task... tasks);

    @Update
    int update(Task... tasks);
}
