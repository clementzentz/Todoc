package com.cleanup.todoc.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    @Insert
    long[] insert(Project... projects);

    @Query("SELECT * FROM project_table")
    LiveData<List<Project>> getProjects();

    @Query("SELECT * FROM project_table WHERE id LIKE :id")
    List<Project> getProjectWithCustomQuery(int id);

    @Delete
    int delete(Project... projects);

    @Query("DELETE FROM project_table")
    void deleteAll();

    @Update
    int update(Project... projects);
}
