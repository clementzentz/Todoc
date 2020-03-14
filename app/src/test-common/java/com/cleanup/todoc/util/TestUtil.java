package com.cleanup.todoc.util;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TestUtil {
    public static final Task TEST_TASK_1 = new Task(1L, "aaa", new Date().getTime());
    public static final Task TEST_TASK_2 = new Task(2L, "bbb", new Date().getTime());
    public static final Task TEST_TASK_3 = new Task(3L, "ccc", new Date().getTime());

    public static final Task TEST_TASK_4 = new Task(0, "ddd", new Date().getTime());

    public static final Project project1 = new Project(1L, "Projet Tartampion", 1);
    public static final Project project2 = new Project(2L, "Projet Lucidia", 2);
    public static final Project project3 = new Project(3L, "Projet Circus", 3);

    static final List<Task> TEST_TASKS_LIST = Collections.unmodifiableList(
            new ArrayList<Task>(){{
                add(TEST_TASK_1);
                add(TEST_TASK_2);
                add(TEST_TASK_3);
            }}
    );
}
