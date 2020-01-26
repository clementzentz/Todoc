package com.cleanup.todoc.persistence;

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
    long[] insertTask(Task... tasks);

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getTasks();

//    @Query("SELECT * FROM Task WHERE id LIKE :id")
//    List<Task> getTaskWithCustomQuery(int id);

    @Delete
    int deleteTask(Task... tasks);

    @Update
    int updateTask(Task... tasks);
}
