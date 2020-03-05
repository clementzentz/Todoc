package com.cleanup.todoc.ui;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.TaskAndProject;
import com.cleanup.todoc.room.TaskDao;
import com.cleanup.todoc.room.TodocDatabase;
import com.cleanup.todoc.util.LiveDataTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class TodocDatabaseTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private TaskDao mTaskDao;
    private TodocDatabase mTodocDatabase;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        mTodocDatabase = Room.inMemoryDatabaseBuilder(context, TodocDatabase.class)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build();
        mTaskDao = mTodocDatabase.getTaskDao();
    }



    @After
    public void closeDb() {
        mTodocDatabase.close();
    }

    @Test
    public void insertAndGetWord() throws Exception {
        Task task = new Task(1L,"aaa", new Date().getTime());
        mTaskDao.insert(task);
        List<Task> allTasks = LiveDataTestUtil.getValue(mTaskDao.getTasks());
        assertEquals(allTasks.get(0), task);
        mTaskDao.delete(task);
    }

    @Test
    public void getAllWords() throws Exception {
        Task task0 = new Task(1L,"aaa", new Date().getTime());
        mTaskDao.insert(task0);
        Task task1 = new Task(2L,"bbb", new Date().getTime());
        mTaskDao.insert(task1);
        List<TaskAndProject> allTasks = LiveDataTestUtil.getValue(mTaskDao.getTasksWithProject());
        assertEquals(allTasks.get(0).getTask().getName(), task0.getName());
        assertEquals(allTasks.get(1).getTask().getName(), task1.getName());
        mTaskDao.delete(task0);
        mTaskDao.delete(task1);
    }

    @Test
    public void deleteAll() throws Exception {
        Task task0 = new Task(1L, "aaa", new Date().getTime());
        mTaskDao.insert(task0);
        Task task1 = new Task(2L,"word2", new Date().getTime());
        mTaskDao.insert(task1);
        mTaskDao.deleteAll();
        List<TaskAndProject> allTasks = LiveDataTestUtil.getValue(mTaskDao.getTasksWithProject());
        assertTrue(allTasks.isEmpty());
        mTaskDao.delete(task0);
        mTaskDao.delete(task1);
    }
}
