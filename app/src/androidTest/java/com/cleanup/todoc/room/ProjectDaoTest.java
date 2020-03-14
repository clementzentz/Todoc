package com.cleanup.todoc.room;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.util.LiveDataTestUtil;
import com.cleanup.todoc.util.TestUtil;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProjectDaoTest extends TodocDatabaseTest{

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    /*
        insert, read, delete
     */
    @Test
    public void insertReadDeleteProjects() throws Exception{
        Project project1 = TestUtil.project1;
        Project project2 = TestUtil.project2;
        Project project3 = TestUtil.project3;

        // insert
        getProjectDao().insert(project1);
        getProjectDao().insert(project2);
        getProjectDao().insert(project3);

        // read
        LiveDataTestUtil<List<Project>> liveDataTestUtil = new LiveDataTestUtil<>();
        List<Project> insertedProjects = liveDataTestUtil.getValue(getProjectDao().getProjects());

        Assert.assertNotNull(insertedProjects);
        Assert.assertEquals(project1, insertedProjects.get(0));
        Assert.assertEquals(project2, insertedProjects.get(1));
        Assert.assertEquals(project3, insertedProjects.get(2));

        // delete
        getProjectDao().delete(project1);
        getProjectDao().delete(project2);
        getProjectDao().delete(project3);

        // confirm the database is empty
        insertedProjects = liveDataTestUtil.getValue(getProjectDao().getProjects());
        assertEquals(0, insertedProjects.size());
    }
}
