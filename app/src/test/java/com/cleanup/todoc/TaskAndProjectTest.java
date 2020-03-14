package com.cleanup.todoc;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.TaskAndProject;
import com.cleanup.todoc.util.TestUtil;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

/**
 * Unit tests for tasks
 *
 * @author Gaëtan HERFRAY
 */
public class TaskAndProjectTest {

    @Test
    public void test_projects() {
        final Task task1 = TestUtil.TEST_TASK_1;
        final Task task2 = TestUtil.TEST_TASK_2;
        final Task task3 = TestUtil.TEST_TASK_3;
        final Task task4 = TestUtil.TEST_TASK_4; // taskProjectId = 0

        Project project1 = TestUtil.project1;
        Project project2 = TestUtil.project2;
        Project project3 = TestUtil.project3;
        Project project4 = null;

        TaskAndProject taskAndProject1 = new TaskAndProject();
        TaskAndProject taskAndProject2 = new TaskAndProject();
        TaskAndProject taskAndProject3 = new TaskAndProject();
        TaskAndProject taskAndProject4 = new TaskAndProject();

        taskAndProject1.setTask(task1);
        taskAndProject1.setProject(project1);

        taskAndProject2.setTask(task2);
        taskAndProject2.setProject(project2);

        taskAndProject3.setTask(task3);
        taskAndProject3.setProject(project3);

        taskAndProject4.setTask(task4);
        taskAndProject4.setProject(project4);

        assertEquals(project1.getName(), taskAndProject1.getProject().getName());
        assertEquals(project2.getName(), taskAndProject2.getProject().getName());
        assertEquals(project3.getName(), taskAndProject3.getProject().getName());
        assertNull(taskAndProject4.getProject());
    }

    @Test
    public void test_az_comparator() {
        final Task taskC = TestUtil.TEST_TASK_3;
        final Task taskB = TestUtil.TEST_TASK_2;
        final Task taskA = TestUtil.TEST_TASK_1;

        TaskAndProject taskAndProject1 = new TaskAndProject();
        TaskAndProject taskAndProject2 = new TaskAndProject();
        TaskAndProject taskAndProject3 = new TaskAndProject();

        taskAndProject1.setTask(taskC);
        taskAndProject2.setTask(taskB);
        taskAndProject3.setTask(taskA);

        final ArrayList<TaskAndProject> tasks = new ArrayList<>();
        tasks.add(taskAndProject1);
        tasks.add(taskAndProject2);
        tasks.add(taskAndProject3);
        Collections.sort(tasks, new Task.TaskAZComparator());

        assertSame(tasks.get(0).getTask(), taskA);
        assertSame(tasks.get(1).getTask(), taskB);
        assertSame(tasks.get(2).getTask(), taskC);
    }

    @Test
    public void test_za_comparator() {
        final Task taskA = TestUtil.TEST_TASK_1;
        final Task taskB = TestUtil.TEST_TASK_2;
        final Task taskC = TestUtil.TEST_TASK_3;

        TaskAndProject taskAndProject1 = new TaskAndProject();
        TaskAndProject taskAndProject2 = new TaskAndProject();
        TaskAndProject taskAndProject3 = new TaskAndProject();

        taskAndProject1.setTask(taskA);
        taskAndProject2.setTask(taskB);
        taskAndProject3.setTask(taskC);

        final ArrayList<TaskAndProject> tasks = new ArrayList<>();
        tasks.add(taskAndProject1);
        tasks.add(taskAndProject2);
        tasks.add(taskAndProject3);
        Collections.sort(tasks, new Task.TaskZAComparator());

        assertSame(tasks.get(0).getTask(), taskC);
        assertSame(tasks.get(1).getTask(), taskB);
        assertSame(tasks.get(2).getTask(), taskA);
    }

    @Test
    public void test_recent_comparator() {
        final Task task1 = TestUtil.TEST_TASK_1;
        final Task task2 = TestUtil.TEST_TASK_2;
        final Task task3 = TestUtil.TEST_TASK_3;

        TaskAndProject taskAndProject1 = new TaskAndProject();
        TaskAndProject taskAndProject2 = new TaskAndProject();
        TaskAndProject taskAndProject3 = new TaskAndProject();

        taskAndProject1.setTask(task1);
        taskAndProject2.setTask(task2);
        taskAndProject3.setTask(task3);

        final ArrayList<TaskAndProject> tasks = new ArrayList<>();
        tasks.add(taskAndProject1);
        tasks.add(taskAndProject2);
        tasks.add(taskAndProject3);
        Collections.sort(tasks, new Task.TaskRecentComparator());

        assertSame(tasks.get(0).getTask(), task1);
        assertSame(tasks.get(1).getTask(), task2);
        assertSame(tasks.get(2).getTask(), task3);
    }

    @Test
    public void test_old_comparator() {
        final Task task1 = TestUtil.TEST_TASK_1;
        final Task task2 = TestUtil.TEST_TASK_2;
        final Task task3 = TestUtil.TEST_TASK_3;

        TaskAndProject taskAndProject1 = new TaskAndProject();
        TaskAndProject taskAndProject2 = new TaskAndProject();
        TaskAndProject taskAndProject3 = new TaskAndProject();

        taskAndProject1.setTask(task1);
        taskAndProject2.setTask(task2);
        taskAndProject3.setTask(task3);

        final ArrayList<TaskAndProject> tasks = new ArrayList<>();
        tasks.add(taskAndProject1);
        tasks.add(taskAndProject2);
        tasks.add(taskAndProject3);
        Collections.sort(tasks, new Task.TaskOldComparator());

        assertSame(tasks.get(0).getTask(), task1);
        assertSame(tasks.get(1).getTask(), task2);
        assertSame(tasks.get(2).getTask(), task3);
    }
}