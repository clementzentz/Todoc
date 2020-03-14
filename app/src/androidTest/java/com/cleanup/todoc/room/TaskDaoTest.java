package com.cleanup.todoc.room;

import android.database.sqlite.SQLiteConstraintException;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.TaskAndProject;
import com.cleanup.todoc.util.LiveDataTestUtil;
import com.cleanup.todoc.util.TestUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TaskDaoTest extends TodocDatabaseTest{

    private static final String TEST_NAME = "Nom de la tâche test";
    private static final long TEST_PROJECT_ID = 2L;
    private static final long TEST_TIMESTAMP = new Date().getTime();

    private long mInsert1;
    private long mInsert2;
    private long mInsert3;

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Before
    public void setup(){
        TestUtil.TEST_TASK_1.setTask_id(0);
        TestUtil.TEST_TASK_2.setTask_id(1);
        TestUtil.TEST_TASK_3.setTask_id(2);

        mInsert1 = getProjectDao().insert(TestUtil.project1);
        mInsert2 = getProjectDao().insert(TestUtil.project2);
        mInsert3 = getProjectDao().insert(TestUtil.project3);
    }

    /*
        insert, read, delete
     */
    @Test
    public void insertReadDelete() throws  Exception{

        Task task = new Task(TestUtil.TEST_TASK_1);
        task.setTaskProjectId(mInsert1);
        // insert
        getTaskDao().insert(task);

        // read
        LiveDataTestUtil<List<TaskAndProject>> liveDataTestUtil = new LiveDataTestUtil<>();
        List<TaskAndProject> insertedTasks = liveDataTestUtil.getValue(getTaskDao().getTasksWithProject());

        assertNotNull(insertedTasks);

        assertEquals(task.getTaskProjectId(), insertedTasks.get(0).getTask().getTaskProjectId());
        assertEquals(task.getName(), insertedTasks.get(0).getTask().getName());
        assertEquals(task.getTimestamp(), insertedTasks.get(0).getTask().getTimestamp());

        task.setTask_id(insertedTasks.get(0).getTask().getTask_id());
        assertEquals(task, insertedTasks.get(0).getTask());

        // delete
        getTaskDao().delete(task);

        // confirm the database is empty
        insertedTasks = liveDataTestUtil.getValue(getTaskDao().getTasksWithProject());
        assertEquals(0, insertedTasks.size());
    }

    /*
        insert, read, update, read, delete
     */
    @Test
    public void insertReadUpdateReadDelete() throws  Exception{

        Task task = new Task(TestUtil.TEST_TASK_1);
        task.setTaskProjectId(mInsert1);
        // insert
        getTaskDao().insert(task);
        // read
        LiveDataTestUtil<List<TaskAndProject>> liveDataTestUtil = new LiveDataTestUtil<>();
        List<TaskAndProject> insertedTasks = liveDataTestUtil.getValue(getTaskDao().getTasksWithProject());

        assertNotNull(insertedTasks);

        assertEquals(task.getTaskProjectId(), insertedTasks.get(0).getTask().getTaskProjectId());
        assertEquals(task.getTimestamp(), insertedTasks.get(0).getTask().getTimestamp());
        assertEquals(task.getName(), insertedTasks.get(0).getTask().getName());

        task.setTask_id(insertedTasks.get(0).getTask().getTask_id());
        assertEquals(task, insertedTasks.get(0).getTask());

        // update
        task.setName(TEST_NAME);
        task.setTaskProjectId(TEST_PROJECT_ID);
        task.setCreationTimestamp(TEST_TIMESTAMP);
        getTaskDao().update(task);

        // read
        insertedTasks = liveDataTestUtil.getValue(getTaskDao().getTasksWithProject());
        assertEquals(TEST_NAME, insertedTasks.get(0).getTask().getName());
        assertEquals(TEST_PROJECT_ID, insertedTasks.get(0).getTask().getTaskProjectId());
        assertEquals(TEST_TIMESTAMP, insertedTasks.get(0).getTask().getTimestamp());

        task.setTask_id(insertedTasks.get(0).getTask().getTask_id());
        assertEquals(task, insertedTasks.get(0).getTask());

        // delete
        getTaskDao().delete(task);

        // confirm the database is empty
        insertedTasks = liveDataTestUtil.getValue(getTaskDao().getTasksWithProject());
        assertEquals(0, insertedTasks.size());
    }

    /*
        insert task with null name, throw exception
     */
    @Test(expected = SQLiteConstraintException.class)
    public void insert_nullTitle_throwSQLiteConstraintException() throws Exception{
        final Task task = new Task(TestUtil.TEST_TASK_2);
        task.setTaskProjectId(mInsert2);
        task.setName(null);

        // insert
        getTaskDao().insert(task);
    }

    /*
        insert, update with null name, throw exception
     */
    @Test(expected = SQLiteConstraintException.class)
    public void updateNote_nullTitle_throwsSQLiteConstraintException() throws Exception{
        Task task = new Task(TestUtil.TEST_TASK_3);
        task.setTaskProjectId(mInsert3);

        // insert
        getTaskDao().insert(task);

        // read
        LiveDataTestUtil<List<TaskAndProject>> liveDataTestUtil = new LiveDataTestUtil<>();
        List<TaskAndProject> insertedTasks =liveDataTestUtil.getValue(getTaskDao().getTasksWithProject());
        assertNotNull(insertedTasks);

        // update
        task = insertedTasks.get(0).getTask();
        task.setName(null);
        getTaskDao().update(task);
    }
}
