package com.cleanup.todoc.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface ProjectDao {

    @Insert
    long[] insertProject(Project... projects);

    @Query("SELECT * FROM Project")
    LiveData<List<Project>> getProjects();

    @Query("SELECT * FROM Project WHERE id LIKE :id")
    List<Project> getProjectWithCustomQuery(int id);

    @Delete
    int deleteProject(Project... projects);

    @Update
    int updateProject(Project... projects);
}
