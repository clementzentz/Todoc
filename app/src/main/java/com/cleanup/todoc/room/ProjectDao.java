package com.cleanup.todoc.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    @Insert
    long insert(Project project);

    @Query("SELECT * FROM project_table")
    LiveData<List<Project>> getProjects();

    @Query("SELECT * FROM project_table")
    List<Project> getProjectsSync();

    @Delete
    int delete(Project project);
}
