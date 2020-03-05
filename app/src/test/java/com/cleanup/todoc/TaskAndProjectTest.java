package com.cleanup.todoc;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.TaskAndProject;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

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
        final Task task1 = new Task(1L, "aaa", new Date().getTime());
        final Task task2 = new Task(2L, "bbb", new Date().getTime());
        final Task task3 = new Task(3L, "ccc", new  Date().getTime());
        final Task task4 = new Task(4L,"ddd", new Date().getTime());

        Project project1 = new Project(1L, "Projet Tartampion", 1);
        Project project2 = new Project(1L, "Projet Lucidia", 2);
        Project project3 = new Project(1L, "Projet Circus", 3);
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

        assertEquals("Projet Tartampion", taskAndProject1.getProject().getName());
        assertEquals("Projet Lucidia", taskAndProject2.getProject().getName());
        assertEquals("Projet Circus", taskAndProject3.getProject().getName());
        assertNull(taskAndProject4.getProject());
    }

    @Test
    public void test_az_comparator() {
        final Task task1 = new Task(1L, "ccc", new Date().getTime());
        final Task task2 = new Task(2L, "bbb", new Date().getTime());
        final Task task3 = new Task(3L, "aaa", new  Date().getTime());

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
        Collections.sort(tasks, new Task.TaskAZComparator());

        assertSame(tasks.get(0).getTask(), task3);
        assertSame(tasks.get(1).getTask(), task2);
        assertSame(tasks.get(2).getTask(), task1);
    }

    @Test
    public void test_za_comparator() {
        final Task task1 = new Task(1L, "aaa", new Date().getTime());
        final Task task2 = new Task(2L, "bbb", new Date().getTime());
        final Task task3 = new Task(3L, "ccc", new  Date().getTime());

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
        Collections.sort(tasks, new Task.TaskZAComparator());

        assertSame(tasks.get(0).getTask(), task3);
        assertSame(tasks.get(1).getTask(), task2);
        assertSame(tasks.get(2).getTask(), task1);
    }

    @Test
    public void test_recent_comparator() {
        final Task task1 = new Task(1L, "aaa", new Date().getTime());
        final Task task2 = new Task(2L, "bbb", new Date().getTime());
        final Task task3 = new Task(3L, "ccc", new  Date().getTime());

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
        final Task task1 = new Task(1L, "aaa", new Date().getTime());
        final Task task2 = new Task(2L, "bbb", new Date().getTime());
        final Task task3 = new Task(3L, "ccc", new  Date().getTime());

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